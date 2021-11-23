package com.opentext.storage.util;


import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * SharedKey credential policy that is put into a header to authorize requests.
 */
public final class StorageSharedKeyCredential {
    private static final String AUTHORIZATION_HEADER_FORMAT = "SharedKey %s:%s";

    // Pieces of the connection string that are needed.
    private static final String ACCOUNT_NAME = "accountname";
    private static final String ACCOUNT_KEY = "accountkey";

    private final String accountName;
    private final String accountKey;

    /**
     * Initializes a new instance of StorageSharedKeyCredential contains an account's name and its primary or secondary
     * accountKey.
     *
     * @param accountName The account name associated with the request.
     * @param accountKey The account access key used to authenticate the request.
     */
    public StorageSharedKeyCredential(String accountName, String accountKey) {
        Objects.requireNonNull(accountName, "'accountName' cannot be null.");
        Objects.requireNonNull(accountKey, "'accountKey' cannot be null.");
        this.accountName = accountName;
        this.accountKey = accountKey;
    }

    /**
     * Creates a SharedKey credential from the passed connection string.
     *
     * <p><strong>Code Samples</strong></p>
     *
     * {@codesnippet com.azure.storage.common.StorageSharedKeyCredential.fromConnectionString#String}
     *
     * @param connectionString Connection string used to build the SharedKey credential.
     * @return a SharedKey credential if the connection string contains AccountName and AccountKey
     * @throws IllegalArgumentException If {@code connectionString} doesn't have AccountName or AccountKey.
     */
    /*public static StorageSharedKeyCredential fromConnectionString(String connectionString) {
        HashMap<String, String> connectionStringPieces = new HashMap<>();
        for (String connectionStringPiece : connectionString.split(";")) {
            String[] kvp = connectionStringPiece.split("=", 2);
            connectionStringPieces.put(kvp[0].toLowerCase(Locale.ROOT), kvp[1]);
        }

        String accountName = connectionStringPieces.get(ACCOUNT_NAME);
        String accountKey = connectionStringPieces.get(ACCOUNT_KEY);

        if (CoreUtils.isNullOrEmpty(accountName) || CoreUtils.isNullOrEmpty(accountKey)) {
            throw new IllegalArgumentException("Connection string must contain 'AccountName' and 'AccountKey'.");
        }

        return new StorageSharedKeyCredential(accountName, accountKey);
    }*/

    /**
     * Gets the account name associated with the request.
     *
     * @return The account name.
     */
    public String getAccountName() {
        return accountName;
    }

    private String computeHMac256(final String base64Key, final String stringToSign) {
        try {
            byte[] key = Base64.getDecoder().decode(base64Key);
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            hmacSHA256.init(new SecretKeySpec(key, "HmacSHA256"));
            byte[] utf8Bytes = stringToSign.getBytes(StandardCharsets.UTF_8);
            return Base64.getEncoder().encodeToString(hmacSHA256.doFinal(utf8Bytes));
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Generates the SharedKey Authorization value from information in the request.
     * @param requestURL URL of the request
     * @param httpMethod HTTP method being used
     * @param headers Headers on the request
     * @return the SharedKey authorization value
     */
    public String generateAuthorizationHeader(URL requestURL, String httpMethod, Map<String, String> headers) {
        String sign = buildStringToSign(requestURL, httpMethod, headers);
        //System.out.println("sign " + sign);
        String signature = computeHMac256(accountKey, sign);
        return String.format(AUTHORIZATION_HEADER_FORMAT, accountName, signature);
    }

    private String buildStringToSign(URL requestURL, String httpMethod, Map<String, String> headers) {
        String contentLength = headers.get("Content-Length");
        contentLength = contentLength.equals("0") ? "" : contentLength;

        // If the x-ms-header exists ignore the Date header
        String dateHeader = (headers.containsKey("x-ms-date")) ? ""
                : getStandardHeaderValue(headers, "Date");

        return String.join("\n",
                httpMethod,
                getStandardHeaderValue(headers, "Content-Encoding"),
                getStandardHeaderValue(headers, "Content-Language"),
                contentLength,
                getStandardHeaderValue(headers, "Content-MD5"),
                getStandardHeaderValue(headers, "Content-Type"),
                dateHeader,
                getStandardHeaderValue(headers, "If-Modified-Since"),
                getStandardHeaderValue(headers, "If-Match"),
                getStandardHeaderValue(headers, "If-None-Match"),
                getStandardHeaderValue(headers, "If-Unmodified-Since"),
                getStandardHeaderValue(headers, "Range"),
                getAdditionalXmsHeaders(headers),
                getCanonicalizedResource(requestURL));
    }

    private String getCanonicalizedResource(URL requestURL) {

        // Resource path
        final StringBuilder canonicalizedResource = new StringBuilder("/");
        canonicalizedResource.append(accountName);

        // Note that AbsolutePath starts with a '/'.
        if (requestURL.getPath().length() > 0) {
            canonicalizedResource.append(requestURL.getPath());
        } else {
            canonicalizedResource.append('/');
        }

        // check for no query params and return
        if (requestURL.getQuery() == null) {
            return canonicalizedResource.toString();
        }

        // The URL object's query field doesn't include the '?'. The QueryStringDecoder expects it.
        Map<String, String[]> queryParams = parseQueryStringSplitValues(requestURL.getQuery());
        ArrayList<String> queryParamNames = new ArrayList<>(queryParams.keySet());
        Collections.sort(queryParamNames);

        for (String queryParamName : queryParamNames) {
            String[] queryParamValues = queryParams.get(queryParamName);
            Arrays.sort(queryParamValues);
            String queryParamValuesStr = String.join(",", queryParamValues);
            canonicalizedResource.append("\n")
                    .append(queryParamName.toLowerCase(Locale.ROOT))
                    .append(":")
                    .append(queryParamValuesStr);
        }

        // append to main string builder the join of completed params with new line
        return canonicalizedResource.toString();
    }

    /*
     * Returns an empty string if the header value is null or empty.
     */
    private String getStandardHeaderValue(Map<String, String> headers, String headerName) {
        final String headerValue = headers.get(headerName);

        return headerValue == null ? "" : headerValue;
    }

    private String getAdditionalXmsHeaders(Map<String, String> headers) {
        // Add only headers that begin with 'x-ms-'
        final List<String> xmsHeaderNameArray = headers.entrySet().stream()
                .filter(entry -> entry.getKey().toLowerCase(Locale.ROOT).startsWith("x-ms-"))
                .filter(entry -> entry.getValue() != null)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (xmsHeaderNameArray.isEmpty()) {
            return "";
        }

        /* Culture-sensitive word sort */
        Collections.sort(xmsHeaderNameArray, Collator.getInstance(Locale.ROOT));

        final StringBuilder canonicalizedHeaders = new StringBuilder();
        for (final String key : xmsHeaderNameArray) {
            if (canonicalizedHeaders.length() > 0) {
                canonicalizedHeaders.append('\n');
            }

            canonicalizedHeaders.append(key.toLowerCase(Locale.ROOT))
                    .append(':')
                    .append(headers.get(key));
        }

        return canonicalizedHeaders.toString();
    }

    private static String decode(final String stringToDecode) {
        try {
            return URLDecoder.decode(stringToDecode, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
    private String urlDecode(final String stringToDecode) {
        if (stringToDecode.isEmpty()) {
            return "";
        }

        if (stringToDecode.contains("+")) {
            StringBuilder outBuilder = new StringBuilder();

            int startDex = 0;
            for (int m = 0; m < stringToDecode.length(); m++) {
                if (stringToDecode.charAt(m) == '+') {
                    if (m > startDex) {
                        outBuilder.append(decode(stringToDecode.substring(startDex, m)));
                    }
                    outBuilder.append("+");
                    startDex = m + 1;
                }
            }

            if (startDex != stringToDecode.length()) {
                outBuilder.append(decode(stringToDecode.substring(startDex)));
            }

            return outBuilder.toString();
        } else {
            return decode(stringToDecode);
        }
    }

    private <T> Map<String, T> parseQueryStringHelper(final String queryString,
                                                             Function<String, T> valueParser) {
        TreeMap<String, T> pieces = new TreeMap<>();

        if (queryString.isEmpty()) {
            return pieces;
        }

        for (String kvp : queryString.split("&")) {
            int equalIndex = kvp.indexOf("=");
            String key = urlDecode(kvp.substring(0, equalIndex).toLowerCase(Locale.ROOT));
            T value = valueParser.apply(kvp.substring(equalIndex + 1));

            pieces.putIfAbsent(key, value);
        }

        return pieces;
    }

    private Map<String, String[]> parseQueryStringSplitValues(final String queryString) {
        return parseQueryStringHelper(queryString, (value) -> urlDecode(value).split(","));
    }
}
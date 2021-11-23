package com.opentext.storage.services;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ClientResponse.Headers;
import org.springframework.web.reactive.function.client.WebClient;

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.util.AWSV4Auth;
import com.opentext.storage.util.StorageSharedKeyCredential;

import reactor.core.publisher.Mono;

public class S3StoreManagement {
	@Bean
	public MultipartResolver multipartResolver() {
	    return new CommonsMultipartResolver();
	}
	public String putFile(MultipartFile file, Path fileStorageLocation, String objectName)  throws ResourceNotFoundException{
        // Normalize file name
		String fileName =null;
		 try {
		String path=file.getInputStream().toString();
		 String storageAccountName = "AKIA2JSCEC3MAAOJSZCB";
         String storageAccessKey = "uyYgHs7c4f0dZSVYeBTOHWjSBdMQpIvDHCjbhq4C";
         URL url = new URL("http://s3.ap-south-1.amazonaws.com");
         Map<String, String> storeheaders = new HashMap<String, String>();
         storeheaders.put("Content-Length", "0");
         StorageSharedKeyCredential credential = new StorageSharedKeyCredential(storageAccountName, storageAccessKey);
         String authorizationHeader = credential.generateAuthorizationHeader(url, "PUT", storeheaders);
         System.out.println("Auth Header : " + authorizationHeader);
         
		final UrlResource resource = new UrlResource("file:/Subbu_Code/micropay/storage/Subbu_CodeData_Temp/Readme.txt");
	    ;
	    TreeMap<String, String> headers = new TreeMap<String, String>();
			
	        headers.put("host", "s3.ap-south-1.amazonaws.com");
	        headers.put("content-type", "application/octet-stream; charset=utf-8");
	        // x-amz-target is value specific to your version and operation. For version 1's SearchItems it'll be com.amazon.paapi5.v1.ProductAdvertisingAPIv1.SearchItems
	        //headers.put("x-amz-target", "com.amazon.paapi5.v1.ProductAdvertisingAPIv1.SearchItems");
	        headers.put("content-encoding", "amz-1.0");
	       headers.put("Content-Length", Long.toString(resource.contentLength()));
	       
	        // Put your Access Key in place of <ACCESS_KEY> and Secret Key in place of <SECRET_KEY> in double quotes
	        AWSV4Auth awsv4Auth = new AWSV4Auth.Builder("AKIA2JSCEC3MAAOJSZCB", "uyYgHs7c4f0dZSVYeBTOHWjSBdMQpIvDHCjbhq4C")
	           .region("ap-south-1")
	            .service("ProductAdvertisingAPI")
	            .httpMethodName("POST")
	            .headers(headers)
	            .payload(resource.toString())
	            .build();
			

				  /*String signature=$(echo -en "${string}" | openssl sha1 -hmac "${S3SECRET}" -binary | base64);
				  
				  
				  curl -s -X PUT -T "$path/$file" \
				    -H "Host: $bucket.${AWSREGION}.amazonaws.com" \
				    -H "Date: $date" \
				    -H "Content-Type: $content_type" \
				    -H "$storage_type" \
				    -H "$acl" \
				    -H "Authorization: AWS ${S3KEY}:$signature" \
				    "https://$bucket.${AWSREGION}.amazonaws.com$aws_path$file"*/
	        Map<String, String> header = awsv4Auth.getHeaders();
	        
				  WebClient webClient = WebClient.builder()
					        .baseUrl("http://s3.ap-south-1.amazonaws.com")
					        
					        .build();
				   // webClient.post().header("Content-Length", Long.toString(resource.contentLength()));
					 HttpHeaders head=new HttpHeaders();
					 head.add("Content-Length", Long.toString(resource.contentLength()));
					
				   for (Map.Entry<String, String> entrySet : header.entrySet()) {
					  
					  webClient.post().header(entrySet.getKey(), entrySet.getValue());
					  System.out.println(entrySet.getKey()+": "+entrySet.getValue());
				         
					  
					    // Print headers by un-commenting following line
			            //System.out.println("Key: " + entrySet.getKey() + " Value: " + entrySet.getValue());
			        }
				  webClient.post().contentLength(resource.contentLength());
				  webClient.post().header("Content-Length: "+resource.contentLength());
				 /* final Resource resource = file.getResource();
				  System.out.println("resource: "+resource);    
				  MultiValueMap<String, MultipartFile> data = new LinkedMultiValueMap<>();
				    data.add("file", file);*/
				    
				    
				    MultiValueMap<String, UrlResource> data = new LinkedMultiValueMap<>();
				    data.add("key", resource);
				     data.add("file", resource);
				    // data.add("Content-Length", Long.toString(resource.contentLength()));
				    Mono<ClientResponse> clientResponse=webClient.post()
		            .uri("/dctm-s3-bucket-1")
		            .body(BodyInserters.fromMultipartData(data))
		            .exchange();

		            clientResponse.subscribe((response) -> {

		                // here you can access headers and status code
		                Headers heads = response.headers();
		                HttpStatus stausCode = response.statusCode();

		                Mono<String> bodyToMono = response.bodyToMono(String.class);
		                // the second subscribe to access the body
		                bodyToMono.subscribe((body) -> {

		                    // here you can access the body
		                    System.out.println("body:" + body);

		                    // and you can also access headers and status code if you need
		                    System.out.println("headers:" + heads.asHttpHeaders());
		                    System.out.println("stausCode:" + stausCode);

		                }, (ex) -> {
		                	 System.out.println("BodyException : " + ex);
		                });
		            }, (ex) -> {
		            	System.out.println("Exception : " + ex);
		            });
		           
		        System.out.println("results: ");    
         fileName = StringUtils.cleanPath(file.getOriginalFilename());

       
        	//Files.createDirectories(fileStorageLocation);
			
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new ResourceNotFoundException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            String format=fileName.substring(fileName.lastIndexOf(".")+1);
            if(objectName!=null)
            	fileName=objectName+"."+format;
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new ResourceNotFoundException("Could not store file " + fileName + ". Please try again!\n"+ ex);
        }
    }
}

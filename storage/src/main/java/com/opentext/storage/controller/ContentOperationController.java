package com.opentext.storage.controller;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmDocument;
import com.opentext.storage.repository.DocumentRepository;
import com.opentext.storage.services.ContentService;
import com.opentext.storage.services.DmDocumentService;

public class ContentOperationController {

	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private DmDocumentService documentService;
	@Value(value = "${file.upload-dir}")
	private String repoPath;
	@Value(value = "${document.rest.url}")
	private String documentsRestUrl;
	@Value(value = "${dmrcontent.rest.url}")
	private String dmrContentUrl;
	
	@Autowired
	ContentService contentService;

	private static final Logger logger = LoggerFactory.getLogger(ContentOperationController.class);

	@RequestMapping(value = { "/content/import" }, method = { RequestMethod.POST })
	public DmDocument createdocument(@RequestParam("file") MultipartFile file, 
			@RequestParam("dm_document") DmDocument document)
			throws ResourceNotFoundException {
		logger.debug("Create called");
		String fileName = null;
		DmDocument fileResource = contentService.importFile(file,document, documentsRestUrl,dmrContentUrl);
		
			
		return fileResource;
	}

	@RequestMapping(value = { "/content/{documentId}" }, method = { RequestMethod.PUT })
	public ResponseEntity<DmDocument> updatedocument(@PathVariable(value = "documentId") Long documentId,
			@Valid @RequestBody DmDocument inputdocument, @RequestParam("file") MultipartFile file,
			@RequestParam("version") String version,

			@RequestParam("aclId") String aclId) throws ResourceNotFoundException {
		DmDocument document = documentRepository.findById(documentId);
		Optional.ofNullable(document)
				.orElseThrow(() -> new ResourceNotFoundException("Document not found for this id :: " + documentId));

		//document.setDocumentPath(inputdocument.getDocumentPath());
		document.setObjectName(inputdocument.getObjectName());
		document.setAclId(inputdocument.getAclId());
		document.setOwnerId(inputdocument.getOwnerId());
		//document.setVersion(inputdocument.getVersion());
		// document.setFirstName(inputdocument.getFirstName());
		final DmDocument updateddocument = documentRepository.save(document);
		return ResponseEntity.ok(updateddocument);
	}

	@RequestMapping(value = { "/content/checkin/{documentId}" }, method = { RequestMethod.PUT })
	public ResponseEntity<DmDocument> checkindocument(@PathVariable(value = "documentId") Long documentId,
			@RequestParam("file") MultipartFile file, @RequestParam("version") String version,

			@RequestParam("aclId") String aclId) throws ResourceNotFoundException {
		logger.debug("Check-in called");
		DmDocument document = documentRepository.findById(documentId);
		if (!document.isCheckout()) {
			new ResourceNotFoundException("Document Not checkout by the user for this id :: " + documentId);
		} else {
			try {

				Path fileStorageLocation =null;// Paths.get(document.getDocumentPath()).toAbsolutePath().normalize();
				// document.setId(id);
				if (version == null) {
		//			version = document.getVersion() + ".1";
//
				}
		//		document.setVersion(version);

				String fileName = documentService.storeFile(file, fileStorageLocation,
						document.getObjectName() + "_" + version);
				document.setCheckout(false);
				logger.debug("Checkin file completed");
			} catch (Exception e) {
				new ResourceNotFoundException(" " + e);
			}
		}
		return ResponseEntity.ok(documentRepository.save(document));
	}

	@RequestMapping(value = { "/content/checkout/{documentId}" }, method = { RequestMethod.PUT })
	public ResponseEntity<Resource> checkoutDocument(@PathVariable(value = "documentId") Long documentId)
			throws ResourceNotFoundException {
		logger.debug("Check-out called");
		Resource resource = null;
		DmDocument document = documentRepository.findById(documentId);
		if (document.isCheckout()) {
			new ResourceNotFoundException("Document already checkout by other user for this id :: " + documentId);
		} else {

			try {

				Path fileStorageLocation = null;//Paths.get(document.getDocumentPath()).toAbsolutePath().normalize();
				document.setCheckout(true);
				System.out.println("Document checked out changed");
				resource = documentService.getFile(fileStorageLocation, document.getObjectName());
				documentRepository.save(document);
				System.out.println("Document checked out");
			} catch (Exception e) {
				new ResourceNotFoundException(" Document was unable to check out" + e);
			}
		}
		return ResponseEntity.ok(resource);
	}

	@RequestMapping(value = { "/content/cancelcheckout/{documentId}" }, method = { RequestMethod.PUT })
	public Map<String, Boolean> cancleCheckoutDocument(@PathVariable(value = "documentId") Long documentId)
			throws ResourceNotFoundException {
		logger.debug("Cancle Check-out called");
		Map<String, Boolean> response = new HashMap<>();
		try {
			DmDocument document = documentRepository.findById(documentId);
			if (!document.isCheckout()) {
				response.put("cancelcheckout", Boolean.FALSE);
			} else {

				document.setCheckout(false);

				documentRepository.save(document);

				response.put("cancelcheckout", Boolean.TRUE);
				logger.debug("Document cancel checked out");

			}
		} catch (Exception e) {
			new ResourceNotFoundException(" " + e);

		}
		return response;
	}

	@RequestMapping(value = { "/content/{documentId}" }, method = { RequestMethod.DELETE })
	public Map<String, Boolean> delete(@PathVariable(value = "documentId") Long documentId,
			@RequestParam("removeAll") Boolean removeAll) throws ResourceNotFoundException {
	
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;

	}

	@RequestMapping(value = { "/content/{documentid}" }, method = { RequestMethod.GET })
	public ResponseEntity<Resource> getdocument(@PathVariable(value = "documentid") Long documentId)
			throws ResourceNotFoundException {
		Resource fileResource = contentService.getFile(documentId, documentsRestUrl,dmrContentUrl);
		return ResponseEntity.ok().body(fileResource);
	}
}

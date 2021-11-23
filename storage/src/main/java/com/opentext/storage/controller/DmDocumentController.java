package com.opentext.storage.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmDocument;
import com.opentext.storage.model.DmStore;
import com.opentext.storage.repository.DocumentRepository;
import com.opentext.storage.services.DmDocumentService;
import com.opentext.storage.services.StorageManagementService;

@RestController
public class DmDocumentController {
	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private DmDocumentService documentService;
	@Value(value = "${file.upload-dir}")
	private String repoPath;
	@Autowired
	StorageManagementService storageService;

	private static final Logger logger = LoggerFactory.getLogger(DmDocumentController.class);

	@RequestMapping(value = { "/documents" }, method = { RequestMethod.GET })
	public List<DmDocument> getAlldocuments() {
		return documentRepository.findAll();
	}

	@RequestMapping(value = { "/documents" }, method = { RequestMethod.POST })
	public DmDocument createdocument(@RequestBody  DmDocument document) throws ResourceNotFoundException {
		logger.debug("Create called");
		Long storeId=document.getStoreId();
		String aclId=document.getAclId();
		String fileName =null;
		
		try {
			
			Path fileStorageLocation = Paths.get(repoPath).toAbsolutePath().normalize();
			if (document == null) {
				System.out.println("Object instance creation: ");
				document = new DmDocument();
				
			}
			document.setAclId(aclId);
			if (storeId != null) {
				DmStore dmStore = storageService.getStore(storeId);
				fileStorageLocation = Paths.get(dmStore.getStoragePath()).toAbsolutePath().normalize();
				System.out.println("fileStorageLocation: "+fileStorageLocation);

			}
			else
			{
				storeId=1l;
			}
			//S3StoreManagement s3store=new S3StoreManagement();
			//s3store.putFile(file,fileStorageLocation, objectName);
			//document.setDocumentPath(fileStorageLocation.toString());
			document.setStoreId(storeId);
			// document.setId(id);
			//document.setOwnerId(ownerId);
			//document.setVersion(version);
			System.out.println("Folder created");
			// fileName = documentService.storeFile(file, fileStorageLocation,objectName+"_"+version);
			System.out.println("fileName: "+fileName);
		//	if(objectName!=null)
			{
		//		fileName=objectName;
				System.out.println("objectName: "+fileName);
			}
			document.setObjectName(fileName);
			document.setCheckout(false);
			

			logger.debug("File created");
		} catch (Exception e) {
			throw new ResourceNotFoundException("New object is unable to created with file name:  " + fileName);
		}
		System.out.println("Object Name: "+document.getObjectName()+" objectName : "+document.getObjectName());
		return documentRepository.save(document);
	}

	@RequestMapping(value = { "/documents/{documentId}" }, method = { RequestMethod.PUT })
	public ResponseEntity<DmDocument> updatedocument(@PathVariable(value = "documentId") Long documentId,
			@Valid @RequestBody DmDocument inputdocument, @RequestParam("file") MultipartFile file,
			@RequestParam("version") String version,

			@RequestParam("aclId") String aclId)
			throws ResourceNotFoundException {
		DmDocument document = documentRepository.findById(documentId);
		Optional.ofNullable(document)
				.orElseThrow(() -> new ResourceNotFoundException("Document not found for this id :: " + documentId));

		//document.setDocumentPath(inputdocument.getDocumentPath());
		document.setObjectName(inputdocument.getObjectName());
		document.setAclId(inputdocument.getAclId());
		document.setOwnerId(inputdocument.getOwnerId());
		// document.setFirstName(inputdocument.getFirstName());
		final DmDocument updateddocument = documentRepository.save(document);
		return ResponseEntity.ok(updateddocument);
	}

	@RequestMapping(value = { "/documents/checkin/{documentId}" }, method = { RequestMethod.PUT })
	public ResponseEntity<DmDocument> checkindocument(@PathVariable(value = "documentId") Long documentId,
		  @RequestParam("file") MultipartFile file,
			@RequestParam("version") String version,

			@RequestParam("aclId") String aclId)
			throws ResourceNotFoundException {
		logger.debug("Check-in called");
		DmDocument document = documentRepository.findById(documentId);
		if (!document.isCheckout()) {
			new ResourceNotFoundException("Document Not checkout by the user for this id :: " + documentId);
		} else {
		try {

			Path fileStorageLocation = null;//Paths.get(document.getDocumentPath()).toAbsolutePath().normalize();
			// document.setId(id);
//			if (version == null) {
//				version = document.getVersion() + ".1";
//
//			}
//			document.setVersion(version);
//			
			String fileName = documentService.storeFile(file, fileStorageLocation,document.getObjectName()+"_"+version);
			document.setCheckout(false);
			logger.debug("Checkin file completed");
		} catch (Exception e) {
			new ResourceNotFoundException(" " + e);
		}
		}
		return ResponseEntity.ok(documentRepository.save(document));
	}

	@RequestMapping(value = { "/documents/checkout/{documentId}" }, method = { RequestMethod.PUT })
	public ResponseEntity<Resource> checkoutDocument(@PathVariable(value = "documentId") Long documentId)
			throws ResourceNotFoundException {
		logger.debug("Check-out called");
		Resource resource = null;
		DmDocument document = documentRepository.findById(documentId);
		if (document.isCheckout()) {
			new ResourceNotFoundException("Document already checkout by other user for this id :: " + documentId);
		} else {

			try {

				Path fileStorageLocation =null;// Paths.get(document.getDocumentPath()).toAbsolutePath().normalize();
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

	@RequestMapping(value = { "/documents/cancelcheckout/{documentId}" }, method = { RequestMethod.PUT })
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

	@RequestMapping(value = { "/documents/{documentId}" }, method = { RequestMethod.DELETE })
	public Map<String, Boolean> delete(@PathVariable(value = "documentId") Long documentId)
			throws ResourceNotFoundException {
		DmDocument document = documentRepository.findById(documentId);
		Optional.ofNullable(document)
				.orElseThrow(() -> new ResourceNotFoundException("Document not found for this id :: " + documentId));
		documentRepository.delete(document);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;

	}

	@RequestMapping(value = { "/documents/{documentid}" }, method = { RequestMethod.GET })
	public ResponseEntity<DmDocument> getdocument(@PathVariable(value = "documentid") Long documentId)
			throws ResourceNotFoundException {
		DmDocument document = documentRepository.findById(documentId);
		Optional.ofNullable(document)
				.orElseThrow(() -> new ResourceNotFoundException("Document not found for this id :: " + documentId));
		return ResponseEntity.ok().body(document);
	}

}

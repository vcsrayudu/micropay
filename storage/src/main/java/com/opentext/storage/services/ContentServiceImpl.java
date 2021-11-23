package com.opentext.storage.services;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmDocument;
import com.opentext.storage.model.DmStore;
import com.opentext.storage.model.DmrContent;
import com.opentext.storage.model.DmSysObject;
@Service(value="contentService")
public class ContentServiceImpl implements ContentService{
	@Autowired
	@LoadBalanced
	 private RestTemplate restTemplate;
	private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);
	@Override
	public boolean checkIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkOut() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelCheckOut() {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public Resource getFile(Long documentId,String documentsRestUrl, String dmrContentUrl) throws ResourceNotFoundException {
		DmDocument document =(DmDocument)getDmObject(documentId,documentsRestUrl);
		Long dmrId=document.getDmrContentId();
		DmrContent dmrContent=(DmrContent)getDmObject(dmrId,dmrContentUrl);
		String documentPath=dmrContent.getDocumentPath();
		Resource src=loadFileAsResource(documentPath,null);
		return src;
	}

	@Override
	public DmDocument importFile(MultipartFile file, DmDocument document,String docResturl, String contRestUrl)
			throws ResourceNotFoundException {
		String fileName =null;
		Long storeId = document.getStoreId();
		Path fileStorageLocation=null;
		try {
			
			 if (document == null) {
				System.out.println("Object instance creation: ");
				document = new DmDocument();
				
			}
			//document.setAclId(aclId);
			if (storeId != null) {
				//Rest Store URL
				DmStore dmStore = (DmStore)getDmObject(storeId, docResturl);
				fileStorageLocation = Paths.get(dmStore.getStoragePath()).toAbsolutePath().normalize();
				System.out.println("fileStorageLocation: "+fileStorageLocation);

			}
			else
			{
				storeId=1l;
			}
		//	document.setDocumentPath(fileStorageLocation.toString());
			document.setStoreId(storeId);
			// document.setId(id);
			
			document.setCheckout(true);
			createDmObject(document,docResturl);
//Create dmr_content object
			//Copy the file
			// Unlock the dm_document object
			logger.debug("File created");
		} catch (Exception e) {
			throw new ResourceNotFoundException("New object is unable to created with file name:  " + fileName);
		}
		
		return document;
	}
	

	
	@Override
	public boolean delete(Long documentId, String documentsRestUrl, String dmrContentUrl, boolean removeAll) throws ResourceNotFoundException{
		// TODO Auto-generated method stub
		DmDocument document =(DmDocument)getDmObject(documentId,documentsRestUrl);
		restTemplate.delete(documentsRestUrl + "/" + documentId);
		if (removeAll) {
			// Remove the content ID from repository and Content from store.
			// Fetch the dmr content objects which contains the parent id is documentId and
			// delete all objects
		}
		
		
		return true;
	}
	
	//Need to customize in single generic  method
	public DmSysObject getDmObject(Long docId,String restUrl) throws ResourceNotFoundException
	{
		DmSysObject document = restTemplate.getForObject(restUrl + "/" + docId, DmSysObject.class);
		Optional.ofNullable(document)
		.orElseThrow(() -> new ResourceNotFoundException("dmdocument not found for this id :: " + docId));
		return document;
	}
	
	public DmSysObject createDmObject(DmSysObject object,String restUrl) throws ResourceNotFoundException
	{
		DmSysObject out = restTemplate.postForObject(restUrl, object, DmSysObject.class);
		Optional.ofNullable(out)
		.orElseThrow(() -> new ResourceNotFoundException("Object is not able to created : " ));
		logger.debug("Object is created successfully");
		return out;
	}
	
	public DmrContent getDmrContent(Long contentId,String contentRestUrl) throws ResourceNotFoundException
	{
		DmrContent document = restTemplate.getForObject(contentRestUrl + "/" + contentId, DmrContent.class);
		Optional.ofNullable(document)
		.orElseThrow(() -> new ResourceNotFoundException("dmdocument not found for this id :: " + contentId));
		return document;
	}
	
	public Resource loadFileAsResource(String fileName, Path fileStorageLocation) throws ResourceNotFoundException {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found " + fileName+"\n"+ ex);
        }
    }

}

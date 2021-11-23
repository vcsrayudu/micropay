package com.opentext.storage.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmDocument;

@Service(value="documentService")
public class DmDocumentServiceImpl implements DmDocumentService{
	

	public String checkIn(MultipartFile file, Path fileStorageLocation) throws ResourceNotFoundException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new ResourceNotFoundException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new ResourceNotFoundException("Could not store file " + fileName + ". Please try again!\n"+ ex);
        }
    }
	    public String storeFile(MultipartFile file, Path fileStorageLocation, String objectName)  throws ResourceNotFoundException{
	        // Normalize file name
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	        	//Files.createDirectories(fileStorageLocation);
				
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new ResourceNotFoundException("Sorry! Filename contains invalid path sequence " + fileName);
	            }

	            // Copy file to the target location (Replacing existing file with the same name)
	            String format=fileName.substring(fileName.lastIndexOf(".")+1);
	           // String format=file.getContentType();
	            if(objectName!=null)
	            	fileName=objectName+"."+format;
	            Path targetLocation = fileStorageLocation.resolve(fileName);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

	            return fileName;
	        } catch (IOException ex) {
	            throw new ResourceNotFoundException("Could not store file " + fileName + ". Please try again!\n"+ ex);
	        }
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
		public boolean importFile() {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public Resource getFile(Path fileStorageLocation,String fileName) throws ResourceNotFoundException{
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
		@Override
		public DmDocument createDocument() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public ArrayList<DmDocument> getAllDocuments() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public DmDocument getDocument(String documentId) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}


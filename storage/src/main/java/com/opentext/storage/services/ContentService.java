package com.opentext.storage.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmDocument;

public interface ContentService {
	public boolean checkIn();
	public boolean checkOut();
	public boolean cancelCheckOut();
	
	public boolean delete(Long documentId,String documentsRestUrl, String dmrContentUrl,boolean removeAll)throws ResourceNotFoundException;
	public Resource getFile(Long documentId,String documentsRestUrl, String dmrContentUrl) throws ResourceNotFoundException;
	public DmDocument importFile(MultipartFile file, DmDocument document,String docResturl, String contRestUrl)
			throws ResourceNotFoundException;

}

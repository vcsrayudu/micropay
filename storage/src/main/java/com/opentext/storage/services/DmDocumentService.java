package com.opentext.storage.services;

import java.nio.file.Path;
import java.util.ArrayList;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmDocument;

public interface DmDocumentService {
public ArrayList<DmDocument> getAllDocuments();
public DmDocument getDocument(String documentId);
public boolean checkIn();
public boolean checkOut();
public boolean cancelCheckOut();
public boolean importFile();
public Resource getFile(Path filePath,String fileName) throws ResourceNotFoundException;
public DmDocument createDocument();
public String storeFile(MultipartFile file, Path fileStorageLocation,String objectName) throws ResourceNotFoundException;


}

package com.opentext.storage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmStore;
import com.opentext.storage.repository.StorageRepository;
import com.opentext.storage.services.StorageManagementService;

@RestController
public class StorageManagementController {
	@Autowired
    private StorageRepository storageRepository;
	@Autowired
    private StorageManagementService storageService;
    private static final Logger logger = LoggerFactory.getLogger(StorageManagementController.class);
   

    @RequestMapping(value={"/stores"},method={RequestMethod.GET})
    public List<DmStore> getAllStores() {
    	return storageRepository.findAll();
    }

    @RequestMapping(value={"/stores"}, method={RequestMethod.POST}, headers={"Accept=application/json,application/xml,text/plain"})
    public DmStore createStorage(@RequestBody DmStore storage) {
    	logger.debug("Storage object creation initiated");
    	DmStore store=storageRepository.save(storage);
    	logger.debug("Storage object successfully created");
    	return store;
    	
    }

    @RequestMapping(value={"/stores/{storageId}"}, method={RequestMethod.PUT})
    public ResponseEntity<DmStore> updateStorage(@PathVariable(value = "storageId") Long storageId,
    	@Valid @RequestBody DmStore inputstorage) throws ResourceNotFoundException {
    	DmStore storage = storageRepository.findById(storageId);
    				Optional.ofNullable(storage).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + storageId));

    		storage.setObjectName(inputstorage.getObjectName());
    		storage.setStoragePath(inputstorage.getStoragePath());
    		//storage.setFirstName(inputstorage.getFirstName());
    		final DmStore updatedstorage= storageRepository.save(storage);
    		return ResponseEntity.ok(updatedstorage);
    }

    @RequestMapping(value={"/stores/{storageId}"}, method={RequestMethod.DELETE})
    public Map<String, Boolean> delete(@PathVariable(value="storageId") Long storageId) 
    	throws ResourceNotFoundException {
    	DmStore storage = storageRepository.findById(storageId);
            		Optional.ofNullable(storage).orElseThrow(() -> new ResourceNotFoundException("storage not found for this id :: " + storageId));
            		storageRepository.delete(storage);
            		Map<String, Boolean> response = new HashMap<>();
            		response.put("Storage deleted", Boolean.TRUE);
            		return response;
           
        
    }

    @RequestMapping(value={"/stores/{storageid}"}, method={RequestMethod.GET})
    public ResponseEntity<DmStore> getstorage(@PathVariable(value="storageid") Long  storageId) 
    	throws ResourceNotFoundException {
    	DmStore storage = storageService.getStore(storageId);
            return ResponseEntity.ok().body(storage);
    }
	
	
}

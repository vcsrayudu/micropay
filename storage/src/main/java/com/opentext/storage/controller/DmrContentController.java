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

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmrContent;
import com.opentext.storage.repository.ContentRepository;


public class DmrContentController {

	@Autowired
    private ContentRepository dmrContentRepository;
	private static final Logger logger = LoggerFactory.getLogger(DmrContentController.class);
   

    @RequestMapping(value={"/storage/dmrcontent"},method={RequestMethod.GET})
    public List<DmrContent> getAllStores() {
    	return dmrContentRepository.findAll();
    }

    @RequestMapping(value={"/storage/dmrcontent"}, method={RequestMethod.POST}, headers={"Accept=application/json,application/xml,text/plain"})
    public DmrContent createDmrContent(@RequestBody DmrContent content) {
    	return dmrContentRepository.save(content);
    }

    @RequestMapping(value={"/storage/dmrcontent/{contentId}"}, method={RequestMethod.PUT})
    public ResponseEntity<DmrContent> updateDmrContent(@PathVariable(value = "contentId") Long contentId,
    	@Valid @RequestBody DmrContent inputDmrContent) throws ResourceNotFoundException {
    	DmrContent dmrContent = dmrContentRepository.findById(contentId);
    				Optional.ofNullable(dmrContent).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + contentId));

    				dmrContent.setParentId(inputDmrContent.getParentId());
    				dmrContent.setDocumentPath(inputDmrContent.getDocumentPath());
    		//DmrContent.setFirstName(inputDmrContent.getFirstName());
    		final DmrContent updatedDmrContent= dmrContentRepository.save(dmrContent);
    		return ResponseEntity.ok(updatedDmrContent);
    }

    @RequestMapping(value={"/storage/dmrcontent/{contentId}"}, method={RequestMethod.DELETE})
    public Map<String, Boolean> delete(@PathVariable(value="contentId") Long contentId) 
    	throws ResourceNotFoundException {
    	DmrContent dmrContent = dmrContentRepository.findById(contentId);
            		Optional.ofNullable(dmrContent).orElseThrow(() -> new ResourceNotFoundException("DmrContent not found for this id :: " + contentId));
            		dmrContentRepository.delete(dmrContent);
            		Map<String, Boolean> response = new HashMap<>();
            		response.put("DmrContent deleted", Boolean.TRUE);
            		return response;
           
        
    }

    @RequestMapping(value={"/storage/dmrcontent/{contentId}"}, method={RequestMethod.GET})
    public ResponseEntity<DmrContent> getDmrContent(@PathVariable(value="contentId") Long  contentId) 
    	throws ResourceNotFoundException {
    	DmrContent DmrContent = dmrContentRepository.findById(contentId);
            return ResponseEntity.ok().body(DmrContent);
    }
	
	
}

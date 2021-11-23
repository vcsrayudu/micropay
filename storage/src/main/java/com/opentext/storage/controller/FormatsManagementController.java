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
import com.opentext.storage.model.DmFormat;
import com.opentext.storage.repository.FormatRepository;

@RestController
public class FormatsManagementController {
	@Autowired
    private FormatRepository formatRepository;
    private static final Logger logger = LoggerFactory.getLogger(FormatsManagementController.class);
   

    @RequestMapping(value={"/formats"},method={RequestMethod.GET})
    public List<DmFormat> getAllformats() {
    	return formatRepository.findAll();
    }

    @RequestMapping(value={"/formats"}, method={RequestMethod.POST}, headers={"Accept=application/json,application/xml,text/plain"})
    public DmFormat createFormat(@RequestBody DmFormat format) {
    	return formatRepository.save(format);
    }

    @RequestMapping(value={"/formats/{formatId}"}, method={RequestMethod.PUT})
    public ResponseEntity<DmFormat> updateFormat(@PathVariable(value = "formatId") Long formatId,
    	@Valid @RequestBody DmFormat inputformat) throws ResourceNotFoundException {
    		DmFormat format = formatRepository.findById(formatId);
    				Optional.ofNullable(format).orElseThrow(() -> new ResourceNotFoundException("Format not found for this id :: " + formatId));

    		format.setFormatName(inputformat.getFormatName());
    		//format.setFirstName(inputformat.getFirstName());
    		final DmFormat updatedformat= formatRepository.save(format);
    		return ResponseEntity.ok(updatedformat);
    }

    @RequestMapping(value={"/formats/{formatId}"}, method={RequestMethod.DELETE})
    public Map<String, Boolean> delete(@PathVariable(value="formatId") Long formatId) 
    	throws ResourceNotFoundException {
            DmFormat format = formatRepository.findById(formatId);
            		Optional.ofNullable(format).orElseThrow(() -> new ResourceNotFoundException("format not found for this id :: " + formatId));
            		formatRepository.delete(format);
            		Map<String, Boolean> response = new HashMap<>();
            		response.put("deleted", Boolean.TRUE);
            		return response;
           
        
    }

    @RequestMapping(value={"/formats/{formatid}"}, method={RequestMethod.GET})
    public ResponseEntity<DmFormat> getFormat(@PathVariable(value="formatid") Long  formatId) 
    	throws ResourceNotFoundException {
            DmFormat format = formatRepository.findById(formatId);
            		Optional.ofNullable(format).orElseThrow(() -> new ResourceNotFoundException("format not found for this id :: " + formatId));
            return ResponseEntity.ok().body(format);
    }
	
}

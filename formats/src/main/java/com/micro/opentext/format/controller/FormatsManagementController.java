package com.micro.opentext.format.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.micro.opentext.format.exceptions.ResourceNotFoundException;
import com.micro.opentext.format.model.DmFormat;
import com.micro.opentext.format.repository.FormatRepository;
import com.micro.opentext.format.services.FormatManagementService;

@RestController
public class FormatsManagementController {
	@Autowired
    private FormatRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(FormatsManagementController.class);
   

    @RequestMapping(value={"/users"},method={RequestMethod.GET})
    public List<DmFormat> getAllUsers() {
    	return userRepository.findAll();
    }

    @RequestMapping(value={"/users"}, method={RequestMethod.POST}, headers={"Accept=application/json,application/xml,text/plain"})
    public DmFormat createUser(@RequestBody DmFormat user) {
    	return userRepository.save(user);
    }

    @RequestMapping(value={"/users/{userId}"}, method={RequestMethod.PUT})
    public ResponseEntity<DmFormat> updateUser(@PathVariable(value = "userId") Long userId,
    	@Valid @RequestBody DmFormat inputUser) throws ResourceNotFoundException {
    		DmFormat user = userRepository.findById(userId);
    				Optional.ofNullable(user).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + userId));

    		//user.setEmailId(inputUser.getEmailId());
    		//user.setUserName(inputUser.getUserName());
    		//user.setFirstName(inputUser.getFirstName());
    		final DmFormat updatedUser= userRepository.save(user);
    		return ResponseEntity.ok(updatedUser);
    }

    @RequestMapping(value={"/users/{userId}"}, method={RequestMethod.DELETE})
    public Map<String, Boolean> delete(@PathVariable(value="userId") Long userId) 
    	throws ResourceNotFoundException {
            DmFormat user = userRepository.findById(userId);
            		Optional.ofNullable(user).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
            		userRepository.delete(user);
            		Map<String, Boolean> response = new HashMap<>();
            		response.put("deleted", Boolean.TRUE);
            		return response;
           
        
    }

    @RequestMapping(value={"/users/{userid}"}, method={RequestMethod.GET})
    public ResponseEntity<DmFormat> getUser(@PathVariable(value="userid") Long  userId) 
    	throws ResourceNotFoundException {
            DmFormat user = userRepository.findById(userId);
            		Optional.ofNullable(user).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
            return ResponseEntity.ok().body(user);
    }
	
}

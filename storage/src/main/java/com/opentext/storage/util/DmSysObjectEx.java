package com.opentext.storage.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

import com.opentext.storage.controller.DmDocumentController;
import com.opentext.storage.exceptions.ResourceNotFoundException;


public class DmSysObjectEx<T> {
	@Autowired
	@LoadBalanced
	 private RestTemplate restTemplate;
	private  Class<T> classtype=null;;
	private static final Logger logger = LoggerFactory.getLogger(DmDocumentController.class);

    public DmSysObjectEx(Class<T> type) {
         this.classtype = type;
    }

    public Class<T> getMyType() {
        return this.classtype;
    }
	
	public T getDmObject(Long docId,String restUrl) throws ResourceNotFoundException
	{
		T document = restTemplate.getForObject(restUrl + "/" + docId, getMyType());
		Optional.ofNullable(document)
		.orElseThrow(() -> new ResourceNotFoundException("dmdocument not found for this id :: " + docId));
		return document;
	}
	
	public T createDmObject(T object,String restUrl) throws ResourceNotFoundException
	{
		T out = restTemplate.postForObject(restUrl, object, getMyType());
		Optional.ofNullable(out)
		.orElseThrow(() -> new ResourceNotFoundException("Object is not able to created : " ));
		logger.debug("Object is created successfully");
		return out;
	}

}

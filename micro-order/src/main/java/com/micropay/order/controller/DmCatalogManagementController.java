package com.micropay.order.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.micropay.order.exceptions.ResourceNotFoundException;
import com.micropay.order.model.DmTransaction;
import com.micropay.order.model.DmCatalog;
import com.micropay.order.services.DmCatalogMgmtService;


@RestController
public class DmCatalogManagementController {
	private static final Logger LOG = Logger.getLogger(DmCatalogManagementController.class.getName());
   @Autowired
   DmCatalogMgmtService catalogService;

    @RequestMapping(value={"/catalogs"},method={RequestMethod.GET})
    public List<DmCatalog> getAllcatalogs() {
    	LOG.log(Level.INFO,"Get all catalogs ");
    	return catalogService.getAllCatalogs();
    }
    

    @RequestMapping(value={"/catalogs/{buyerId}"},method={RequestMethod.GET})
    public DmCatalog getOrder(@PathVariable(value="buyerId") Long buyerId) {
    	return catalogService.getCatalog(buyerId);
    }
    @RequestMapping(value={"/catalogs"}, method={RequestMethod.POST}, headers={"Accept=application/json,application/xml,text/plain"})
    public DmCatalog createorder(@RequestBody DmCatalog catalog) {
    	return catalogService.createCatalog(catalog);
    }

    @RequestMapping(value={"/catalogs/{buyerId}"}, method={RequestMethod.PUT})
    public ResponseEntity<DmCatalog> updateCatalog(@PathVariable(value = "buyerId") Long buyerId,@RequestBody DmCatalog dmCatalog) throws ResourceNotFoundException {
    	DmCatalog order=catalogService.updateCatalog(buyerId,dmCatalog);
    	return ResponseEntity.ok(order);
    }

    
	
}

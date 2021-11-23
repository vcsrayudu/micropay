package com.micropay.product.controller;

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

import com.micropay.product.exceptions.ResourceNotFoundException;
import com.micropay.product.model.DmProduct;
import com.micropay.product.services.DmProductMgmtService;


@RestController
public class DmProductManagementController {
	private static final Logger LOG = Logger.getLogger(DmProductManagementController.class.getName());
   @Autowired
   DmProductMgmtService productService;

    @RequestMapping(value={"/products"},method={RequestMethod.GET})
    public List<DmProduct> getAllProducts() {
    	LOG.log(Level.INFO,"Get all products ");
    	return productService.getAllProducts();
    }
    @RequestMapping(value={"/products/user/{uid}"},method={RequestMethod.GET})
    public List<DmProduct> getAllProductsByUid(@PathVariable(value="uid") Long uid) {
    	return productService.getAllProductsUid(uid);
    }

    @RequestMapping(value={"/products/{oid}"},method={RequestMethod.GET})
    public DmProduct getProduct(@PathVariable(value="productId") Long oid) {
    	return productService.getProduct(oid);
    }
    @RequestMapping(value={"/products"}, method={RequestMethod.POST}, headers={"Accept=application/json,application/xml,text/plain"})
    public DmProduct createProduct(@RequestBody DmProduct product) {
    	return productService.createProduct(product);
    }

    @RequestMapping(value={"/products/{productId}"}, method={RequestMethod.PUT})
    public ResponseEntity<DmProduct> updateProduct(@PathVariable(value = "productId") Long oid,@RequestBody DmProduct dmproduct) throws ResourceNotFoundException {
    	DmProduct product=productService.updateProduct(oid,dmproduct);
    	return ResponseEntity.ok(product);
    }

    
	
}

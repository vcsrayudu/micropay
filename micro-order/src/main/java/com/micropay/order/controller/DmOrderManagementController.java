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
import com.micropay.order.model.DmOrder;
import com.micropay.order.services.DmOrderMgmtService;


@RestController
public class DmOrderManagementController {
	private static final Logger LOG = Logger.getLogger(DmOrderManagementController.class.getName());
   @Autowired
   DmOrderMgmtService orderService;

    @RequestMapping(value={"/orders"},method={RequestMethod.GET})
    public List<DmOrder> getAllOrders() {
    	LOG.log(Level.INFO,"Get all orders ");
    	return orderService.getAllOrders();
    }
    @RequestMapping(value={"/orders/user/{uid}"},method={RequestMethod.GET})
    public List<DmOrder> getAllOrdersByUid(@PathVariable(value="uid") Long uid) {
    	return orderService.getAllOrdersUid(uid);
    }

    @RequestMapping(value={"/orders/{oid}"},method={RequestMethod.GET})
    public DmOrder getOrder(@PathVariable(value="orderId") Long oid) {
    	return orderService.getOrder(oid);
    }
    @RequestMapping(value={"/orders"}, method={RequestMethod.POST}, headers={"Accept=application/json,application/xml,text/plain"})
    public DmOrder createorder(@RequestBody DmOrder order) {
    	return orderService.createOrder(order);
    }

    @RequestMapping(value={"/orders/{orderId}"}, method={RequestMethod.PUT})
    public ResponseEntity<DmOrder> updateorder(@PathVariable(value = "orderId") Long oid,@RequestBody DmOrder dmOrder) throws ResourceNotFoundException {
    	DmOrder order=orderService.updateOrder(oid,dmOrder);
    	return ResponseEntity.ok(order);
    }

    
	
}

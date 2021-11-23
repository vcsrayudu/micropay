package com.micropay.order.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micropay.order.model.DmTransaction;
import com.micropay.order.model.DmOrder;
import com.micropay.order.repository.DmTransactionRepository;
import com.micropay.order.repository.DmOrderRepository;

@Service(value = "orderService")
public class DmOrderMgmtServiceImpl implements DmOrderMgmtService {
	@Autowired
	private DmTransactionRepository traxnRepository;
	@Autowired
	private DmOrderRepository orderRepository;
	
	 private static final Logger LOG = Logger.getLogger(DmOrderMgmtServiceImpl.class.getName());

	@Override
	public List<DmOrder> getAllOrders() {
		// TODO Auto-generated method stub
		
		return orderRepository.findAll();
	}

	@Override
	public double getBalance(Long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DmOrder getOrder(Long orderId) {
		// TODO Auto-generated method stub
		return orderRepository.findByOrderId(orderId);
	}

	@Override
	public DmOrder createOrder(DmOrder order) {
		// TODO Auto-generated method stub
		return orderRepository.save(order);
	}

	@Override
	public DmOrder updateOrder(long oid, DmOrder order) {
		// TODO Auto-generated method stub
		DmOrder oriOrder=orderRepository.findByOrderId(oid);
		
		return null;
	}

	@Override
	public List<DmOrder> getAllOrdersUid(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

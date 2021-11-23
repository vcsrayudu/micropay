package com.micropay.order.services;

import java.util.List;

import com.micropay.order.model.DmTransaction;
import com.micropay.order.model.DmOrder;

public interface DmOrderMgmtService {
public List<DmOrder> getAllOrders();

public double getBalance(Long userId);
public DmOrder getOrder(Long orderId);
public DmOrder createOrder(DmOrder order);
public DmOrder updateOrder(long oid, DmOrder order);

public List<DmOrder> getAllOrdersUid(Long uid);

}

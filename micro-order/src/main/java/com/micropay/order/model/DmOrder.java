package com.micropay.order.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dm_order")
public class DmOrder {
	private long buyerId;
	private String orderDesc;
	@Id
	private long orderId;
	private Set<DmItem> itemList;
	public Set<DmItem> getItemList() {
		return itemList;
	}
	public void setItemList(Set<DmItem> itemList) {
		this.itemList = itemList;
	}
	public long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
		public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	
	
	

}

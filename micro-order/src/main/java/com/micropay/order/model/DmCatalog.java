package com.micropay.order.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "dm_catalog")
public class DmCatalog {
	@Id
	private long buyerId;
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
	


	
	
	
	

}

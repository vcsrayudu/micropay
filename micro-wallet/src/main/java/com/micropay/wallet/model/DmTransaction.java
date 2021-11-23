package com.micropay.wallet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dm_transaction")
public class DmTransaction {
private long senderId;
private long id;
private long parentId;
private String description;
private String status;
@Column(name="status")
public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

@Column(name="parent_id")
public long getParentId() {
	return parentId;
}

public void setParentId(long parentId) {
	this.parentId = parentId;
}
@Column(name="description")
public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}
private long reciverId;
@Column(name="reciver_id")
public long getReciverId() {
	return reciverId;
}

public void setReciverId(long reciverId) {
	this.reciverId = reciverId;
}

@Column(name="sender_id")
public long getSenderId() {
	return senderId;
}

public void setSenderId(long senderId) {
	this.senderId = senderId;
}
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
@Column(name="amount")
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
@Column(name="traxn_date")
public Date getTraxnDate() {
	return traxnDate;
}
public void setTraxnDate(Date traxnDate) {
	this.traxnDate = traxnDate;
}
private double amount;
private Date traxnDate;

}
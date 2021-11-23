package com.opentext.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
@Entity
@Table(name = "dm_sys_object")
@Inheritance(
	    strategy = InheritanceType.JOINED
	)
public class DmSysObject {
	 private String objectName;
	 private long id;
	 private String ownerId;
	 @Column(name = "owner_id", nullable = true)
	 public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "object_name", nullable = false)
	 public String getObjectName() {
	 	return objectName;
	 }
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 @Column(name = "id", unique=true,nullable=false)
	 public long getId() {
		 return id;
	}
	public void setId(long storeId) {
	 	this.id = storeId;
	 }
	 public void setObjectName(String objectName) {
	 	this.objectName=objectName;
	 }
	
	
}

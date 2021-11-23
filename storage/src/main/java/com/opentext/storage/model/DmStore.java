package com.opentext.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "dm_store")
public class DmStore extends DmSysObject{

private String storagePath;
@Column(name = "storage_path", nullable = false)
public String getStoragePath() {
	return storagePath;
}
public void setStoragePath(String storagePath) {
	this.storagePath = storagePath;
}


public void setStringParam(String paramName, String paramValue)
{
	switch(paramName)
	{
	case "object_name":
		setObjectName(paramValue);
		break;
	case "storage_path":
		this.storagePath=paramValue;
		break;
	default:
		break;
	}
}
public String getStringParam(String paramName)
{
	switch(paramName)
	{
	case "object_name":
		return getObjectName();
		
	case "storage_path":
		return this.storagePath;
		
	default:
		return null;
		
	}
	
}


}

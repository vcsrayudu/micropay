package com.micro.opentext.format.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dm_format")
public class DmFormat {
private String formatName;
private long id;

@Column(name = "object_name", nullable = false)
public String getFormatName() {
	return formatName;
}
public void setFormatName(String formatName) {
	this.formatName = formatName;
}
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}

}

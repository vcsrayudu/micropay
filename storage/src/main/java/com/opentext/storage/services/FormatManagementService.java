package com.opentext.storage.services;

import java.util.ArrayList;

import com.opentext.storage.model.DmFormat;


public interface FormatManagementService {
public ArrayList<DmFormat> getAllFormats();
public DmFormat getFormat(Long formatId);
public boolean createFormat(DmFormat format);
public boolean updateFormat(DmFormat format);
public boolean deleteFormat(Long formatId);

}

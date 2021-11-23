package com.micro.opentext.format.services;

import java.util.ArrayList;

import com.micro.opentext.format.model.DmFormat;

public interface FormatManagementService {
public ArrayList<DmFormat> getAllUsers();
public DmFormat getUser(Long userId);
public boolean createUser(DmFormat user);
public boolean updateUser(DmFormat user);
public boolean deleteUser(Long userId);

}

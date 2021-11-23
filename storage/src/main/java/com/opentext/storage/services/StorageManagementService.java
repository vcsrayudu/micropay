package com.opentext.storage.services;

import java.util.ArrayList;

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmStore;

public interface StorageManagementService {
public ArrayList<DmStore> getAllStores();
public DmStore getStore(Long StoreId) throws ResourceNotFoundException;
public boolean createStore(DmStore Store);
public boolean updateStore(DmStore Store);
public boolean deleteStore(String StoreId);

}

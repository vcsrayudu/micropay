package com.opentext.storage.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opentext.storage.exceptions.ResourceNotFoundException;
import com.opentext.storage.model.DmStore;
import com.opentext.storage.repository.StorageRepository;
@Service(value="storeService")
public class StorageManagementServiceImpl implements StorageManagementService {
	@Autowired
    private StorageRepository storageRepository;
	@Override
	public ArrayList<DmStore> getAllStores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DmStore getStore(Long storageId) throws ResourceNotFoundException {
		DmStore storage = storageRepository.findById(storageId);
		Optional.ofNullable(storage).orElseThrow(() -> new ResourceNotFoundException("storage not found for this id :: " + storageId));
		return storage;
	}

	@Override
	public boolean updateStore(DmStore Store) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteStore(String StoreId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createStore(DmStore Store) {
		// TODO Auto-generated method stub
		return false;
	}

}

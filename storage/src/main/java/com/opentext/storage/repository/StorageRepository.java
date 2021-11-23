package com.opentext.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opentext.storage.model.DmStore;

@Repository
public interface StorageRepository extends JpaRepository<DmStore, Integer> {

	DmStore findById(long storeId);
	

}

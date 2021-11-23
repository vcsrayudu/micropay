package com.opentext.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opentext.storage.model.DmrContent;
@Repository
public interface ContentRepository extends JpaRepository<DmrContent, Integer>{
	DmrContent findById(long dmrId);

	
}

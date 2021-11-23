package com.opentext.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opentext.storage.model.DmDocument;

@Repository
public interface DocumentRepository extends JpaRepository<DmDocument, Integer> {

	DmDocument findById(long documentId);

}

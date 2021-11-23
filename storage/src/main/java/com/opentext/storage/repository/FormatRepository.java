
package com.opentext.storage.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.opentext.storage.model.DmFormat;

@Repository
public interface FormatRepository extends JpaRepository<DmFormat, Integer>{

	DmFormat findById(long formatId);
	  

}

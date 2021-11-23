
package com.micro.opentext.format.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.opentext.format.model.DmFormat;
@Repository
public interface FormatRepository extends JpaRepository<DmFormat, Integer>{

	DmFormat findById(Long formatId);
	  

}

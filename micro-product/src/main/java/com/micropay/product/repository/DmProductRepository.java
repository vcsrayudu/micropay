
package com.micropay.product.repository;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.micropay.product.model.DmProduct;
@Transactional
public interface DmProductRepository extends MongoRepository<DmProduct, Long> {
	
	
	  
	 
	  public DmProduct findByProductId(Long oid);


}

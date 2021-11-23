
package com.micropay.order.repository;
import java.util.Set;

import com.micropay.order.model.DmCatalog;
import com.micropay.order.model.DmOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface DmCatalogRepository extends MongoRepository<DmCatalog, Long> {
	
	
	  //public User findByDeparture(String departure);
	  public DmCatalog findByBuyerId(Long uid);
	 


}

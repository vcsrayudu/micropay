
package com.micropay.order.repository;
import java.util.Set;


import com.micropay.order.model.DmOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface DmOrderRepository extends MongoRepository<DmOrder, Long> {
	
	
	  //public User findByDeparture(String departure);
	  public Set<DmOrder> findByBuyerId(Long uid);
	 
	  public DmOrder findByOrderId(Long oid);


}


package com.micropay.product.repository;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.micropay.product.model.DmItem;
import com.micropay.product.model.DmProduct;
@Transactional
public interface DmItemRepository extends MongoRepository<DmItem, Long> {
	
	
	 public Set<DmItem> findBySellerId(Long sid);
	 
	  public DmItem findByItemId(Long itemId);


}

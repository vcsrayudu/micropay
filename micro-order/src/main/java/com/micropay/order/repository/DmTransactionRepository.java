
package com.micropay.order.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.micropay.order.model.DmTransaction;
@Repository
public interface DmTransactionRepository extends JpaRepository<DmTransaction, Integer>{

	DmTransaction findById(Long tranxId);
	@Query("SELECT t FROM DmTransaction t WHERE t.senderId = :senderId")
	List<DmTransaction> getAllTraxnByUid(long senderId);

}

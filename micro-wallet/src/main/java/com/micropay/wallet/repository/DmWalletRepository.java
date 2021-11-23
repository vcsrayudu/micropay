
package com.micropay.wallet.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micropay.wallet.model.DmWallet;
@Repository
public interface DmWalletRepository extends JpaRepository<DmWallet, Integer>{

	DmWallet findById(Long userId);
	  

}

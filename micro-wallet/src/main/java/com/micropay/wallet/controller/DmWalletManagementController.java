package com.micropay.wallet.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.micropay.wallet.exceptions.ResourceNotFoundException;
import com.micropay.wallet.model.DmTransaction;
import com.micropay.wallet.model.DmWallet;

import com.micropay.wallet.services.DmWalletMgmtService;


@RestController
public class DmWalletManagementController {
	private static final Logger LOG = Logger.getLogger(DmWalletManagementController.class.getName());
   @Autowired
   DmWalletMgmtService walletService;

    @RequestMapping(value={"/wallets/transactions/{uid}"},method={RequestMethod.GET})
    public List<DmTransaction> getAllTransactionsForUser(@PathVariable(value="uid") Long uid) {
    	LOG.log(Level.INFO,"Get all transactions for user : "+uid);
    	return walletService.getAllTransactionsByUid(uid);
    }
    @RequestMapping(value={"/wallets/transactions"},method={RequestMethod.GET})
    public List<DmTransaction> getAllTransactions() {
    	return walletService.getAllTransactions();
    }

    @RequestMapping(value={"/wallets/{uid}"},method={RequestMethod.GET})
    public DmWallet getWallet(@PathVariable(value="uid") Long uid) {
    	return walletService.getWallet(uid);
    }
    @RequestMapping(value={"/wallets"}, method={RequestMethod.POST}, headers={"Accept=application/json,application/xml,text/plain"})
    public DmWallet createWallet(@RequestBody DmWallet wallet) {
    	return walletService.createWallet(wallet);
    }

    @RequestMapping(value={"/wallets/{uid}"}, method={RequestMethod.PUT})
    public ResponseEntity<DmTransaction> updateWallet(@PathVariable(value = "uid") Long uid,@RequestBody DmTransaction dmTrans) throws ResourceNotFoundException {
    	DmTransaction trxn=walletService.updateBalance(uid,dmTrans);
    	return ResponseEntity.ok(trxn);
    }

    
	
}

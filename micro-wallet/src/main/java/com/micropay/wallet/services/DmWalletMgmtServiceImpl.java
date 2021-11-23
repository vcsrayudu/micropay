package com.micropay.wallet.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micropay.wallet.model.DmTransaction;
import com.micropay.wallet.model.DmWallet;
import com.micropay.wallet.repository.DmTransactionRepository;
import com.micropay.wallet.repository.DmWalletRepository;

@Service(value = "walletService")
public class DmWalletMgmtServiceImpl implements DmWalletMgmtService {
	@Autowired
	private DmTransactionRepository traxnRepository;
	@Autowired
	private DmWalletRepository walletRepository;
	
	 private static final Logger logger = Logger.getLogger(DmWalletMgmtServiceImpl.class.getName());
	@Override
	public List<DmTransaction> getAllTransactionsByUid(Long uid) {
		logger.log(Level.INFO,"Retrieve All transactions for user : "+uid);
		return traxnRepository.getAllTraxnByUid(uid);

	}

	@Override
	public List<DmTransaction> getAllTransactions() {
		logger.log(Level.INFO,"Retrieve All transactions ");
		return traxnRepository.findAll();

	}

	@Override
	public double getBalance(Long userId) {
		// TODO Auto-generated method stub
		DmWallet wallet = walletRepository.findById(userId);
		logger.log(Level.INFO,"Get balance for user : "+wallet.getUserId());
		return wallet.getAmount();
	}

	@Override
	public DmWallet getWallet(Long userId) {
		logger.log(Level.INFO,"Get Wallet for user : "+userId);
		return walletRepository.findById(userId);
	}

	@Override
	public DmWallet createWallet(DmWallet wallet) {
		DmTransaction dmTrans = new DmTransaction();
		dmTrans.setAmount(wallet.getAmount());
		dmTrans.setDescription("New Account created for User: " + wallet.getUserId());
		dmTrans.setSenderId(wallet.getUserId());
		dmTrans.setStatus("COMPLETED");
		dmTrans.setParentId(0);
		DmWallet walletTmp = walletRepository.save(wallet);
		dmTrans.setTraxnDate(new Date());
		traxnRepository.save(dmTrans);
		return walletTmp;
	}

	@Override
	public DmTransaction updateBalance(long uid, DmTransaction dmTrans) {
		// TODO Auto-generated method stub
		
		Date date = new Date();
		dmTrans.setTraxnDate(date);
		double amount = dmTrans.getAmount();

		dmTrans.setStatus("PENDING");
		dmTrans = traxnRepository.save(dmTrans);
		logger.log(Level.INFO,"Creating transaction for user : "+uid+" with status pending : ");
		DmWallet wallet = walletRepository.findById(uid);
		logger.log(Level.INFO,"Get wallet user : "+uid);
		if (!wallet.isLock()) {
			wallet.setLock(true);
			wallet = walletRepository.save(wallet);
			double balance = wallet.getAmount();

			if (amount > 0) {
				wallet.setAmount(amount + balance);
				dmTrans.setDescription("Amount Credited : " + amount);
				wallet.setLock(false);
				walletRepository.save(wallet);
				dmTrans.setStatus("COMPLETED");
				logger.log(Level.INFO,"Successfully Added amount : "+amount+" to user wallet : "+uid);

			} else if (balance >= (-1 * amount)) {
				wallet.setAmount(amount + balance);
				dmTrans.setDescription("Amount Debited : " + amount);
				wallet.setLock(false);
				walletRepository.save(wallet);
				dmTrans.setStatus("COMPLETED");
				logger.log(Level.INFO,"Successfully deducted amount : "+amount+" from user wallet : "+uid);

			} else {
				logger.log(Level.INFO,"Balance is in sufficient for user : "+uid);
				dmTrans.setStatus("INSUFFICIANT_BALANCE");
			}

		} else {
			logger.log(Level.INFO,"User wallet is locked : "+uid);
			dmTrans.setStatus("LOCKED");
		}
		date = new Date();
		dmTrans.setTraxnDate(date);
		dmTrans = traxnRepository.save(dmTrans);
		return dmTrans;
	}

}

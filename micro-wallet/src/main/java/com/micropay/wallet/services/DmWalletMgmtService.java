package com.micropay.wallet.services;

import java.util.List;

import com.micropay.wallet.model.DmTransaction;
import com.micropay.wallet.model.DmWallet;

public interface DmWalletMgmtService {
public List<DmTransaction> getAllTransactionsByUid(Long uid);
public List<DmTransaction> getAllTransactions();
public double getBalance(Long userId);
public DmWallet getWallet(Long userId);
public DmWallet createWallet(DmWallet wallet);
public DmTransaction updateBalance(long uid, DmTransaction dmTrans);

}

package com.micropay.order.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micropay.order.model.DmTransaction;
import com.micropay.order.model.DmCatalog;
import com.micropay.order.repository.DmTransactionRepository;
import com.micropay.order.repository.DmCatalogRepository;
import com.micropay.order.repository.DmCatalogRepository;

@Service(value = "catalogService")
public class DmCatalogMgmtServiceImpl implements DmCatalogMgmtService {
	@Autowired
	private DmCatalogRepository catalogRepository;
	
	 private static final Logger LOG = Logger.getLogger(DmCatalogMgmtServiceImpl.class.getName());

	@Override
	public List<DmCatalog> getAllCatalogs() {
		// TODO Auto-generated method stub
		
		return catalogRepository.findAll();
	}

	
	@Override
	public DmCatalog getCatalog(Long uid) {
		// TODO Auto-generated method stub
		return catalogRepository.findByBuyerId(uid);
	}

	@Override
	public DmCatalog createCatalog(DmCatalog catalog) {
		// TODO Auto-generated method stub
		return catalogRepository.save(catalog);
	}

	@Override
	public DmCatalog updateCatalog(long uid, DmCatalog Catalog) {
		// TODO Auto-generated method stub
		DmCatalog oriCatalog=catalogRepository.findByBuyerId(uid);
		
		return null;
	}


	
}

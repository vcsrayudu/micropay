package com.micropay.order.services;

import java.util.List;

import com.micropay.order.model.DmTransaction;
import com.micropay.order.model.DmCatalog;

public interface DmCatalogMgmtService {
public List<DmCatalog> getAllCatalogs();



public DmCatalog getCatalog(Long uid);
public DmCatalog createCatalog(DmCatalog catalog);
public DmCatalog updateCatalog(long uid, DmCatalog catalog);


}

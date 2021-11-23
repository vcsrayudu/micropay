package com.micropay.product.services;

import java.util.List;

import com.micropay.product.model.DmProduct;


public interface DmProductMgmtService {
public List<DmProduct> getAllProducts();

public double getBalance(Long userId);
public DmProduct getProduct(Long ProductId);
public DmProduct createProduct(DmProduct Product);
public DmProduct updateProduct(long oid, DmProduct Product);

public List<DmProduct> getAllProductsUid(Long uid);

}

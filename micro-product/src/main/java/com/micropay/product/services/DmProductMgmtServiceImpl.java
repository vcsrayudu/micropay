package com.micropay.product.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micropay.product.model.DmProduct;
import com.micropay.product.repository.DmProductRepository;


@Service(value = "productService")
public class DmProductMgmtServiceImpl implements DmProductMgmtService {
	@Autowired
	private DmProductRepository productRepository;
	
	 private static final Logger LOG = Logger.getLogger(DmProductMgmtServiceImpl.class.getName());

	@Override
	public List<DmProduct> getAllProducts() {
		// TODO Auto-generated method stub
		
		return productRepository.findAll();
	}

	@Override
	public double getBalance(Long userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DmProduct getProduct(Long productId) {
		// TODO Auto-generated method stub
		return productRepository.findByProductId(productId);
	}

	@Override
	public DmProduct createProduct(DmProduct Product) {
		// TODO Auto-generated method stub
		return productRepository.save(Product);
	}

	@Override
	public DmProduct updateProduct(long pid, DmProduct product) {
		// TODO Auto-generated method stub
		DmProduct oriProduct=productRepository.findByProductId(pid);
		
		return null;
	}

	@Override
	public List<DmProduct> getAllProductsUid(Long uid) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

package com.model2.mvc.service.product.impl;

import java.util.Map;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;


public class ProductServiceImpl implements ProductService{
	
	///Field
	private ProductDao productDao;
	
	///Constructor
	public ProductServiceImpl() {
		productDao=new ProductDao();
	}
	///Method
	public void addProduct(Product product) throws Exception {
		productDao.insertProduct(product);
	}


	public Product getProduct(int productNo) throws Exception {
//		System.out.println("impl productNO : "+productNo);
//		System.out.println("impl productDAO.findProduct(productNo) : "+productDAO.findProduct(productNo));
		return productDao.findProduct(productNo);
	}

	public Map<String,Object> getProductList(Search search) throws Exception {
		return productDao.getProductList(search);
	}

	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}

}
package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;







public class PurchaseServiceImpl implements PurchaseService{
	
	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		System.out.println("impl add purchase"+purchase);
		purchaseDAO.insertPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int ProdNo) throws Exception {
		return purchaseDAO.findPurchase2(ProdNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		// TODO Auto-generated method stub
		return purchaseDAO.getPurchaseList(search,buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		return null;
	}
	@Override
	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);	
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println("impl 여기 들어오나요");
		purchaseDAO.updateTranCode(purchase);
		
	}
}
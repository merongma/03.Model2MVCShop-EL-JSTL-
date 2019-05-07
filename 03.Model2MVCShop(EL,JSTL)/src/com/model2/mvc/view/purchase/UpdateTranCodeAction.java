package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		
		System.out.println("여기 updatetranconde action 들어오냠");
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		String tranCode =request.getParameter("tranCode");
		System.out.println("updatetrancode action에 넘어오는 prodno와 trancode 값 확인 : "+tranNo+"/"+tranCode);

		
		PurchaseService service1 = new PurchaseServiceImpl();
		Purchase purchase =service1.getPurchase(tranNo);
		
		request.setAttribute("purchase", purchase);
		System.out.println("purchase 값 확인 :"+purchase);
		
		Product product = new Product();
		product.setProdNo(purchase.getPurchaseProd().getProdNo());
		
		purchase.setTranCode(request.getParameter("tranCode"));
		purchase.setPurchaseProd(product);

		
		PurchaseService service=new PurchaseServiceImpl();
		service.updateTranCode(purchase);
		
		

		return  "forward:/listPurchase.do?tranNo="+tranNo;
	}
}
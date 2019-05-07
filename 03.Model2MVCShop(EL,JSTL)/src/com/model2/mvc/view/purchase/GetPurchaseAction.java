package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;




public class GetPurchaseAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		System.out.println("getproductAction에 들어오는 tranNo");
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("getproductAction에 들어오는 tranNo");
		
		PurchaseService service=new PurchaseServiceImpl();
		Purchase purchase=service.getPurchase(tranNo);
		
		
		request.setAttribute("purchase", purchase);
		System.out.println("getproductAction");
			
		return "forward:/purchase/getPurchaseView.jsp";
		
	}
}
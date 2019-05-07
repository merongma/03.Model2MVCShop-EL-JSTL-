package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;



public class AddPurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String buyerId = request.getParameter("buyerId");
		
		System.out.println("addpurchaseview.jsp에서 넘어오는 buyerId 값 확인 : " +buyerId);
		System.out.println("addpurchaseview.jsp에서 넘어오는 prodNo 값 확인 : " +prodNo);
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		request.setAttribute("product", product);
		UserService service2 = new UserServiceImpl();
		User user = service2.getUser(buyerId);
		request.setAttribute("user", user);
	

		
		Purchase purchase =new Purchase();
		purchase.setPurchaseProd((Product) request.getAttribute("product"));
		purchase.setBuyer((User) request.getAttribute("user"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		
				
		System.out.println(purchase);

		
		PurchaseService service3=new PurchaseServiceImpl();
		service3.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchaseViewResult.jsp";
		
		
	}
}
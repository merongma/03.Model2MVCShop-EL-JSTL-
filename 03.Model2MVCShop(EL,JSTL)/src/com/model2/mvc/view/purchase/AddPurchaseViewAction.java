package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;



public class AddPurchaseViewAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		int prodNo =Integer.parseInt( request.getParameter("prod_no"));
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		request.setAttribute("product", product);
		System.out.println(product);
		
		
		return "forward:/purchase/addPurchaseView.jsp";
		
	}
}

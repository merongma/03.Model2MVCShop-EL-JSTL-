package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;




public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		
		
		int prodNo =Integer.parseInt( request.getParameter("prodNo"));
		
		
		Product product = new Product();
		product.setProdNo(prodNo);
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));	
		product.setFileName(request.getParameter("fileName"));
		
		//productVO.setRegDate((request.getParameter("regDate"));
		
		System.out.println("updateproduct action");
		
		ProductService service=new ProductServiceImpl();
		service.updateProduct(product);
		Product product2 =  service.getProduct(prodNo);
		
		//productVO2.getRegDate()
		product.setRegDate(product2.getRegDate());
		
		request.setAttribute("product", product);
		//request.setAttribute("vo2", productVO);
		
		
		System.out.println("request¿¡ ÀÖ´Â product "+product);
		

		return "forward:/product/updateProduct.jsp";
	}
}
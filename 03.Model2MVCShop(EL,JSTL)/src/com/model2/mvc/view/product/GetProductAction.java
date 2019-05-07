package com.model2.mvc.view.product;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;




public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		System.out.println("GetProductAction 들어오나용?");
		
		System.out.println(request.getParameter("prodNo"));
		
		int prodNo =Integer.parseInt( request.getParameter("prodNo"));

		System.out.println("GetProductAction prodNo : "+prodNo);
		
		request.setCharacterEncoding("EUC_KR");
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();

		Cookie cookieBox[] = request.getCookies();
		Cookie cookie =null;
		
		if(cookieBox!=null) {
			for (int i = 0; i < cookieBox.length; i++) {
				if (cookieBox[i].getName().equals("history")) {
					cookie = new Cookie("history",cookieBox[i].getValue() + "," + prodNo); 
					break;
				}
			}
		}else {
			 cookie = new Cookie("history", String.valueOf(prodNo)); 
		}

			
		if(cookie==null) {
			cookie = new Cookie("history", String.valueOf(prodNo));
		}
		
		cookie.setMaxAge(-1);
		
		System.out.println("쿠키값 확인 " +cookie.getValue());
		response.addCookie(cookie);
		
		ProductService service=new ProductServiceImpl();
		Product product=service.getProduct(prodNo);
		
		
		request.setAttribute("product", product);
		
		System.out.println(" GetProductAction 메뉴값 확인 : "+request.getParameter("menu"));
		
		if( request.getParameter("menu").equals("manage") ) {
			
			return "forward:/product/updateProductView.jsp";
			
		}else {
		
		return "forward:/product/getProduct.jsp";
		}
	}
}
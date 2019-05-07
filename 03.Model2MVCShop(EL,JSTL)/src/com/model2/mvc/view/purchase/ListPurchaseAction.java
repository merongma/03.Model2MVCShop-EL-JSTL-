package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;



public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		System.out.println("���� listpurchaseaction ������??");
		Search search = new Search();

		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage"));

		search.setCurrentPage(currentPage);
		//search.setSearchCondition(request.getParameter("searchCondition"));
		//search.setSearchKeyword(request.getParameter("searchKeyword"));

		// web.xml meta-data �� ���� ��� ����
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		//buyerid ����
		User user = (User)request.getSession().getAttribute("user");
		String buyerId=user.getUserId();
		System.out.println("session buyerid : " +buyerId);
	
		System.out.println("search�� Ȯ��1 : "+search);
		// Business logic ����
		PurchaseService productService = new PurchaseServiceImpl();
		Map<String, Object> map = productService.getPurchaseList(search, buyerId);
		
		System.out.println("search�� Ȯ��2 : "+search);

		Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::" + resultPage);

		// Model �� View ����
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);

		return "forward:/purchase/listPurchase.jsp";
	}
}
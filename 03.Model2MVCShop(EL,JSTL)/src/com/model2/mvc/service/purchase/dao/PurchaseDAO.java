package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;



public class PurchaseDAO {
	
	public PurchaseDAO(){
	}

	public void insertPurchase(Purchase purchase) throws Exception {
			
			Connection con = DBUtil.getConnection();
	
			String sql = "INSERT INTO transaction VALUES (seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,1,sysdate,?)";
		
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
			stmt.setString(2, purchase.getBuyer().getUserId());
			stmt.setString(3, purchase.getPaymentOption());
			stmt.setString(4, purchase.getReceiverName());
			stmt.setString(5, purchase.getReceiverPhone());
			stmt.setString(6, purchase.getDivyAddr());
			stmt.setString(7, purchase.getDivyRequest());
			stmt.setString(8, purchase.getDivyDate());
			stmt.executeUpdate();
			System.out.println("insert 들어오나욤");
			System.out.println("purchase"+purchase);
			stmt.close();
			con.close();
		}
	
	public Map<String,Object> getPurchaseList(Search search,String buyerId) throws Exception {
		System.out.println("dao 값 확인"+buyerId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE buyer_id =";
		sql+= "\'"+ buyerId+"\'";
		
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: sql ::"+sql);
		System.out.println("PuchaseDAO :: totalCount ::"+totalCount);
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt= con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		
		System.out.println(search);
		

		List<Purchase> list = new ArrayList<Purchase>();
		while (rs.next()){
			
				User user = new User();
				user.setUserId(rs.getString("buyer_Id"));
				
				Product product = new Product();
				product.setProdNo(rs.getInt("PROD_NO"));
				
				Purchase vo = new Purchase();
				vo.setBuyer(user);
				vo.setPurchaseProd(product);
				vo.setTranNo(rs.getInt("TRAN_NO"));
				vo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				vo.setReceiverName(rs.getString("RECEIVER_NAME"));
				vo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				vo.setDivyAddr(rs.getString("DEMAILADDR"));
				vo.setDivyRequest(rs.getString("DLVY_REQUEST"));
				vo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				vo.setOrderDate(rs.getDate("ORDER_DATA"));
				vo.setDivyDate(rs.getString("DLVY_DATE"));
				list.add(vo);
				
			}
		System.out.println("add한 list :"+list);
		
		map.put("totalCount", new Integer(totalCount));
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();
			
		return map;
	}
	
	public Purchase findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE TRAN_NO=?";

		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = null;
		while (rs.next()) {
			
			User user = new User();
			user.setUserId(rs.getString("buyer_Id"));
			
			Product product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			
		
			purchase = new Purchase();
			purchase.setBuyer(user);
			purchase.setPurchaseProd(product);
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DEMAILADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setOrderDate(rs.getDate("ORDER_DATA"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));
			
		}
		System.out.println("findpurchase 실행"+purchase);
		
		con.close();

		return purchase;
	}
	
	public Purchase findPurchase2(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE PROD_NO=?";

		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = null;
		while (rs.next()) {
			
			User user = new User();
			user.setUserId(rs.getString("buyer_Id"));
			
			Product product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			
		
			purchase = new Purchase();
			purchase.setBuyer(user);
			purchase.setPurchaseProd(product);
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DEMAILADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setOrderDate(rs.getDate("ORDER_DATA"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));
			
		}
		System.out.println("findpurchase2 실행"+purchase);
		
		con.close();

		return purchase;
	}

	public void updatePurchase(Purchase purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET PAYMENT_OPTION=?,RECEIVER_NAME=?,RECEIVER_PHONE=?,DEMAILADDR=?, DLVY_REQUEST=?,DLVY_DATE=? where TRAN_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1,purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		stmt.executeUpdate();
		
		con.close();
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		
		System.out.println("updatetrancode 들어오나요");
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET TRAN_STATUS_CODE=?	 WHERE PROD_NO=? ";
		
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getPurchaseProd().getProdNo());
		stmt.executeUpdate();
		
		con.close();
	}
	
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		
		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	// 게시판 currentPage Row 만 return
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("PrdouctDAO :: make SQL :: "+ sql);	
		
		return sql;
	}



	
}
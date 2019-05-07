package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;

public class ProductDao {

	public ProductDao() {
	}

	public void insertProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO product VALUES (seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate)";

		PreparedStatement pStmt = con.prepareStatement(sql);

		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
	}

	public Product findProduct(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product WHERE prod_no=?";

		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);

		ResultSet rs = pStmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setRegDate(rs.getDate("REG_DATE"));

		}
		System.out.println("findproduct 실행" + product);
		
		rs.close();
		pStmt.close();
		con.close();

		return product;
	}

	public Map<String, Object> getProductList(Search search) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();

		String sql = "select p.prod_no, p.prod_name, p.prod_detail, p.manufacture_day, p.price, p.image_file, p.reg_date, t.tran_status_code" + 
				"		from product p, transaction t" + 
				"		where p.prod_no = t.prod_no(+)";

		System.out.println("search.getSearchCondition()" + search.getSearchCondition());
		System.out.println("search.getSearchKeyword()" + search.getSearchKeyword());
		
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")&& !search.getSearchKeyword().equals("")) {
				sql += " AND p.prod_no LIKE '%" + search.getSearchKeyword() + "%' ";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " AND p.prod_name LIKE '%" + search.getSearchKeyword() + "%' ";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " AND p.price LIKE '%" + search.getSearchKeyword() + "%' ";
			}
		}
		sql += " ORDER BY prod_no ";

		System.out.println("ProductDAO :: Original SQL :: " + sql);

		// ==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);

		// ==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		System.out.println(search);

		List<Product> list = new ArrayList<Product>();

		while (rs.next()) {
			Product product = new Product();
			product.setProTranCode(rs.getString("tran_status_code"));
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setFileName(rs.getString("image_file"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setRegDate(rs.getDate("reg_date"));
			System.out.println("trancode값 확인" +rs.getString("TRAN_STATUS_CODE"));
			list.add(product);
		}
		
		
		System.out.println("add한 list: "+list);
		
		// ==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		// ==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

	public void updateProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product SET PROD_NAME=?,PROD_DETAIL=?,	MANUFACTURE_DAY=?,PRICE=?, IMAGE_FILE=? WHERE PROD_NO=?";

		PreparedStatement pStmt = con.prepareStatement(sql);

		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.setInt(6, product.getProdNo());
		pStmt.executeUpdate();

		pStmt.close();
		con.close();
	}

	// 게시판 Page 처리를 위한 전체 Row(totalCount) return
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
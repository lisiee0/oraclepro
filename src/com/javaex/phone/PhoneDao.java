package com.javaex.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PhoneDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver= "oracle.jdbc.driver.OracleDriver";
	private String url= "jdbc:oracle:thin:@localhost:1521:xe";
	private String id= "phonedb";
	private String pw= "phonedb";
		
	public PhoneDao() {
		
	}
		
	private void getConnection() {	
		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, id, pw);
			
		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}   
	}
	
	
	private void close() {
	    try {
	        if (rs != null) {
	            rs.close();
	        }                
	        if (pstmt != null) {
	            pstmt.close();
	        }
	        if (conn != null) {
	            conn.close();
	        }
	    } catch (SQLException e) {
	        System.out.println("error:" + e);
	    }
	}
	
	
	public void personInsert(PhoneVo pv) {
		
		this.getConnection();
		
		try {
		    String query= "";
		    query += " insert into person ";
		    query += " values(seq_person_id.nextval, ?, ?, ?) ";
		    
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setString(1, pv.getName()); // name
		    pstmt.setString(2, pv.getHp()); // hp
		    pstmt.setString(3, pv.getCompany()); // company		   

		    int count= pstmt.executeUpdate();	    
		    		   	    
		    System.out.println("["+count+"건 등록되었습니다.]");
		        	    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		this.close();
	}
	
	
	public void personUpdate(PhoneVo pv) {
		
		this.getConnection();
		
		try {
		    String query= "";
		    query += " update 	person ";
		    query += " set 		name= ?, ";
		    query += " 	   		hp= ?, ";
		    query += " 	   		company= ?, ";
		    query += " where	person_id= ? ";
			
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setString(1, pv.getName());
		    pstmt.setString(2, pv.getHp());
		    pstmt.setString(3, pv.getCompany());
		    pstmt.setInt(4, pv.getPersonId());
		    
		    int count= pstmt.executeUpdate();
		    
		    System.out.println("["+count+"건 수정되었습니다.]");
		    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		this.close();
	}
	
	
	public void personDelete(int personId) {
		
		this.getConnection();
		
		try {
		    String query= "";
		    query += " delete from person ";
		    query += " where	   person_id= ? ";
			
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setInt(1, personId);
		    
		    int count= pstmt.executeUpdate();
		    
		    System.out.println("["+count+"건 삭제되었습니다.]");
		    
		    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		this.close();
	}
	
	
	public List<BookVo> bookSelect() {
		List<BookVo> bookList= new ArrayList<BookVo>();
		
		this.getConnection();

		try {
		    // 3. SQL문 준비 / 바인딩 / 실행		
			String query= "";
			query += " select   book_id, "; 
			query += "          title, ";
			query += "          pubs, ";
			query += "          to_char(pub_date, 'YYYY-MM-DD') pubdate, ";
			query += "          a.author_id id, ";
			query += "          author_name, ";
			query += "          author_desc ";
			query += " from     book b, author a ";
			query += " where    b.author_id= a.author_id ";
			
			// 문자열 쿼리문으로 만들기
			pstmt= conn.prepareStatement(query);
			
			// 바인딩--> 생략 ( ? 없음)
			
			// 실행 
			rs= pstmt.executeQuery();
			
		    // 4.결과처리
            while(rs.next()) {           
            	int bookId= rs.getInt("book_id"); 
            	String title= rs.getString("title");
            	String pubs= rs.getString("pubs");
            	String pubdate= rs.getString("pubdate");
            	int authorId= rs.getInt("id");
            	String authorName= rs.getString("author_name");
            	String authorDesc= rs.getString("author_desc");
            	
            	BookVo vo= new BookVo(bookId, title, pubs, pubdate, authorId, authorName, authorDesc);
            	bookList.add(vo);
            }

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}	
		this.close();
		
		return bookList;
	}
	
	
	public List<BookVo> bookSearch(String search) {
		List<BookVo> bookList= new ArrayList<BookVo>();
		
		this.getConnection();

		try {
		    // 3. SQL문 준비 / 바인딩 / 실행		
			String query= "";
			query += " select   book_id, "; 
			query += "          title, ";
			query += "          pubs, ";
			query += "          to_char(pub_date, 'YYYY-MM-DD') pubdate, ";
			query += "          a.author_id id, ";
			query += "          author_name, ";
			query += "          author_desc ";
			query += " from     book b, author a ";
			query += " where    b.author_id= a.author_id ";
			query += " and      (title like ? or pubs like ? or author_name like ?) ";

			
			// 문자열 쿼리문으로 만들기
			pstmt= conn.prepareStatement(query);
			
			// 바인딩
			pstmt.setString(1, "%"+search+"%");
		    pstmt.setString(2, "%"+search+"%");
		    pstmt.setString(3, "%"+search+"%");
		    
			// 실행 
			rs= pstmt.executeQuery();
			
		    // 4.결과처리
            while(rs.next()) {           
            	int bookId= rs.getInt("book_id"); 
            	String title= rs.getString("title");
            	String pubs= rs.getString("pubs");
            	String pubdate= rs.getString("pubdate");
            	int authorId= rs.getInt("id");
            	String authorName= rs.getString("author_name");
            	String authorDesc= rs.getString("author_desc");
            	
            	BookVo vo= new BookVo(bookId, title, pubs, pubdate, authorId, authorName, authorDesc);
            	bookList.add(vo);
            }
            
            // 출력
            for(BookVo bv: bookList) {
            	bv.showInfo();
            }

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}	
		this.close();
		
		return bookList;
	}
}


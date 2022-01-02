package com.javaex.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PhoneDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	public Scanner sc= new Scanner(System.in);
	
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
	
	
	public void personInsert() {
		List<PhoneVo> pList= new ArrayList<PhoneVo>();
		
		this.getConnection();
		
		sc.nextLine(); // 개행문자
		System.out.println("<2.등록>");
		System.out.print(">이름: ");
		String name= sc.nextLine();
		System.out.print(">휴대전화: ");
		String hp= sc.nextLine();
		System.out.print(">회사전화: ");
		String company= sc.nextLine();
		
		pList.add(new PhoneVo(name, hp, company));
		
		try {
			String query= "";
		    query += " insert into person ";
		    query += " values(seq_person_id.nextval, ?, ?, ?) ";
		    
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setString(1, name); // name
		    pstmt.setString(2, hp); // hp
		    pstmt.setString(3, company); // company		   

		    int count= pstmt.executeUpdate();	    
		    		   	    
		    System.out.println("["+count+"건 등록되었습니다.]");
		        	    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		this.close();
	}
	
	
	public void personUpdate() {
		
		this.getConnection();
		
		System.out.println("<3.수정>");
		System.out.print(">번호: ");
		int personId= sc.nextInt();
		sc.nextLine(); // 개행문자
		System.out.print(">이름: ");
		String name= sc.nextLine();
		System.out.print(">휴대전화: ");
		String hp= sc.nextLine();
		System.out.print(">회사전화: ");
		String company= sc.nextLine();
		
		try {
			String query= "";
		    query += " update 	person ";
		    query += " set 		name= ?, ";
		    query += " 	   		hp= ?, ";
		    query += " 	   		company= ? ";
		    query += " where	person_id= ? ";
			
		    pstmt= conn.prepareStatement(query);
		    
		    pstmt.setString(1, name);
		    pstmt.setString(2, hp);
		    pstmt.setString(3, company);
		    pstmt.setInt(4, personId);
		    
		    int count= pstmt.executeUpdate();
		    
		    System.out.println("["+count+"건 수정되었습니다.]");
		    
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		this.close();
	}
	
	
	public void personDelete() {
		
		this.getConnection();
		
		System.out.println("<4.삭제>");
		System.out.print(">번호: ");
		int personId= sc.nextInt();
		
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
	
	
	public List<PhoneVo> personSelect() {
		List<PhoneVo> pList= new ArrayList<PhoneVo>();
		
		this.getConnection();
		
		System.out.println("<1.리스트>");
		
		try {
			String query= "";
			query += " select   person_id, "; 
			query += "          name, ";
			query += "          hp, ";
			query += "          company ";
			query += " from     person ";

			pstmt= conn.prepareStatement(query);
			
			rs= pstmt.executeQuery();

            while(rs.next()) {           
            	int personId= rs.getInt("person_id"); 
            	String name= rs.getString("name");
            	String hp= rs.getString("hp");
            	String company= rs.getString("company");
            	
            	PhoneVo vo= new PhoneVo(personId, name, hp, company);
            	pList.add(vo);
            }
            
            for(PhoneVo pv: pList) {
            	pv.showInfo();
            }

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}	
		this.close();
		
		return pList;
	}
	
	
	public List<PhoneVo> personSearch() {
		List<PhoneVo> pList= new ArrayList<PhoneVo>();
		
		this.getConnection();
		
		sc.nextLine(); // 개행문자
		System.out.println("<5.검색>");
		System.out.print(">검색어: ");
		String search= sc.nextLine();
		
		try {
			String query= "";
			query += " select   person_id, "; 
			query += "          name, ";
			query += "          hp, ";
			query += "          company ";
			query += " from     person ";
			query += " where    (name like ? or hp like ? or company like ?) ";

			pstmt= conn.prepareStatement(query);

			pstmt.setString(1, "%"+search+"%");
		    pstmt.setString(2, "%"+search+"%");
		    pstmt.setString(3, "%"+search+"%");
		    
			rs= pstmt.executeQuery();

            while(rs.next()) {           
            	int personId= rs.getInt("person_id"); 
            	String name= rs.getString("name");
            	String hp= rs.getString("hp");
            	String company= rs.getString("company");
            	
            	PhoneVo vo= new PhoneVo(personId, name, hp, company);
            	pList.add(vo);
            }

            for(PhoneVo pv: pList) {
            	pv.showInfo();
            }

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}	
		this.close();
		
		return pList;
	}
}


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
			query += " 	   		company= ? ";
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
	
	
	public List<PhoneVo> getPersonList() {
		List<PhoneVo> pList= new ArrayList<PhoneVo>();
		
		this.getConnection();
		
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

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}	
		this.close();
		
		return pList;
	}
	
	public void printList() {			
		System.out.println("<1.리스트>");
		
		for(PhoneVo pv: this.getPersonList()) {
			pv.showInfo();
		}
	}
	
	
	public List<PhoneVo> personSearch(String search) {
		List<PhoneVo> pList= new ArrayList<PhoneVo>();
		
		this.getConnection();
		
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


package com.javaex.phone;

import java.util.Scanner;

public class PhoneApp {
	
	private static Scanner sc= new Scanner(System.in);
	private static PhoneDao phoneDao= new PhoneDao();
	
	public static void main(String[] args) {
			
		boolean action= true;
		
		opening();
		
		while(action) {
			menu();
			
			int order= sc.nextInt();
			
			switch(order) {
				case 1: 
					phoneDao.getPersonList();
					phoneDao.printList();
					break;
	
				case 2:
					enroll();
					break;
					
				case 3:
					update();
					break;
					
				case 4:
					delete();
					break;
					
				case 5: 
					search();
					
					break;
				
				case 6:
					closing();
					action= false;
					break;
					
				default :
					reenter();
					break;
			}
		}
		sc.close();
	}

	public static void opening() {
		System.out.println("*********************************");
		System.out.println("*        전화번호 관리 프로그램        *");
		System.out.println("*********************************");		
	}
	
	public static void menu() {
		System.out.println("");
		System.out.println("1.리스트  2.등록  3.수정  4.삭제  5.검색  6.종료");
		System.out.println("----------------------------------------");
		System.out.print(">메뉴번호: ");
	}
	
	public static void closing() {
		System.out.println("");
		System.out.println("*********************************");
		System.out.println("*            감사합니다            *");
		System.out.println("*********************************");
	}
	
	public static void reenter() {
		System.out.println("[다시 입력해 주세요.]");
	}
	
	
	//////////////////////////////////////////////
	/////// input
	//////////////////////
	
	public static void enroll() {
		sc.nextLine(); // 개행문자
		System.out.println("<2.등록>");
		System.out.print(">이름: ");
		String name= sc.nextLine();
		System.out.print(">휴대전화: ");
		String hp= sc.nextLine();
		System.out.print(">회사전화: ");
		String company= sc.nextLine();
		
		PhoneVo pv= new PhoneVo(name, hp, company);
		phoneDao.personInsert(pv);
	}
	
	public static void update() {
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
		
		PhoneVo pv= new PhoneVo(personId, name, hp, company);
		phoneDao.personUpdate(pv);
	}
	
	public static void delete() {
		System.out.println("<4.삭제>");
		System.out.print(">번호: ");
		int personId= sc.nextInt();
		phoneDao.personDelete(personId);
	}
	
	public static void search() {
		sc.nextLine(); // 개행문자
		System.out.println("<5.검색>");
		System.out.print(">검색어: ");
		String search= sc.nextLine();
		phoneDao.personSearch(search);
	}
}
	
	
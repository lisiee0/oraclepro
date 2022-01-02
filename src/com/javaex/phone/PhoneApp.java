package com.javaex.phone;

public class PhoneApp {

	public static void main(String[] args) {
		
		PhoneDao phoneDao= new PhoneDao();
		
		boolean action= true;
		opening();
		
		while(action) {
			menu();
			
			int order= phoneDao.sc.nextInt();
			
			switch(order) {
				case 1: 
					phoneDao.personSelect();
					break;
	
				case 2:
					phoneDao.personInsert();
					break;
					
				case 3:
					// 수정
					break;
					
				case 4:
					phoneDao.personDelete();
					break;
					
				case 5: 
					phoneDao.personSearch();
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
}
	
	
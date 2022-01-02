package com.javaex.phone;

import java.util.Scanner;

public class PhoneApp {

	public static void main(String[] args) {
		
		Scanner sc= new Scanner(System.in);
		
		boolean action= true;
		opening();
		
		while(action) {
			menu();
			
			int order= sc.nextInt();
			
			switch(order) {
				case 1: 
					// 리스트 불러오기
					break;
	
				case 2:
					// 등록
					break;
					
				case 3:
					// 수정
					break;
					
				case 4:
					// 삭제
					break;
					
				case 5: 
					// 검색
					action= false;
					break;
				
				case 6:
					// 종료
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
		System.out.println("1.리스트  2.등록  3.삭제  4.검색  5.종료");
		System.out.println("---------------------------------");
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
	
	
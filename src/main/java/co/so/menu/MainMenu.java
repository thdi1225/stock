package co.so.menu;

import java.util.Scanner;

import co.so.member.service.MemberService;
import co.so.member.serviceImpl.MemberServiceImpl;
import co.so.member.vo.MemberVO;
import co.so.stock.service.StockService;
import co.so.stock.service.TempStockService;
import co.so.stock.serviceImpl.StockServiceImpl;
import co.so.stock.serviceImpl.TempStockServiceImpl;

public class MainMenu {
	private Scanner scn = new Scanner(System.in);
	MemberService memberService = new MemberServiceImpl();
	TempStockService tempStockService = new TempStockServiceImpl();
	StockService stockService = new StockServiceImpl();
	AdminLoginMenu adminLogin = new AdminLoginMenu();
	MemberLoginMenu memberLogin = new MemberLoginMenu();
	public void run() {
		mainMenu();
	}
	
	private void mainMenu() {
		boolean run = true;
		
		while(run) {
			int menuNum = 0;
			
			System.out.println("=================================================================");
			System.out.println("==============     1. 로그인  2.회원가입  3.종료     =================");
			System.out.println("=================================================================");
			
			try {
				System.out.print("메뉴 입력 : ");
				menuNum = Integer.parseInt(scn.nextLine());
			}catch(Exception e){
				System.out.println("잘못된 값을 입력하였습니다.");
			}
			
			switch (menuNum) {
				case 1:
					MemberVO login = login();
					
					if(login.getMemberAuth() == 0) {
						adminLogin.adminLoginMenu(login);
					}else if(login.getMemberAuth() == 1){
						memberLogin.memberLoginMenu(login);
					}
					break;
				case 2:
					memberInsert();
					break;
				case 3:
					System.out.println("=================================================================");
					System.out.println("====================   시스템을 종료합니다.   ========================");
					System.out.println("=================================================================");
					scn.close();
					run = false;
					break;
				default:
					errorMenu();
					break;
			}
		}
	}
	
	private void memberInsert() {
		System.out.print("아이디 : ");
		String memberId = scn.nextLine();
		System.out.print("비밀번호 : ");
		String password = scn.nextLine();
		System.out.print("이름 : ");
		String name = scn.nextLine();
		System.out.print("연락처 : ");
		String phone = scn.nextLine();
		System.out.print("주소 : ");
		String address = scn.nextLine();
		MemberVO vo = new MemberVO(memberId, password, name, phone, address);
		
		memberService.memberInsert(vo);
	}
	
	private MemberVO login() {
		System.out.print("아이디 : ");
		String memberId = scn.nextLine();
		System.out.print("비밀번호 : ");
		String password = scn.nextLine();
		MemberVO vo = new MemberVO(memberId, password);
		
		MemberVO memberVO = memberService.login(vo);

		if(memberVO.getMemberId() != null) {
			if(memberVO.getMemberAuth() == 0) {
				System.out.println("관리자 계정입니다.");
			}else if(memberVO.getMemberAuth() == 1){
				System.out.println(memberVO.getMemberName() + "님이 접속하였습니다. 소지금은 " + memberVO.getMemberMoney() + "원 입니다.");
			}
		}else {
			System.out.println("로그인에 실패하였습니다.");
			memberVO.setMemberAuth(9);
			return memberVO;
		}
		
		return memberVO;
	}
	
	private void errorMenu() {
		System.out.println("없는 메뉴입니다.");
	}
	
}
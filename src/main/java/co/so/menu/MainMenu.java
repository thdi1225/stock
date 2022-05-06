package co.so.menu;

import java.util.List;
import java.util.Scanner;

import co.so.member.service.MemberService;
import co.so.member.serviceImpl.MemberServiceImpl;
import co.so.member.vo.MemberVO;
import co.so.stock.service.StockService;
import co.so.stock.service.TempStockService;
import co.so.stock.serviceImpl.StockServiceImpl;
import co.so.stock.serviceImpl.TempStockServiceImpl;
import co.so.stock.vo.StockVO;
import co.so.stock.vo.TempStockVO;

public class MainMenu {
	private Scanner scn = new Scanner(System.in);
	MemberService memberService = new MemberServiceImpl();
	TempStockService tempStockService = new TempStockServiceImpl();
	StockService stockService = new StockServiceImpl();
	
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
					int login = login();
					
					if(login == 0) {
						int subMenuNum = adminLogin();
						
						switch (subMenuNum) {
						case 1:
							stockInsert();
							break;
						case 2:
							System.out.println("수정");
							break;
						case 3:
							
							break;
						case 4:
							
							break;
						default:
							errorMenu();
							break;
						}
					}else if(login == 1){
						memberLogin();
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
	
	private void stockInsert() {
		List<TempStockVO> list = tempStockService.tempStockSelectList();
		
		for(TempStockVO vo : list) {
			System.out.printf("이름 : %-10s | 가격 : %d원 \n", vo.getTempStockName(), vo.getTempStockPrice());
		}
		
		System.out.println("상단 목록에서 확인 후 등록할 주식의 이름을 입력하세요.");
		System.out.print("이름 : ");
		String name = scn.nextLine();
		
		TempStockVO tempStockVO = tempStockService.tempStockSelect(name);
		StockVO vo = new StockVO();
		
		vo.setStockNum(tempStockVO.getTempStockNum());
		vo.setStockName(tempStockVO.getTempStockName());
		vo.setStockPrice(tempStockVO.getTempStockPrice());
		
		stockService.stockInsert(vo);
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
	
	private int login() {
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
			return 9;
		}
		
		return memberVO.getMemberAuth();
	}
	
	private int memberLogin() {
		int subMenuNum = 0;
		System.out.println("=================================================================");
		System.out.println("======    1.주식목록 2.주식검색 3.주식구매 4.주식판매 5.로그아웃    ========");
		System.out.println("=================================================================");
		
		try {
			System.out.print("메뉴 입력 : ");
			subMenuNum = Integer.parseInt(scn.nextLine());
		}catch(Exception e){
			System.out.println("잘못된 값을 입력하였습니다.");
		}
		
		return subMenuNum;
	}
	
	private int adminLogin() {
		int subMenuNum = 0;
		System.out.println("=================================================================");
		System.out.println("========    1.주식등록  2.주식수정  3.주식삭제  4.로그아웃    ============");
		System.out.println("=================================================================");
		
		try {
			System.out.print("메뉴 입력 : ");
			subMenuNum = Integer.parseInt(scn.nextLine());
		}catch(Exception e){
			System.out.println("잘못된 값을 입력하였습니다.");
		}
		
		return subMenuNum;
	}
	
	private void errorMenu() {
		System.out.println("없는 메뉴입니다.");
	}
	
}
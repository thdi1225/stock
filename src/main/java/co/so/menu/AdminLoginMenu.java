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

public class AdminLoginMenu {
	private Scanner scn = new Scanner(System.in);
	MemberService memberService = new MemberServiceImpl();
	TempStockService tempStockService = new TempStockServiceImpl();
	StockService stockService = new StockServiceImpl();
	
	public void adminLoginMenu(MemberVO login) {
		boolean isTrue = true;
		while(isTrue) {
			int subMenuNum = adminLogin();
			switch (subMenuNum) {
			case 1:
				stockInsert();
				break;
			case 2:
				stockUpdate();
				break;
			case 3:
				stockDelete();
				break;
			case 4:
				isTrue = false;
				break;
			default:
				errorMenu();
				break;
			}
		}
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
	
	private void stockInsert() {
		List<TempStockVO> list = tempStockService.tempStockSelectList();
		
		for(TempStockVO vo : list) {
			System.out.printf("이름 : %-10s | 가격 : %d원 \n", vo.getTempStockName(), vo.getTempStockPrice());
		}
		
		System.out.println("상단 목록에서 확인 후 등록할 주식의 이름을 입력하세요.");
		System.out.print("이름 : ");
		String name = scn.nextLine();
		System.out.println("등록할 주식의 증감율을 입력하세요.(최대 소수점 1자리) ex) 10% = 0.1");
		System.out.print("증감율 : ");
		double upAndDown = Double.parseDouble(scn.nextLine());
		
		TempStockVO tempStockVO = tempStockService.tempStockSelect(name);
		if(tempStockVO.getTempStockName() != null) {
			StockVO vo = new StockVO();
			
			vo.setStockNum(tempStockVO.getTempStockNum());
			vo.setStockName(tempStockVO.getTempStockName());
			vo.setStockPrice(tempStockVO.getTempStockPrice());
			vo.setUpAndDown(upAndDown);
			
			stockService.stockInsert(vo);
		}else {
			System.out.println("없는 주식입니다.");
		}
		
	}
	
	private void stockUpdate() {
		List<StockVO> list = stockService.stockSelectList();
		
		for(StockVO vo : list) {
			System.out.printf("이름 : %-10s | 증감율 : %.1f \n", vo.getStockName(), vo.getUpAndDown());
		}
		
		System.out.println("상단 목록에서 확인 후 수정될 주식의 이름을 입력하세요.");
		System.out.print("이름 : ");
		String name = scn.nextLine();
		System.out.println("수정할 주식의 증감율을 입력하세요.(최대 소수점 1자리) ex) 10% = 0.1");
		System.out.print("증감율 : ");
		double upAndDown = Double.parseDouble(scn.nextLine());
		
		StockVO vo = new StockVO();
		vo.setStockName(name);
		vo.setUpAndDown(upAndDown);
		
		int result = stockService.stockUpdate(vo);
		if(result == 0) {
			System.out.println("없는 주식입니다.");
		}
	}

	private void stockDelete() {
		List<StockVO> list = stockService.stockSelectList();
		
		for(StockVO vo : list) {
			System.out.printf("이름 : %-10s | 증감율 : %.1f \n", vo.getStockName(), vo.getUpAndDown());
		}
		
		System.out.println("상단 목록에서 확인 후 삭제할 주식의 이름을 입력하세요.");
		System.out.print("이름 : ");
		String name = scn.nextLine();
				
		StockVO vo = new StockVO();
		vo.setStockName(name);
		
		int result = stockService.stockDelete(vo);
		if(result == 0) {
			System.out.println("없는 주식입니다.");
		}
	}
}

package co.so.menu;

import java.util.List;
import java.util.Scanner;

import co.so.member.service.MemberService;
import co.so.member.serviceImpl.MemberServiceImpl;
import co.so.member.vo.MemberVO;
import co.so.memberStock.service.MemberStockService;
import co.so.memberStock.serviceImpl.MemberStockServiceImpl;
import co.so.memberStock.vo.MemberStockDTO;
import co.so.memberStock.vo.MemberStockVO;
import co.so.stock.service.StockService;
import co.so.stock.service.TempStockService;
import co.so.stock.serviceImpl.StockServiceImpl;
import co.so.stock.serviceImpl.TempStockServiceImpl;
import co.so.stock.vo.StockVO;

public class MemberLoginMenu {
	private Scanner scn = new Scanner(System.in);
	MemberService memberService = new MemberServiceImpl();
	TempStockService tempStockService = new TempStockServiceImpl();
	StockService stockService = new StockServiceImpl();
	MemberStockService memberStockService = new MemberStockServiceImpl();
	
	public void memberLoginMenu(MemberVO login) {
		boolean isTrue = true;
		while(isTrue) {
			int subMenuNum = memberLogin();
			switch (subMenuNum) {
			case 1:
				stockSelectList();
				break;
			case 2:
				myStockSelectList(login);
				break;
			case 3:
				stockBuy(login);
				break;
			case 4:
				stockSell(login);
				break;
			case 5:
				memberInfo(login);
				break;
			case 6:
				isTrue = false;
				break;
			default:
				errorMenu();
				break;
			}
		}
	}
	
	private void memberInfo(MemberVO login) {
		login = memberService.login(login);
		System.out.printf("id : %s | 이름 : %s | 소지금 : %d \n", login.getMemberId(), login.getMemberName(), login.getMemberMoney());
	}

	private int memberLogin() {
		int subMenuNum = 0;
		System.out.println("=================================================================");
		System.out.println("==  1.전체 주식목록 2.내 주식목록 3.주식구매 4.주식판매 5.내 정보 6.로그아웃  ==");
		System.out.println("=================================================================");
		
		try {
			System.out.print("메뉴 입력 : ");
			subMenuNum = Integer.parseInt(scn.nextLine());
		}catch(Exception e){
			System.out.println("잘못된 값을 입력하였습니다.");
		}
		
		return subMenuNum;
	}
	private void stockBuy(MemberVO login) {
		System.out.println("구매할 주식의 이름을 입력하세요.");
		System.out.print("이름 : ");
		String name = scn.nextLine();
		System.out.print("개수 : ");
		int count = 0;
		
		try{
			count = Integer.parseInt(scn.nextLine());
		}catch(Exception e) {
			System.out.println("잘못된 값을 입력하였습니다.");
		}
		
		StockVO stockVO = new StockVO();
		stockVO.setStockName(name);
		stockVO = stockService.stockSelect(stockVO);

		int price = (stockVO.getStockPrice() * count);
		
		if(stockVO.getStockName() != null) {
			if((login.getMemberMoney() - price) > 0) {
				
				login.setMemberMoney(login.getMemberMoney() - price);
				memberService.memberMoneyUpdate(login);
				
				MemberStockVO memberStockVO = new MemberStockVO();
				memberStockVO.setMemberId(login.getMemberId());
				memberStockVO.setStockName(name);
				memberStockVO.setCount(count);
			
				MemberStockVO memberStockVO2 = memberStockService.stockSelect(memberStockVO);
				if(memberStockVO2.getStockName() == null) {
					memberStockService.stockBuyInsert(memberStockVO);
				}else {
					memberStockVO2.setCount(memberStockVO2.getCount() + count);
					memberStockService.stockBuyUpdate(memberStockVO2);
				}
			}else {
				System.out.println("돈이 부족합니다.");
			}
		}else {
			System.out.println("없는 주식입니다.");
		}
	}

	private void stockSell(MemberVO login) {
		System.out.println("판매할 주식의 이름을 입력하세요.");
		System.out.print("이름 : ");
		String name = scn.nextLine();
		System.out.print("개수 : ");
		int count = 0;
		
		try{
			count = Integer.parseInt(scn.nextLine());
		}catch(Exception e) {
			System.out.println("잘못된 값을 입력하였습니다.");
		}
		
		MemberStockVO memberStockVO = new MemberStockVO();
		memberStockVO.setMemberId(login.getMemberId());
		memberStockVO.setStockName(name);
		memberStockVO.setCount(count);
		
		StockVO stockVO = new StockVO();
		stockVO.setStockName(name);
		stockVO = stockService.stockSelect(stockVO);
		int price = stockVO.getStockPrice() * count;
		
		MemberStockVO memberStockVO2 = memberStockService.stockSelect(memberStockVO);
		
		if(memberStockVO2.getCount() - count > 0) {
			memberStockVO.setCount(memberStockVO2.getCount() - count);
			memberStockService.stockSellUpdate(memberStockVO);
			
			MemberVO memberVO = new MemberVO();
			memberVO.setMemberId(login.getMemberId());
			memberVO.setMemberMoney(login.getMemberMoney() + price);
			
			memberService.memberMoneyUpdate(memberVO);
		}else if(memberStockVO2.getCount() - count == 0){
			memberStockService.stockSellDelete(memberStockVO);
			
			MemberVO memberVO = new MemberVO();
			memberVO.setMemberId(login.getMemberId());
			memberVO.setMemberMoney(login.getMemberMoney() + price);

			memberService.memberMoneyUpdate(memberVO);
		}else if(memberStockVO2.getStockName() == null){
			System.out.println("없는 주식입니다.");
		}else {
			System.out.println("가지고 계신 주식 수 보다 많습니다. 다시 입력하세요.");
		}
	}
	
	private void myStockSelectList(MemberVO login) {
		List<MemberStockDTO> list = memberStockService.stockSelectList(login);
		
		for(MemberStockDTO vo : list) {
			System.out.printf("이름 : %-5s | 가격 : %d | 수량 : %d \n", vo.getStockName(), vo.getStockPrice(), vo.getCount());
		}
	}
	
	private void stockSelectList() {
		List<StockVO> list = stockService.stockSelectList();
		
		for(StockVO vo : list) {
			System.out.printf("번호 : %-5s | 이름 : %-10s | 가격 : %d | 상한가 : %d | 하한가 : %d \n", vo.getStockNum(), vo.getStockName(), vo.getStockPrice(), vo.getStockHigh(), vo.getStockLow());
		}
	}

	private void errorMenu() {
		System.out.println("없는 메뉴입니다.");
	}
}

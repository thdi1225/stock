package co.so.memberStock.service;

import java.util.List;

import co.so.member.vo.MemberVO;
import co.so.memberStock.vo.MemberStockDTO;
import co.so.memberStock.vo.MemberStockVO;

public interface MemberStockService {

	MemberStockVO stockSelect(MemberStockVO memberStockVO);

	int stockBuyInsert(MemberStockVO memberStockVO);

	int stockBuyUpdate(MemberStockVO memberStockVO);

	int stockSellUpdate(MemberStockVO memberStockVO);

	int stockSellDelete(MemberStockVO memberStockVO);

	List<MemberStockDTO> stockSelectList(MemberVO login);
	
}

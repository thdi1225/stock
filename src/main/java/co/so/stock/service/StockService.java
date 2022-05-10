package co.so.stock.service;

import java.util.List;

import co.so.stock.vo.StockVO;

public interface StockService {

	int stockInsert(StockVO vo);

	List<StockVO> stockSelectList();

	int stockUpdate(StockVO vo);

	int stockDelete(StockVO vo);

	StockVO stockSelect(StockVO vo);

	int stockPriceUpdate(StockVO vo);

	List<StockVO> stockJoinSelectList();

	int initUpdate(StockVO vo);
	
}

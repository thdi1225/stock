package co.so.stock.service;

import java.util.List;

import co.so.stock.vo.TempStockVO;

public interface TempStockService {

	int tempStockInsert(TempStockVO vo);

	int tempStockAllDelete();

	List<TempStockVO> tempStockSelectList();

	TempStockVO tempStockSelect(String name);

}

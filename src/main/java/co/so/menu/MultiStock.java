package co.so.menu;

import java.util.List;

import co.so.stock.service.StockService;
import co.so.stock.vo.StockVO;

public class MultiStock {
	private StockService service;
	
	public void run() {
		main();
	}
	
	private void main() {
		List<StockVO> list = service.stockSelectList();
		for(StockVO vo : list) {
			double random = Math.random() * vo.getStockPrice();
			System.out.println(random);
		}
	}
}

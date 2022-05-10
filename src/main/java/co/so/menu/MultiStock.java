package co.so.menu;

import java.util.List;

import co.so.stock.service.StockService;
import co.so.stock.serviceImpl.StockServiceImpl;
import co.so.stock.vo.StockVO;

public class MultiStock {
	StockService service = new StockServiceImpl();
	
	
	public void init() {
		List<StockVO> list = service.stockJoinSelectList();
		if(list.size() != 0) {
			for(StockVO vo : list) {
				service.initUpdate(vo);
			}
		}
	}
	
	public void run() {
		main();
	}
	
	private void main() {
		List<StockVO> list = service.stockSelectList();
		if(list.size() != 0) {
			for(StockVO vo : list) {
				int upAndDown = (int) (vo.getStockPrice() * vo.getUpAndDown());
				int random = (int) (Math.random() * upAndDown) - (upAndDown/2) + 1;
				int price = random + vo.getStockPrice();
				
				if(price >= vo.getStockHigh()) {
					vo.setStockPrice(vo.getStockHigh());
				}else if(price <= vo.getStockLow()){
					vo.setStockPrice(vo.getStockLow());
				}else {
					vo.setStockPrice(price);
				}
				
				service.stockPriceUpdate(vo);
			}
		}
	}
}

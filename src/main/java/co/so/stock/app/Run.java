package co.so.stock.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import co.so.stock.api.StockAPI;
import co.so.stock.service.TempStockService;
import co.so.stock.serviceImpl.TempStockServiceImpl;
import co.so.stock.vo.TempStockVO;

public class Run {
	TempStockService tempStockService = new TempStockServiceImpl();
	private LocalDate now = LocalDate.now();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private String formatedNow = now.format(formatter);
	
	public void run() {
		main();
	}
	
	private void main() {
		tempStockService.tempStockAllDelete();
		while(true) {
			List<TempStockVO> list = StockAPI.getStock(formatedNow);
			
			if(list.isEmpty()) {
				listNull();
	    	}else {
	    		for(TempStockVO vo : list) {
	    			tempStockService.tempStockInsert(vo);
	    		}
	    		break;
	    	}
		}
	}
	
	private void listNull() {
		now = now.minusDays(1);
		formatedNow = now.format(formatter);
		StockAPI.getStock(formatedNow);
	}
}

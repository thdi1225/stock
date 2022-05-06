package co.so.stock.vo;

import lombok.Data;

@Data
public class StockVO {
	private String stockNum;
	private String stockName;
	private int stockPrice;
	private int stockHigh;
	private int stockLow;
	private int startPrice;
	private double upAndDown;
}

package co.so.memberStock.vo;

import lombok.Data;

@Data
public class MemberStockDTO {
	private int memberStockId;
	private String memberId;
	private String stockName;
	private int count;
	private int stockPrice;
}

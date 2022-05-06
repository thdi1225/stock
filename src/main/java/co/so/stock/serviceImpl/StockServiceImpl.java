package co.so.stock.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.so.conn.DataSource;
import co.so.stock.service.StockService;
import co.so.stock.vo.StockVO;

public class StockServiceImpl implements StockService {
	private DataSource dataSource = DataSource.getInstance();
	private Connection conn = dataSource.getConnection();
	private PreparedStatement psmt;
	private ResultSet rs;
	
	@Override
	public int stockInsert(StockVO vo) {
		int result = 0;
		String sql = "INSERT INTO STOCK VALUES(?, ?, ?,ROUND(?+(?*0.3)) ,ROUND(?-(?*0.3)) ,? ,?)";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getStockNum());
			psmt.setString(2, vo.getStockName());
			psmt.setInt(3, vo.getStockPrice());
			psmt.setInt(4, vo.getStockPrice());
			psmt.setInt(5, vo.getStockPrice());
			psmt.setInt(6, vo.getStockPrice());
			psmt.setInt(7, vo.getStockPrice());
			psmt.setInt(8, vo.getStockPrice());
			psmt.setDouble(9, vo.getUpAndDown());
						
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}

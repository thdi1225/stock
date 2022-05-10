package co.so.stock.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
//			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<StockVO> stockSelectList() {
		List<StockVO> list = new ArrayList<StockVO>();
		StockVO vo;
		String sql = "SELECT * FROM STOCK";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				vo = new StockVO();
				vo.setStockNum(rs.getString("stockNum"));
				vo.setStockName(rs.getString("stockName"));
				vo.setStockPrice(rs.getInt("stockPrice"));
				vo.setStockHigh(rs.getInt("stockHigh"));
				vo.setStockLow(rs.getInt("stockLow"));
				vo.setStartPrice(rs.getInt("startPrice"));
				vo.setUpAndDown(rs.getDouble("upAndDown"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int stockUpdate(StockVO vo) {
		int result = 0;
		String sql = "UPDATE STOCK SET UPANDDOWN = ? WHERE STOCKNAME = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setDouble(1, vo.getUpAndDown());
			psmt.setString(2, vo.getStockName());
						
			result = psmt.executeUpdate();
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int stockDelete(StockVO vo) {
		int result = 0;
		String sql = "DELETE FROM STOCK WHERE STOCKNAME = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getStockName());
						
			result = psmt.executeUpdate();
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public StockVO stockSelect(StockVO vo) {
		StockVO stockVO = new StockVO();
		String sql = "SELECT * FROM STOCK WHERE STOCKNAME = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getStockName());
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				stockVO.setStockNum(rs.getString("stockNum"));
				stockVO.setStockName(rs.getString("stockName"));
				stockVO.setStockPrice(rs.getInt("stockPrice"));
				stockVO.setStockHigh(rs.getInt("stockHigh"));
				stockVO.setStockLow(rs.getInt("stockLow"));
				stockVO.setStartPrice(rs.getInt("startPrice"));
				stockVO.setUpAndDown(rs.getDouble("upAndDown"));
			}
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		return stockVO;
	}

	@Override
	public int stockPriceUpdate(StockVO vo) {
		int result = 0;
		String sql = "UPDATE STOCK SET STOCKPRICE = ? WHERE STOCKNAME = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, vo.getStockPrice());
			psmt.setString(2, vo.getStockName());
						
			result = psmt.executeUpdate();
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<StockVO> stockJoinSelectList() {
		List<StockVO> list = new ArrayList<StockVO>();
		StockVO vo;
		String sql = "SELECT a.*, b.* FROM STOCK a join tempstock b on a.stocknum = b.tempstocknum";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				vo = new StockVO();
				vo.setStockNum(rs.getString("stockNum"));
				vo.setStockName(rs.getString("stockName"));
				vo.setStockPrice(rs.getInt("stockPrice"));
				vo.setStockHigh(rs.getInt("stockHigh"));
				vo.setStockLow(rs.getInt("stockLow"));
				vo.setStartPrice(rs.getInt("startPrice"));
				vo.setUpAndDown(rs.getDouble("upAndDown"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int initUpdate(StockVO vo) {
		int result = 0;
		String sql = "UPDATE STOCK SET STOCKHIGH = (? + (? * 0.3)), STOCKLOW = (? - (? * 0.3)), STARTPRICE = ? WHERE STOCKNUM = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, vo.getStockPrice());
			psmt.setInt(2, vo.getStockPrice());
			psmt.setInt(3, vo.getStockPrice());
			psmt.setInt(4, vo.getStockPrice());
			psmt.setInt(5, vo.getStockPrice());
			psmt.setString(6, vo.getStockNum());
						
			result = psmt.executeUpdate();
		} catch (SQLException e) {
//			e.printStackTrace();
		}
		
		return result;
	}
	
}

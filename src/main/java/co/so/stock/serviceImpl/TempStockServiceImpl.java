package co.so.stock.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.so.conn.DataSource;
import co.so.stock.service.TempStockService;
import co.so.stock.vo.TempStockVO;

public class TempStockServiceImpl implements TempStockService{
	private DataSource dataSource = DataSource.getInstance();
	private Connection conn = dataSource.getConnection();
	private PreparedStatement psmt;
	private ResultSet rs;
	
	@Override
	public int tempStockInsert(TempStockVO vo) {
		int result = 0;
		String sql = "INSERT INTO TEMPSTOCK VALUES(?, ?, ?)";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getTempStockNum());
			psmt.setString(2, vo.getTempStockName());
			psmt.setInt(3, vo.getTempStockPrice());
						
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int tempStockAllDelete() {
		int result = 0;
		String sql = "DELETE FROM TEMPSTOCK";
		
		try {
			psmt = conn.prepareStatement(sql);
						
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<TempStockVO> tempStockSelectList() {
		List<TempStockVO> list = new ArrayList<TempStockVO>();
		TempStockVO vo;
		String sql = "SELECT * FROM TEMPSTOCK";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				vo = new TempStockVO();
				vo.setTempStockName(rs.getString("tempStockName"));
				vo.setTempStockPrice(Integer.parseInt(rs.getString("tempStockPrice")));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public TempStockVO tempStockSelect(String name) {
		TempStockVO vo = new TempStockVO();
		String sql = "SELECT * FROM TEMPSTOCK WHERE TEMPSTOCKNAME = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				vo = new TempStockVO();
				vo.setTempStockNum(rs.getString("tempStockNum"));
				vo.setTempStockName(rs.getString("tempStockName"));
				vo.setTempStockPrice(Integer.parseInt(rs.getString("tempStockPrice")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}
}
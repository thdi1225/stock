package co.so.memberStock.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.so.conn.DataSource;
import co.so.member.vo.MemberVO;
import co.so.memberStock.service.MemberStockService;
import co.so.memberStock.vo.MemberStockDTO;
import co.so.memberStock.vo.MemberStockVO;

public class MemberStockServiceImpl implements MemberStockService{
	private DataSource dataSource = DataSource.getInstance();
	private Connection conn = dataSource.getConnection();
	private PreparedStatement psmt;
	private ResultSet rs;
	
	@Override
	public MemberStockVO stockSelect(MemberStockVO memberStockVO) {
		MemberStockVO vo = new MemberStockVO();
		String sql = "SELECT * FROM MEMBERSTOCK WHERE MEMBERID = ? AND STOCKNAME = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, memberStockVO.getMemberId());
			psmt.setString(2, memberStockVO.getStockName());
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				vo.setMemberStockId((rs.getInt("memberStockId")));				
				vo.setMemberId(rs.getString("memberId"));
				vo.setStockName((rs.getString("stockName")));
				vo.setCount(rs.getInt("count"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	@Override
	public int stockBuyInsert(MemberStockVO memberStockVO) {
		int result = 0;
		String sql = "INSERT INTO MEMBERSTOCK VALUES(MEMBERSTOCKNO.NEXTVAL,?, ?, ?)";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, memberStockVO.getMemberId());
			psmt.setString(2, memberStockVO.getStockName());
			psmt.setInt(3, memberStockVO.getCount());
			
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int stockBuyUpdate(MemberStockVO memberStockVO) {
		int result = 0;
		String sql = "UPDATE MEMBERSTOCK SET COUNT = ? WHERE MEMBERID = ? AND STOCKNAME = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, memberStockVO.getCount());
			psmt.setString(2, memberStockVO.getMemberId());
			psmt.setString(3, memberStockVO.getStockName());
			
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int stockSellUpdate(MemberStockVO memberStockVO) {
		int result = 0;
		String sql = "UPDATE MEMBERSTOCK SET COUNT = ? WHERE MEMBERID = ? AND STOCKNAME = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, memberStockVO.getCount());
			psmt.setString(2, memberStockVO.getMemberId());
			psmt.setString(3, memberStockVO.getStockName());
			
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int stockSellDelete(MemberStockVO memberStockVO) {
		int result = 0;
		String sql = "DELETE FROM MEMBERSTOCK WHERE MEMBERID = ? AND STOCKNAME = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, memberStockVO.getMemberId());
			psmt.setString(2, memberStockVO.getStockName());
			
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<MemberStockDTO> stockSelectList(MemberVO login) {
		List<MemberStockDTO> list = new ArrayList<MemberStockDTO>();
		String sql = "select a.*, b.stockprice from memberstock a left join stock b on a.stockname = b.stockname where memberid = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, login.getMemberId());
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				MemberStockDTO vo = new MemberStockDTO();
				vo.setMemberStockId((rs.getInt("memberStockId")));				
				vo.setMemberId(rs.getString("memberId"));
				vo.setStockName((rs.getString("stockName")));
				vo.setCount(rs.getInt("count"));
				vo.setStockPrice(rs.getInt("stockPrice"));
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}

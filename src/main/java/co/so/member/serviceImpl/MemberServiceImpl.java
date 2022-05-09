package co.so.member.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.so.conn.DataSource;
import co.so.member.service.MemberService;
import co.so.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService{
	private DataSource dataSource = DataSource.getInstance();
	private Connection conn = dataSource.getConnection();
	private PreparedStatement psmt;
	private ResultSet rs;
	
	@Override
	public int memberInsert(MemberVO vo) {
		int result = 0;
		String sql = "INSERT INTO MEMBER VALUES(MEMBERNO.NEXTVAL,?, ?, ?, ?, ?, SYSDATE, SYSDATE, 10000, 1)";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getMemberId());
			psmt.setString(2, vo.getMemberPassword());
			psmt.setString(3, vo.getMemberName());
			psmt.setString(4, vo.getMemberPhone());
			psmt.setString(5, vo.getMemberAddress());
			
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public MemberVO login(MemberVO vo) {
		MemberVO memberVO = new MemberVO();
		String sql = "SELECT * FROM MEMBER WHERE MEMBERID = ? AND MEMBERPASSWORD = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getMemberId());
			psmt.setString(2, vo.getMemberPassword());
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				memberVO.setMemberNo(rs.getInt("memberNo"));
				memberVO.setMemberId(rs.getString("memberId"));
				memberVO.setMemberPassword(rs.getString("memberPassword"));
				memberVO.setMemberName(rs.getString("memberName"));
				memberVO.setMemberPhone(rs.getString("memberPhone"));
				memberVO.setMemberAddress(rs.getString("memberAddress"));
				memberVO.setMemberNdt(rs.getDate("memberNdt"));
				memberVO.setMemberEdt(rs.getDate("memberEdt"));
				memberVO.setMemberMoney(rs.getInt("memberMoney"));
				memberVO.setMemberAuth(rs.getInt("memberAuth"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberVO;
	
	}

	@Override
	public int memberMoneyUpdate(MemberVO login) {
		int result = 0;
		String sql = "UPDATE MEMBER SET MEMBERMONEY = ? WHERE MEMBERID = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, login.getMemberMoney());
			psmt.setString(2, login.getMemberId());
			
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
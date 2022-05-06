package co.so.member.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberVO {
	private int memberNo;
	private String memberId;
	private String memberPassword;
	private String memberName;
	private String memberPhone;
	private String memberAddress;
	private int memberAuth;
	private Date memberNdt;
	private Date memberEdt;
	private int memberMoney;
	
//	private int loginFailCount;
//	private boolean isAccessLock;
//	private Date lastLoginDate;
	
	public MemberVO(String id, String password, String name, String phone, String address) {
		this.memberId = id;
		this.memberPassword = password;
		this.memberName = name;
		this.memberPhone = phone;
		this.memberAddress = address;
	}

	public MemberVO(String id, String password) {
		this.memberId = id;
		this.memberPassword = password;
	}
	
	public MemberVO() {

	}
}

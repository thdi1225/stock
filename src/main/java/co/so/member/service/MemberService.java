package co.so.member.service;

import co.so.member.vo.MemberVO;

public interface MemberService {

	int memberInsert(MemberVO vo);

	MemberVO login(MemberVO vo);

	int memberMoneyUpdate(MemberVO login);

}

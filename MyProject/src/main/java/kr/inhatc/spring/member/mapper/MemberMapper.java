package kr.inhatc.spring.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.inhatc.spring.member.dto.ImageDto;
import kr.inhatc.spring.member.dto.MemberDto;

@Mapper
public interface MemberMapper {

	List<MemberDto> memberList();

	void memberInsert(MemberDto member);

	MemberDto memberDetail(String memberId);

	void memberUpdate(MemberDto member);

	void memberDelete(String memberId);

	void memberFileInsert(List<ImageDto> list);

	String selectBoardFile(String memberId);

}

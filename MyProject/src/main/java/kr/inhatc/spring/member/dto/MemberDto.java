package kr.inhatc.spring.member.dto;

import lombok.Data;

@Data
public class MemberDto {

	private String memberId;
	private String email;
	private String enabled;
	private String joinDate;
	private String name;
	private String pw;
	private String role;
	// 파일 관리를 위한 리스트 추가
	private String m_image;
}

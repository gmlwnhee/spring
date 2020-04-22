package kr.inhatc.spring.board.dto;
// DTO : Data Transfer Object 데이터 전달 객체
//lombok 의존성 추가
import lombok.Data;

@Data
public class BoardDto {

	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createDateTime;
	
}

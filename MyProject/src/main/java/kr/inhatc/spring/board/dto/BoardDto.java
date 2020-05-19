package kr.inhatc.spring.board.dto;
import java.util.List;

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
	// 파일 관리를 위한 리스트 추가
	private List<FileDto> fileList;
}

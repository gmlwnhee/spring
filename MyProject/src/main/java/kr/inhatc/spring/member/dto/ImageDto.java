package kr.inhatc.spring.member.dto;

import lombok.Data;

@Data
public class ImageDto {
	
	private int idx;
	private String memberId;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
}

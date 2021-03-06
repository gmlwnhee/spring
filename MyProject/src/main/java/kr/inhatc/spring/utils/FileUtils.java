package kr.inhatc.spring.utils;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.dto.FileDto;
import kr.inhatc.spring.member.dto.ImageDto;
import kr.inhatc.spring.user.entity.Images;

@Component
public class FileUtils {

	public List<FileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) {

		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}

		List<FileDto> fileList = new ArrayList<FileDto>();

		// 파일이 업로드될 폴더 생성
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/" + current.format(format);

		File file = new File(path);
		if(file.exists() == false) {
			file.mkdir();
		}

		Iterator<String> iter = multipartHttpServletRequest.getFileNames();

		String originalFileExtension = null;
		while(iter.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iter.next());

			for (MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {

					String contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}
					String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;

					FileDto boardFile = new FileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);

					file = new File(path + "/" + newFileName);
					try {
						multipartFile.transferTo(file);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}

		return fileList;
	}

	// member 이미지
	public List<ImageDto> parseFileInfo(String memberId,
			MultipartHttpServletRequest multipartHttpServletRequest) {
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}

		List<ImageDto> fileList = new ArrayList<ImageDto>();

		// 파일이 업로드될 폴더 생성
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "src/main/resources/static/images-member/" + current.format(format);

		File file = new File(path);
		if(file.exists() == false) {
			file.mkdir();
		}

		Iterator<String> iter = multipartHttpServletRequest.getFileNames();

		String originalFileExtension = null;
		while(iter.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iter.next());

			for (MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {

					String contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}
					String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;

					ImageDto imageFile = new ImageDto();
					imageFile.setMemberId(memberId);
					imageFile.setFileSize(multipartFile.getSize());
					imageFile.setOriginalFileName(multipartFile.getOriginalFilename());
					imageFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(imageFile);

					file = new File(path + "/" + newFileName);
					try {
						multipartFile.transferTo(file);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}

		return fileList;
	}
	
	
	// Users 이미지
		public List<Images> parseFileInfoUser(String userId,
				MultipartHttpServletRequest multipartHttpServletRequest) {
			if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
				return null;
			}

			List<Images> fileList = new ArrayList<Images>();

			// 파일이 업로드될 폴더 생성
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
			ZonedDateTime current = ZonedDateTime.now();
			String path = "src/main/resources/static/images-member/" + current.format(format);

			File file = new File(path);
			if(file.exists() == false) {
				file.mkdir();
			}

			Iterator<String> iter = multipartHttpServletRequest.getFileNames();

			String originalFileExtension = null;
			while(iter.hasNext()) {
				List<MultipartFile> list = multipartHttpServletRequest.getFiles(iter.next());

				for (MultipartFile multipartFile : list) {
					if(multipartFile.isEmpty() == false) {

						String contentType = multipartFile.getContentType();
						if(ObjectUtils.isEmpty(contentType)) {
							break;
						} else {
							if(contentType.contains("image/jpeg")) {
								originalFileExtension = ".jpg";
							} else if(contentType.contains("image/png")) {
								originalFileExtension = ".png";
							} else if(contentType.contains("image/gif")) {
								originalFileExtension = ".gif";
							} else {
								break;
							}
						}
						String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;

						Images imageFile = new Images();
						imageFile.setUserId(userId);
						imageFile.setFileSize(multipartFile.getSize());
						imageFile.setOriginalFileName(multipartFile.getOriginalFilename());
						imageFile.setStoredFilePath(path + "/" + newFileName);
						fileList.add(imageFile);

						file = new File(path + "/" + newFileName);
						try {
							multipartFile.transferTo(file);
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			}

			return fileList;
		}
}

package kr.inhatc.spring.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.member.dto.ImageDto;
import kr.inhatc.spring.member.dto.MemberDto;
import kr.inhatc.spring.member.mapper.MemberMapper;
import kr.inhatc.spring.utils.FileUtils;
import kr.inhatc.spring.utils.ThumnailUtils;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<MemberDto> memberList() {
		return memberMapper.memberList();
	}

	@Override
	public void memberInsert(MemberDto member, MultipartHttpServletRequest multipartHttpServletRequest) {
		memberMapper.memberInsert(member);
		
		// 1. 파일 저장
		List<ImageDto> list = fileUtils.parseFileInfo(member.getMemberId(), multipartHttpServletRequest);
		
		// 2. 디비 저장
		if(CollectionUtils.isEmpty(list) == false) {
			memberMapper.memberFileInsert(list);
		}
	}

	@Override
	public MemberDto memberDetail(String memberId) {
		MemberDto member = memberMapper.memberDetail(memberId);
		ThumnailUtils thumnail = new ThumnailUtils();
		//파일 정보
		String image = memberMapper.selectBoardFile(memberId);
		thumnail.makeThumnail(image);
		String img = image.replaceFirst("src/main/resources/static", "");
		member.setM_image(img);
		
		return member;
	}

	@Override
	public void memberUpdate(MemberDto member) {
		memberMapper.memberUpdate(member);
	}

	@Override
	public void memberDelete(String memberId) {
		memberMapper.memberDelete(memberId);
		
	}

}

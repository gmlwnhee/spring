package kr.inhatc.spring.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.member.dto.MemberDto;
import kr.inhatc.spring.member.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired //컨트롤러에 서비스를 불러옴
	private MemberService memberService;
	
	@RequestMapping("/member/memberList")
	public String memberList(Model model) {
		// model은 controller에서 html로 보낼 때
		List<MemberDto> list = memberService.memberList();
		//System.out.println("===========> " + list.size());
		model.addAttribute("list", list);
		return "member/memberList";
	}
	
	@RequestMapping("/member/memberWrite")
	public String memberWrite() {
		return "member/memberWrite";
	}
	
	@RequestMapping("/member/memberInsert")
	public String memberInsert(MemberDto member, MultipartHttpServletRequest multipartHttpServletRequest) {
		memberService.memberInsert(member, multipartHttpServletRequest);
		return "redirect:/member/memberList";
	}
	
	@RequestMapping("/member/memberDetail")
	public String memberDetail(@RequestParam String memberId, Model model) {
		MemberDto member = memberService.memberDetail(memberId);
		model.addAttribute("member", member);
		//System.out.println(board);
		//System.out.println("=========> boardIdx : " + boardIdx);
		return "member/memberDetail";
	}
	
	@RequestMapping("/member/memberUpdate")
	public String memberUpdate(MemberDto member) {
		System.out.println("=========> id : " + member.getMemberId());
		System.out.println("=========> email : " + member.getEmail());
		memberService.memberUpdate(member);
		return "redirect:/member/memberList";
	}
	
	@RequestMapping("/member/memberDelete")
	public String memberDelete(@RequestParam("memberId") String memberId) {
		memberService.memberDelete(memberId);
		return "redirect:/member/memberList";
	}
}

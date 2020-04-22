package kr.inhatc.spring.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.service.BoardService;

// 웹 사이트에 접근하는 컨트롤러
//@RestController
//public class BoardController {
//
//	@RequestMapping("/")
//	public String hello() {
//		return "Hello World";
//	}
//}

@Controller // thymeleaf 의존 추가
public class BoardController {
	
	//컨트롤러에 서비스를 불러옴
	@Autowired
	private BoardService boardService;

	@RequestMapping("/")
	public String hello() {
		return "index"; // index.html
	}
	
	@RequestMapping("/board/boardList.do")
	public String boardList(Model model) {
		List<BoardDto> list = boardService.boardList();
		System.out.println("===========> " + list.size());
		model.addAttribute("list", list);
		return "board/boardList";
		
		
	}
	
//	@RequestMapping("/board/boardList.do")
//	public ModelAndView boardList() {
//		ModelAndView mv = new ModelAndView("board/boardList");
//		List<BoardDto> list = boardService.boardList();
//		mv.addObject("list", list);
//		return mv;
//	}
}
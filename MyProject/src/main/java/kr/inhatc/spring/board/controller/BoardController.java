package kr.inhatc.spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 웹 사이트에 접근하는 컨트롤런
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

	@RequestMapping("/")
	public String hello() {
		return "index"; // index.html
	}
}
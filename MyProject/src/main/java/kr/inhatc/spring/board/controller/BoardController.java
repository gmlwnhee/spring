package kr.inhatc.spring.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.dto.FileDto;
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
	 
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//컨트롤러에 서비스를 불러옴
	@Autowired
	private BoardService boardService;

//	@RequestMapping("/")
//	public String hello() {
//		return "index"; // index.html
//	}
	
	@RequestMapping("/board/boardList")
	public String boardList(Model model) {
		// model은 controller에서 html로 보낼 때
		List<BoardDto> list = boardService.boardList();
		log.debug("===========> " + list.size());
		//위와 같이 log로 찍을 수도 있음 System.out.println("===========> " + list.size());
		model.addAttribute("list", list);
		return "board/boardList";
	}
	
	@RequestMapping("/board/boardWrite")
	public String boardWrite() {
		return "board/boardWrite";
	}
	
	@RequestMapping("/board/boardInsert")
	public String boardInsert(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) {
		boardService.boardInsert(board, multipartHttpServletRequest);
		return "redirect:/board/boardList";
	}
	
	@RequestMapping("/board/boardDetail")
	public String boardDetail(@RequestParam int boardIdx, Model model) {
		BoardDto board = boardService.boardDetail(boardIdx);
		model.addAttribute("board", board);
		//System.out.println(board);
		//System.out.println("=========> boardIdx : " + boardIdx);
		return "board/boardDetail";
	}
	
	@RequestMapping("/board/boardUpdate")
	public String boardUpdate(BoardDto board) {
		System.out.println("=========> Index : " + board.getBoardIdx());
		System.out.println("=========> Title : " + board.getTitle());
		boardService.boardUpdate(board);
		return "redirect:/board/boardList";
	}
	
	@RequestMapping("/board/boardDelete")
	public String boardDelete(@RequestParam("boardIdx") int boardIdx) {
		boardService.boardDelete(boardIdx);
		return "redirect:/board/boardList";
	}
	
	@RequestMapping("/board/downloadBoardFile")
	public void downloadBoardFile(@RequestParam("idx") int idx,
			@RequestParam("boardIdx") int boardIdx,
			HttpServletResponse response) throws Exception {
//		System.out.println("========>" + idx);
//		System.out.println("========>" + boardIdx);		
		FileDto boardFile = boardService.selectFileInfo(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			//response 헤더에 설정
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") +"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();	//버퍼 비워
			response.getOutputStream().close();
		}
	}
	
//	@RequestMapping("/board/boardList.do")
//	public ModelAndView boardList() {
//		ModelAndView mv = new ModelAndView("board/boardList");
//		List<BoardDto> list = boardService.boardList();
//		mv.addObject("list", list);
//		return mv;
//	}
}
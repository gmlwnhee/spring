package kr.inhatc.spring.comment.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.inhatc.spring.comment.entity.Comment;
import kr.inhatc.spring.comment.service.CommentService;

@Controller
public class CommentController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommentService commentService;
	
	// 댓글 리스트
	@RequestMapping(value="/comment/commentList", method=RequestMethod.POST)
	@ResponseBody
	public List<Comment> commentList(Comment comment){
		log.debug("===============>"+comment.getBno());
		List<Comment> list = commentService.commentList(comment.getBno());
		return list;
	}
	
	// 댓글 추가
	@RequestMapping("/comment/commentInsert")
	@ResponseBody
	public void commentInsert(Comment comment, HttpServletResponse response){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal.equals("anonymousUser")==false) {
		UserDetails userDetails = (UserDetails)principal;
		String uid = userDetails.getUsername();
		comment.setWriter(uid);
		commentService.saveComment(comment);
		}
	}
	
	// 댓글 수정
	@RequestMapping("/comment/commentUpdate")
	@ResponseBody
	public void commentUpdate(Comment comment) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String uid = userDetails.getUsername();
		comment.setWriter(uid);
		commentService.saveComment(comment);
	}
	
	// 댓글 삭제
	@RequestMapping(value="/comment/commentDelete/{cno}", method=RequestMethod.GET)
	@ResponseBody
	public void commentDelete(@PathVariable("cno")int cno) {
		Comment comment = commentService.findCno(cno);
		commentService.commentDelete(comment);
	}

	
}

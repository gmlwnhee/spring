package kr.inhatc.spring.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.inhatc.spring.comment.entity.Comment;

@Service
public interface CommentService {

	List<Comment> commentList(int bno);

	void saveComment(Comment comment);

	Comment findCno(int cno);

	void commentDelete(Comment comment);

}

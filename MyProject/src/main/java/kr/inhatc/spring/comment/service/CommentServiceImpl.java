package kr.inhatc.spring.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.comment.entity.Comment;
import kr.inhatc.spring.comment.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentRepository commentRepository;

	@Override
	public List<Comment> commentList(int bno) {
		List<Comment> list = commentRepository.findByBnoOrderByCno(bno);
		return list;
	}

	@Override
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

	@Override
	public Comment findCno(int cno) {
		Comment comment = commentRepository.findByCno(cno);
		return comment;
	}

	@Override
	public void commentDelete(Comment comment) {
		commentRepository.delete(comment);
	}

	
	
}

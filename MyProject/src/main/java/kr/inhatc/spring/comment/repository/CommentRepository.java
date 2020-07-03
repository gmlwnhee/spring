package kr.inhatc.spring.comment.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.comment.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, String>{

	List<Comment> findByBnoOrderByCno(int bno);

	Comment findByCno(int cno);

}

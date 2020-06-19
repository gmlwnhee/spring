package kr.inhatc.spring.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kr.inhatc.spring.user.entity.Images;

public interface ImageRepository extends CrudRepository<Images, String>{

	Images findByUserId(String id);

}

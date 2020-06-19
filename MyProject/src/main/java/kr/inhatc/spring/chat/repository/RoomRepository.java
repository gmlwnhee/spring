package kr.inhatc.spring.chat.repository;

import java.util.List;
import java.util.Random;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.chat.entity.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, String>{

	List<Room> findAllByOrderByCreatedTime();

	Room findByRoomId(int roomId);

	//Room findByUserId(int randId);

	//int findUserIdByUserId(int randId);

}

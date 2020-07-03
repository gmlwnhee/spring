package kr.inhatc.spring.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.chat.entity.Room;
import kr.inhatc.spring.chat.repository.RoomRepository;


@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Override
	public void saveRoom(Room room) {
		roomRepository.save(room);
		
	}

	@Override
	public List<Room> showRoom() {
		List<Room> room = (List<Room>) roomRepository.findAll();
		return room;
	}
	
	@Override
	public List<Room> showUserList() {
		List<Room> list = roomRepository.findAllByOrderByCreatedTime();
		return list;
	}


	@Override
	public Room findRoom(int roomId) {
		Room room = roomRepository.findByRoomId(roomId);
		return room;
	}
	
}

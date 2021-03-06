package kr.inhatc.spring.chat.service;

import java.util.List;

import kr.inhatc.spring.chat.entity.Room;

public interface RoomService {

	List<Room> showRoom();

	// 상담원 페이지에 보이는 유저리스트
	List<Room> showUserList();
	
	// ChatRoomState (채팅방상태 관리)
	void saveRoom(Room room);
	
	// 방 찾기
	Room findRoom(int roomId);

}

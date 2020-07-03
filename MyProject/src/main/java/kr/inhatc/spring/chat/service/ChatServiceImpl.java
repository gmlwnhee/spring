package kr.inhatc.spring.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.chat.entity.ChatLog;
import kr.inhatc.spring.chat.repository.ChatRepository;


@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Override
	public void saveMessage(ChatLog chatLog) {
		chatRepository.save(chatLog);
	}

	@Override
	public List<ChatLog> showChatList(int roomId) {
		List<ChatLog> list = chatRepository.findByRoomIdOrderBySendTime(roomId);
		return list;
	}

	
}

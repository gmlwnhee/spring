package kr.inhatc.spring.chat.service;

import java.util.List;

import kr.inhatc.spring.chat.entity.ChatLog;

public interface ChatService {

	void saveMessage(ChatLog chatLog);

	List<ChatLog> showChatList(int roomId);

}

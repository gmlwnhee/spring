package kr.inhatc.spring.chat.service;

import kr.inhatc.spring.chat.entity.ChatLog;

public interface ChatService {

	void saveMessage(ChatLog chatLog);

//	List<ChatLog> findChat(String userId, String who);
//
//	// 다른 repository에서 관리 
//	void saveRealData(ChatData chatData);
//
//	List<ChatData> showData(int userId);
//
//	ChatData findContent(int id);
//
//	void contentDelete(ChatData data);

}

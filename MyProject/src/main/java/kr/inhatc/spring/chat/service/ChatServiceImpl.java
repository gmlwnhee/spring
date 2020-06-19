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
	
//	@Override
//	public List<ChatLog> findChat(String userId, String who) {
//		List<ChatLog> list = chatRepository.findByUserIdAndNumNotAndWhoOrderBySendTime(Integer.parseInt(userId), 0, who);
//		return list;
//	}

//	@Override
//	public void saveRealData(ChatData chatData) {
//		realRepository.save(chatData);
//	}
//
//	@Override
//	public List<ChatData> showData(int userId) {
//		List<ChatData> list = realRepository.findByUserId(userId);
//		return list;
//	}
//
//	@Override
//	public ChatData findContent(int id) {
//		ChatData data = realRepository.findById(id);
//		return data;
//	}
//
//	@Override
//	public void contentDelete(ChatData data) {
//		realRepository.delete(data);
//	}
	
}

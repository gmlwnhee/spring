package kr.inhatc.spring.chat.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import kr.inhatc.spring.chat.entity.ChatLog;
import kr.inhatc.spring.chat.service.ChatService;

@Controller
public class ChatController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	//
	//컨트롤러에 서비스를 불러옴
	@Autowired
	private ChatService chatService;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/chat/send")
	public void chat(ChatLog chatLog) throws Exception {
		String receiver = chatLog.getReceiver();

		log.debug("==========>"+chatLog.getReceiver());
		log.debug("==========>"+chatLog); 
		// receiver에게 보냄
		simpMessagingTemplate.convertAndSend("/topic/"+receiver, chatLog);
		
		 chatService.saveMessage(chatLog); 
	}


}

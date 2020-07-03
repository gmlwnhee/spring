package kr.inhatc.spring.chat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.inhatc.spring.chat.entity.ChatLog;
import kr.inhatc.spring.chat.entity.Room;
import kr.inhatc.spring.chat.service.ChatService;
import kr.inhatc.spring.chat.service.RoomService;
import kr.inhatc.spring.user.entity.Images;
import kr.inhatc.spring.user.repository.ImageRepository;

@Controller
public class ChatRoomController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoomService roomService;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ChatService chatService;

	// 과거 채팅내역 불러오기
	@RequestMapping(value = "/chat/showPastChat", method=RequestMethod.POST)
	@ResponseBody
	public List<ChatLog> showPastChat(ChatLog chatLog) {
		List<ChatLog> list =chatService.showChatList(chatLog.getRoomId());
		return list;
	}
	// 채팅방 목록
	@RequestMapping(value = "/chat/chatList", method=RequestMethod.GET)
	public String chatList(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String uid = userDetails.getUsername();
		log.debug("===========>" + uid);
		model.addAttribute("userId", uid);
		return "chat/chatList";
	}

	// 새로운 방 생성(ajax)
	@RequestMapping(value = "/chat/makeRoom", method=RequestMethod.POST)
	@ResponseBody
	public void makeRoom(Room room, Model model) {
		log.debug(room.getTitle() + room.getRoomMaker()+"<====");
		roomService.saveRoom(room);
	}

	// 방 목록 보여주기(ajax)
	@RequestMapping(value = "/chat/showRoom", method=RequestMethod.POST)
	@ResponseBody
	public List<Room> showRoom() {
		List<Room> list = roomService.showRoom();
		return list;
	}
	
	// 채팅창
	@RequestMapping(value = "/chat/chat/{roomId}", method=RequestMethod.GET)
	public String conQA(@PathVariable("roomId") int roomId, Model model, HttpServletResponse response) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String uid = userDetails.getUsername();
		log.debug("===========>" + uid);

		Room room = roomService.findRoom(roomId);
		String receiver;
		String sender;
		// 방장과 다른 사람일 경우
		if(!uid.equals(room.getRoomMaker())) {
			// 1:1 로 꽉찼을때
			if(room.getVisiter()==null) {
				room.setVisiter(uid);
				roomService.saveRoom(room);
			}else if(!room.getVisiter().isEmpty()&&!room.getVisiter().equals(uid)) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out;
					try {
						out = response.getWriter();
						out.println("<script>alert('이미 정원이 다 찼습니다.'); history.go(-1);</script>");
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return "chat/chatList";
				}
			receiver = room.getRoomMaker();
			sender = room.getVisiter();
			model.addAttribute("receiver", receiver);
			model.addAttribute("sender", sender);
			model.addAttribute("who","방문자");
		} // 방장일 경우
		else {
			receiver = room.getVisiter();
			sender = room.getRoomMaker();
			model.addAttribute("receiver", receiver);
			model.addAttribute("sender", sender);
			model.addAttribute("who","방장");
		}
		model.addAttribute("roomId",roomId);
		// 썸네일 찾기
		String image_s;
		String image_r;
		Images image_sender = imageRepository.findByUserId(sender);
		if(image_sender == null) {
			image_s = "src/main/resources/static/images-member/user.png";
		} else {
			image_s = image_sender.getStoredFilePath();
		}
		String img_s = image_s.replaceFirst("src/main/resources/static", "");

		Images image_receiver = imageRepository.findByUserId(receiver);
		if(image_receiver == null) {
			image_r = "src/main/resources/static/images-member/user.png";
		} else {
			image_r = image_receiver.getStoredFilePath();
		}
		String img_r = image_r.replaceFirst("src/main/resources/static", "");
		model.addAttribute("img_s", img_s);
		model.addAttribute("img_r", img_r);

		return "chat/chat";
	}

}

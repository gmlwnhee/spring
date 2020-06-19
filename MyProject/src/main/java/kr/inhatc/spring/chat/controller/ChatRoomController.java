package kr.inhatc.spring.chat.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	//	@Autowired
	//	private ChatService chatService;

	//	@RequestMapping(value = "/chat/mainCon", method=RequestMethod.GET)
	//	public String mainCon(Model model) {
	//		List<Room> list =roomService.showUserList();
	//		model.addAttribute("list", list);
	//		return "chat/mainCon";
	//	}
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
	@RequestMapping(value = "/chat/chat/{roomId}", method=RequestMethod.GET)
	public String conQA(@PathVariable("roomId") int roomId, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String uid = userDetails.getUsername();
		log.debug("===========>" + uid);

		Room room = roomService.findRoom(roomId);
		String receiver;
		String sender;
		// 방장과 다른 사람일 경우
		if(!uid.equals(room.getRoomMaker())) {
			room.setVisiter(uid);
			roomService.saveRoom(room);
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

	//	@RequestMapping(value = "/chat/conProcess/{userId}", method=RequestMethod.GET)
	//	public String consProcess(@PathVariable("userId") String userId, Model model) {
	//		List<ChatLog> chatList1 = chatService.findChat(userId, "상담원");
	//		List<ChatLog> chatList2 = chatService.findChat(userId, "사용자");
	//		model.addAttribute("list1",chatList1);
	//		model.addAttribute("list2",chatList2);
	//		return "chat/conProcess";
	//	}
	//	
	//	@RequestMapping(value = "/chat/deleteState/{userId}", method=RequestMethod.GET)
	//	public String deleteState(@PathVariable("userId") String userId) {
	//		Room room = roomService.findRoom(userId);
	//		roomService.roomDelete(room);
	//		return "redirect:/chat/mainCon";
	//	}
	//	@RequestMapping(value = "/chat/ConQA/{userId}", method=RequestMethod.GET)
	//	public String conQA(@PathVariable("userId") String userId, Model model,HttpServletRequest request) {
	//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	//		UserDetails userDetails = (UserDetails)principal;
	//		String uid = userDetails.getUsername();
	//		log.debug("===========>" + uid);
	//		
	//		//상담상태 변경 및 상담원 번호 등록
	//		Room room = roomService.findRoom(userId);
	//		room.setConsId(Integer.parseInt(conId));
	//		room.setState("상담중");
	//		roomService.saveRoom(room);
	//		
	//		//상담원은 전달가능
	//		model.addAttribute("receiver", userId);
	//		model.addAttribute("sender", conId);
	//		model.addAttribute("who","상담원");
	//		
	//		return "chat/ConQA";
	//	}
	//	@RequestMapping("/main/chatbot")
	//	public String chatbot() {
	//		return "main/AIbotChat";
	//	}
	//	@RequestMapping(value = {"/main/Human/{userId}"}, method=RequestMethod.GET)
	//	public String Human(@PathVariable("userId") Optional<String> userId, Model model) {
	//		String uid = userId.get();
	//		model.addAttribute("sender", uid);
	//		int conId = roomService.findId(uid);
	//		model.addAttribute("receiver", Integer.toString(conId));
	//		model.addAttribute("who","사용자");
	//		
	//		return "main/HumanChat";
	//	}
	//	@RequestMapping(value = {"/main/mainQA","/main/mainQA/{userId}"}, method=RequestMethod.GET)
	//	public String confirmerList(@PathVariable("userId") Optional<String> userId, Model model) {
	//		if (userId.isPresent()){
	//			String uid = userId.get();
	//			model.addAttribute("sender", uid);
	//			int conId = roomService.findId(uid);
	//			model.addAttribute("receiver", Integer.toString(conId));
	//			model.addAttribute("who","사용자");
	//			return "main/mainQAInfoHuman";
	//			
	//		} else {
	//			return "main/mainQAInfoAI";
	//		}
	//
	//		
	//	}

	//	// 새로운 사용자 번호 생성
	//	@RequestMapping(value = "/main/idMake", method=RequestMethod.GET)
	//	public String idMake(Room room) {
	//		Random rand = new Random();
	//		long seed = System.currentTimeMillis();
	//		rand = new Random(seed);
	//		int randId = rand.nextInt(2147483547)+100;
	//		//중복 방지 - 이미 있는 사용자인지 확인
	//		while (roomService.findId(randId)!=-1) {
	//			randId = rand.nextInt(2147483647);
	//		}
	//		//room.setUserId(randId);
	//		room.setState("대기중");
	//		roomService.saveRoom(room);
	//
	//		return "redirect:/main/Human/" + randId;
	//	}


}

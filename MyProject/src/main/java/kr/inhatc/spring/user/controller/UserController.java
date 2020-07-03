package kr.inhatc.spring.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.service.UserService;

// 메모리에 올라와야 사용할 수 있음
@Controller
public class UserController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	//컨트롤러에 서비스를 불러옴
	@Autowired
	private UserService userService;
	
	//추가(보안)
	@Autowired
	private PasswordEncoder encoder;

	@RequestMapping("/")
	public String hello(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//log.debug("===========>" + principal);
		if(principal.equals("anonymousUser")==false) {
		UserDetails userDetails = (UserDetails)principal;
		String uid = userDetails.getUsername();
		//log.debug("===========>" + uid);
		model.addAttribute("userId", uid);}
		List<Users> list = userService.userList();
		model.addAttribute("list", list);
		 
		//log.debug("===========>" + "여기!!!");
		return "index";
	}

	// GET(read), POST(creat), PUT(update), DELETE(delete)
	//@GetMapping로 할 수 도
	@RequestMapping(value = "/user/userList", method=RequestMethod.GET)
	public String userList(Model model) {
		List<Users> list = userService.userList();
		model.addAttribute("list", list);
		return "user/userList";
	}

	@RequestMapping(value = "/user/userInsert", method=RequestMethod.GET)
	public String userWrite(Model model) {
		List<String> enabledList = new ArrayList<String>();
		enabledList.add("Yes");
		enabledList.add("No");
		
		List<String> authorityList = new ArrayList<String>();
		authorityList.add("ROLE_GUEST");
		authorityList.add("ROLE_MEMBER");
		authorityList.add("ROLE_ADMIN");
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("enabledList", enabledList);
		map.put("authorityList", authorityList);
		
		model.addAttribute("map",map);

		return "user/userWrite";
	}

	@RequestMapping(value = "/user/userInsert", method=RequestMethod.POST)
	public String userInsert(Users user, MultipartHttpServletRequest multipartHttpServletRequest) {
		//추가 (보안)
		if(user != null) {
			System.out.println("변경 전: " + user.getPw());
			String pw = encoder.encode(user.getPw());
			System.out.println("변경 후: " + pw);
			user.setPw(pw);
			userService.saveUsersWithFile(user, multipartHttpServletRequest);
		}
		
		return "redirect:/user/userList";
	}
	 
	@RequestMapping(value = "/signUp", method=RequestMethod.POST)
	public String signUp(Users user) {
		//추가 (보안)
		System.out.println("======>" + user);
		if(user != null) {
			System.out.println("변경 전: " + user.getPw());
			String pw = encoder.encode(user.getPw());
			System.out.println("변경 후: " + pw);
			user.setPw(pw);
			userService.saveUsers(user);
		}
		
		return "redirect:/login/login";
	}

	@RequestMapping(value = "/user/userDetail/{id}", method=RequestMethod.GET)
	public String userDetail(@PathVariable("id") String id, Model model) { //@PathVariable 경로처럼 가져옴
		Users user = userService.userDetail(id);
		model.addAttribute("user", user);
		//System.out.println("============> " + user);
		return "user/userDetail";
	}

	@RequestMapping(value = "/user/userUpdate/{id}", method=RequestMethod.POST)
	public String userUpdate(@PathVariable("id") String id, Users user) {

		// 아이디 설정
		user.setId(id);
		// 비밀번호 암호화
		String pw = encoder.encode(user.getPw());
		user.setPw(pw);
		//System.out.println("=========> " + user);
		userService.saveUsers(user);
		return "redirect:/user/userList";
	}

	@RequestMapping(value = "/user/userDelete/{id}", method=RequestMethod.GET)
	public String useDelete(@PathVariable("id") String id) {
		userService.userDelete(id);
		return "redirect:/user/userList";
	}
	
	// 내 정보 관리
	@RequestMapping(value = "/user/userMe", method=RequestMethod.GET)
	public String userMe(Model model) { //@PathVariable 경로처럼 가져옴
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String uid = userDetails.getUsername();
		log.debug("===========>" + uid);
		
		Users user = userService.userDetail(uid);
		model.addAttribute("user", user);
		return "user/userMe";
	}

	@RequestMapping(value = "/user/userMeUpdate/{id}", method=RequestMethod.POST)
	public String userMeUpdate(@PathVariable("id") String id, Users user) {

		// 아이디 설정
		user.setId(id);
		// 비밀번호 암호화
		String pw = encoder.encode(user.getPw());
		user.setPw(pw);
		userService.saveUsers(user);
		return "redirect:/";
	}
}

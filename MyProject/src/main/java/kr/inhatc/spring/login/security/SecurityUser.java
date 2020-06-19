package kr.inhatc.spring.login.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import kr.inhatc.spring.user.entity.Users;

public class SecurityUser extends User{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Users user;
	
	public SecurityUser(Users user) {
		// 암호화 처리 전까지는 패스워드 앞에 {noop} 추가 ("{noop}"+)
		super(user.getId(), user.getPw(), AuthorityUtils.createAuthorityList(user.getRole()));
		this.user = user;
	}
	

}

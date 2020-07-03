package kr.inhatc.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity//(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		//super.configure(security);
		//웹페이지 권한 설정
		security.authorizeRequests().antMatchers("/","/board/**","/comment/commentList").permitAll();
		security.authorizeRequests().antMatchers("/chat/**","/user/userMe","/user/userMeUpdate/**","/comment/**").hasAnyRole("ADMIN","MEMBER");
		security.authorizeRequests().antMatchers("/user/**").hasRole("ADMIN");
		
		// RESTfull를 사용하기 위해서는 비활성화 /사이트 간 요청 위조 - 개발시만 disable
		security.csrf().disable();
		
		// 로그인 관련 페이지와 성공시 이동할 페이지 설정
		security.formLogin().loginPage("/login/login").defaultSuccessUrl("/", true);
		
		// 실패시 이동할 페이지
		security.exceptionHandling().accessDeniedPage("/login/accessDenied");
		
		// 로그아웃 요청 시 세션을 강제 종료하고 시작 페이지로 이동
		security.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
	}	
	/***
	 * 1. 개요 : 패스워드에 암호화 처리
	 * 2. 처리 내용 : 암호화 처리
	 * 
	 * @Method Name : passwordEncoder
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


}

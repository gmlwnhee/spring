package kr.inhatc.spring.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity//(debug = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		//super.configure(security);
		//웹페이지 권한 설정
		security.authorizeRequests().antMatchers("/").permitAll();
		security.authorizeRequests().antMatchers("/user/**").hasAnyRole("MEMBER", "ADMIN");
		security.authorizeRequests().antMatchers("/board/**").hasRole("ADMIN");
		
		// RESTfull를 사용하기 위해서는 비활성화 /사이트 간 요청 위조 - 개발시만 disable
		security.csrf().disable();
		
		// 로그인 관련 페이지와 성공시 이동할 페이지 설정
		security.formLogin().loginPage("/login/login").defaultSuccessUrl("/", true);
		
		// 실패시 이동할 페이지
		security.exceptionHandling().accessDeniedPage("/login/accessDenied");
		
		// 로그아웃 요청 시 세션을 강제 종료하고 시작 페이지로 이동
		security.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
	}	



}

package kr.inhatc.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// publish할 때 사용할 url prefix와 subscire할 때 사용할 url prefix를 설정해주는 과정
		config.enableSimpleBroker("/topic");
		// 클라이언트에서 메세지 송신 시 붙여줄 prefix를 정의
		config.setApplicationDestinationPrefixes("/app");
	}

	// 해당 링크로 동작하는 SockJs를 만들어 등록
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/websocket").withSockJS();
	}

}

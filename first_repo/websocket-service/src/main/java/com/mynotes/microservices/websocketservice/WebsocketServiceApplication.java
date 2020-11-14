package com.mynotes.microservices.websocketservice;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

@SpringBootApplication
public class WebsocketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketServiceApplication.class, args);
	}
}

@Configuration
class WebSocketConfiguration {

	@Bean
	public WebSocketHandlerAdapter wsha() {
		return new WebSocketHandlerAdapter();
	}

	@Bean
	public HandlerMapping hm() {
		SimpleUrlHandlerMapping suhm = new SimpleUrlHandlerMapping();
		suhm.setOrder(10);
		Map<String, WebSocketHandler> urlMap = new HashMap<>();
		urlMap.put("/ws/files", wsh());
		suhm.setUrlMap(urlMap);

		return suhm;
	}

	@Bean
	public WebSocketHandler wsh() {
		return new WebSocketHandler() {

			@Override
			public Mono<Void> handle(WebSocketSession session) {
				// TODO Auto-generated method stub
				// return Mono.empty(Flux<T>.empty());

				ObjectMapper om = new ObjectMapper();

				Flux<WebSocketMessage> publisher = Flux.generate(new Consumer<SynchronousSink<FileEvent>>() {

					@Override
					public void accept(SynchronousSink<FileEvent> sink) {
						sink.next(new FileEvent(Long.toString(new Date().getTime()), "/a/b/c"));
					}

				}).map(it -> {
					try {
						return om.writeValueAsString(it);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
						return "";
					}
				}).map(it -> session.textMessage(it))
						.delayElements(Duration.ofSeconds(5L));

				return session.send(publisher);
			}

		};
	}
}

class FileEvent {
	private String sessionId;
	private String path;

	public FileEvent(String sessionId, String path) {
		super();
		this.sessionId = sessionId;
		this.path = path;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
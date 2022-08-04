package com.webrtc.videoChattingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class VideoChattingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoChattingServiceApplication.class, args);
	}

}

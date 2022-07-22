package com.webrtc.videoChattingService.controller.socket;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;


@Slf4j
public class SignalHandler extends TextWebSocketHandler {


    // message types, used in signalling:
    // text message
    private static final String MSG_TYPE_TEXT = "text";
    // SDP Offer message
    private static final String MSG_TYPE_OFFER = "offer";
    // SDP Answer message
    private static final String MSG_TYPE_ANSWER = "answer";
    // New ICE Candidate message
    private static final String MSG_TYPE_ICE = "ice";
    // join room data message
    private static final String MSG_TYPE_JOIN = "join";
    // leave room data message
    private static final String MSG_TYPE_LEAVE = "leave";

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) {
        System.out.println("클라이언트 나감 == " + session);

    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) {
        // webSocket has been opened, send a message to the client
        // when data field contains 'true' value, the client starts negotiating
        // to establish peer-to-peer connection, otherwise they wait for a counterpart
        System.out.println("클라이언트 접속 ==" + session);
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage textMessage) throws IOException {
        System.out.println("textMessage" + textMessage);

        TextMessage textMessage1 = new TextMessage("하이");
        session.sendMessage(textMessage1);
    }

    private void sendMessage(WebSocketSession session, WebSocketMessage message) {

    }



}

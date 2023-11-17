package instaface.backend.Chat.WebSocket;//package com.example.instaFace.WebSocket;
//
//import com.example.instaFace.Models.ChatLog;
//import jakarta.websocket.Session;
//import lombok.RequiredArgsConstructor;
//import java.awt.TrayIcon.MessageType;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.awt.*;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class WebSocketEventListener {
//    private SimpMessageSendingOperations messageSendingOperations;
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(
//            SessionDisconnectEvent event
//    )
//    {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if(username != null){
//            log.info("User disconnected: {}", username);
//            var chatLog = ChatLog.builder()
//                    .messageType(MessageType.INFO)
//                    .text(username)
//                    .build();
//            messageSendingOperations.convertAndSend("/topic/public", chatLog);
//        }
//
//    }
//}

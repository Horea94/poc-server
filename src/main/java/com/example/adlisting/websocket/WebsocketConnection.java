package com.example.adlisting.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WebsocketConnection extends TextWebSocketHandler {
  private static final Logger log = LogManager.getLogger(WebsocketConnection.class);
  static Map<String, WebSocketSession> connections = new HashMap<>();

  public WebsocketConnection() {
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    log.info("Connected with id: " + session.getId());
    connections.put(session.getId(), session);
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    log.info("Received message for session: " + session.getId() + " content: " + message.getPayload());
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    log.error(Arrays.toString(exception.getStackTrace()));
    connections.remove(session.getId());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    log.info("Connection with id: " + session.getId() + " closed with status: " + status);
    connections.remove(session.getId());
  }

  public void broadcast(String msg) {
    for(WebSocketSession webSocketSession : connections.values()) {
      if(webSocketSession.isOpen()) {
        try {
          webSocketSession.sendMessage(new TextMessage("test"));
        } catch (IOException e) {
          log.error("Error sending to: " + webSocketSession.getId());
        }
      }
    }
  }
}

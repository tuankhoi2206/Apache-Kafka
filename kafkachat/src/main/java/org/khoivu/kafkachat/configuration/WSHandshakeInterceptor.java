package org.khoivu.kafkachat.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WSHandshakeInterceptor implements HandshakeInterceptor {

  public static Map<String, String> sessions = new ConcurrentHashMap<String, String>();
  public static List<String> lstSession = new ArrayList<String>();

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    return true;
  }

  /**
   * https://exceptionshub.com/how-to-retrieve-the-session-id-from-spring-websocket-on-initial-handshake-and-to-be-used-later-on-2.html
   */
  @Override
  public void afterHandshake(ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    if (serverHttpRequest instanceof ServletServerHttpRequest) {
      ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
      HttpSession session = servletRequest.getServletRequest().getSession();
      String sessionId = session.getId();
      sessions.put(sessionId, sessionId);
      lstSession.add(sessionId);
      log.info("AfterHandshake session Id {}", sessionId);
    }
  }
}

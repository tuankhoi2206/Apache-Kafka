package org.khoivu.kafkachat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * https://docs.spring.io/autorepo/docs/spring-security/4.2.x/reference/html/websocket.html
 * Enable STOMP messaging
 *  At this point: you will see how to annotate controller methods with @MessageMapping to handle STOMP messages
 *  within SpringMVC in a way very similar to how @RequestMapping annotated methods handle HTTP requests
 * </pre>
 * 
 * @author Admin
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

  // register end point to client
  private final String[] END_POINTS = new String[] {//
      "ws-chat", //
      "notify"//
  };

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry//
        .addEndpoint(END_POINTS)//
        .setAllowedOrigins("*")//
        .addInterceptors(new WSHandshakeInterceptor())//
        .withSockJS();// you're saying that you want SockJs to be enabled ,and for its fallback to
                      // go into effect if WebSocket can't be used
  }
  
  
  private final String DESTINATION_PREFIX = "/app";

  // set up a simple memory-based message broker ???
  private final String[] MESSAGE_BORKERS = new String[] {//
      "/topic/", //
      "/add-friend/", //
      "/remove-friend/"//
  };

  /**
   * <pre>
   * Reference at:
   *    https://www.baeldung.com/spring-websockets-sendtouser
   *    Spring in Action at pages 496
   * Note:
   *    If you don't override it, you will get a simple in-memory message broker
   *    configured to handle message prefixed with /topic
   * </pre>
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes(DESTINATION_PREFIX);
    registry.enableSimpleBroker(MESSAGE_BORKERS);
    //Config Message Broker( RabbitMQ, ActiveMQ...)
  }

}

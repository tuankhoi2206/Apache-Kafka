package org.khoivu.kafkachat.controller;

import org.khoivu.kafkachat.model.Message;
import org.khoivu.kafkachat.service.ChatProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ChatController {

  @Autowired
  private ChatProducerService chatProducerService;

  /**
   * Step 1
   * 
   * @param message
   */
  @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
  public void sendMessage(@RequestBody Message message) {
    chatProducerService.sendMessage(message);
  }

  /**
   * <pre>
   * Return an individual message to both the user and simultaneously broadcast that message to
   * everyone
   * all messages are broadcasted to all connected users by using the @SendTo annotation.
   * Reference at:
   *     https://github.com/spring-projects/spring-framework/issues/21430
   * </pre>
   * 
   * @param message
   * @return
   */
  @MessageMapping("/sendMessage")
  @SendTo("/topic/group")
  public Message broadCastGroupMessage(@Payload Message message) {
    log.info("BroadCastGroupMessage {}", message);
    return message;
  }
}

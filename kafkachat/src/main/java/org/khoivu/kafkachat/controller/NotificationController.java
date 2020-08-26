package org.khoivu.kafkachat.controller;

import javax.ws.rs.core.MediaType;
import org.khoivu.kafkachat.constants.KafkaConstants;
import org.khoivu.kafkachat.constants.MessageType;
import org.khoivu.kafkachat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NotificationController {

  @Autowired
  private KafkaTemplate<String, Message> kafkaTemplate;

  @PostMapping(value = "/api/addfriend", consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public void addFriend(@RequestBody Message message) {
    log.info("Send request add friend {}", message);
    kafkaTemplate.send(KafkaConstants.KAFKA_COMMON_NOTIFY_TOPIC, message);
  }

  @PostMapping(value = "/api/removefriend", consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public void removeFriend(@RequestBody Message message) {
    log.info("Send request remove friend {}", message);
    kafkaTemplate.send(KafkaConstants.KAFKA_COMMON_NOTIFY_TOPIC, message);
  }

  @PostMapping(value = "/api/notify", consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public void notify(@RequestBody Message message) {
    log.info("Send notify {}", message);
    log.info("Message Type {}", message.getMessageType());
    kafkaTemplate.send(KafkaConstants.KAFKA_COMMON_NOTIFY_TOPIC, message);
  }

  /**
   * User typing something
   * https://nulpointerexception.com/2019/05/14/a-tutorial-on-implementing-a-chat-application-like-whatsapp-using-websocket-and-spring/
   * 
   * @param message
   */
  @PostMapping(value = "/api/userTyping", consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public void userTyping(@RequestBody Message message) {
    log.info("Send request user typing {}", message);
    kafkaTemplate.send(KafkaConstants.KAFKA_USER_TYPING_TOPIC, message);
  }

  @MessageMapping("/sendNotifyToUser")
  @SendToUser("/topic/group")
  public Message sendNotifyToUser(@Payload Message message) {
    return message;
  }
}

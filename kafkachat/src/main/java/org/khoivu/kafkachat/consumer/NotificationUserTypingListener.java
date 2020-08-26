package org.khoivu.kafkachat.consumer;


import org.khoivu.kafkachat.constants.KafkaConstants;
import org.khoivu.kafkachat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NotificationUserTypingListener {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  // private String DIST_PAGE_CLIENT = "/notification/page/{pageId}";
  private String DIST_PAGE_CLIENT = "/notification/page/%s";

  /**
   * User typing page
   * 
   * @param message
   */
  @KafkaListener(//
      topics = KafkaConstants.KAFKA_USER_TYPING_TOPIC, //
      groupId = KafkaConstants.KAFKA_USER_TYPING_GROUP_ID)
  public void listenUserTyping(Message message) {
    log.info("Listen user typing {}", message);
    String sendTo = String.format(DIST_PAGE_CLIENT, message.getPageId());
    log.info("Send distination user typing {}", sendTo);
    messagingTemplate.convertAndSend(sendTo, message);
  }
}

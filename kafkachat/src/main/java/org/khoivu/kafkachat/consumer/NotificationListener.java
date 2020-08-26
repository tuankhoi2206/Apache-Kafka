package org.khoivu.kafkachat.consumer;

import org.khoivu.kafkachat.constants.KafkaConstants;
import org.khoivu.kafkachat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NotificationListener {

  private final String DIST_NOTIFICATION_CLIENT = "/notification-server";

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  /**
   * Consumer add new friend
   * 
   * @param message
   */
  @KafkaListener(//
      topics = KafkaConstants.KAFKA_COMMON_NOTIFY_TOPIC, //
      groupId = KafkaConstants.KAFKA_COMMON_NOTIFY_GROUP_ID)
  public void sendRequestAddFriend(@Payload Message message) {
    log.info("Send request add friend {}", message);
    messagingTemplate.convertAndSend(DIST_NOTIFICATION_CLIENT, message);
    /*
     * Group  
     */
    switch (message.getMessageType()) {
      case ADD_FRIEND:
        // Do something.
        // For instance: save something to data
        message.setContent("Add new friend ......");
        // messagingTemplate.convertAndSendToUser(message.getReceiver(), DIST_CLIENT, message);
        break;
      case REMOVE_FRIEND:
        // do something
        message.setContent("Remove friend ......");
        // messagingTemplate.convertAndSendToUser(message.getReceiver(), DIST_CLIENT, message);
        break;
      case USER_COMMENT:
        // do something
        message.setContent("User comment ......");
        break;
      case USER_SHARE:
        // do something
        message.setContent("User Share ......");
        break;
      case USER_TAG:
        // do something
        message.setContent("User Tag ......");
        break;
      default:
        break;
    }// end switch
    messagingTemplate.convertAndSend(DIST_NOTIFICATION_CLIENT, message);
  }
}

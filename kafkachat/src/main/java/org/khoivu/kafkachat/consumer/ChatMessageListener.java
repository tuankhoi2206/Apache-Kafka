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
public class ChatMessageListener {

  @Autowired
  private SimpMessagingTemplate template;

  /**
   * Step 2 Listen.
   * 
   * https://programmer.ink/think/5cfad71ed3990.html
   * https://tech.trello.com/why-we-chose-kafka/
   * @param message the message
   */
  @KafkaListener(//
      topics = KafkaConstants.KAFKA_TOPIC, //
      groupId = KafkaConstants.GROUP_ID)
  public void listen(Message message) {
    // Step#3: Send to websocket distination topic
    log.info("Listener send message {}", message);
    template.convertAndSend("/topic/group", message);

    // List<String> lstSession = WSHandshakeInterceptor.lstSession;
    // String sessionId = lstSession.get(1);
    // log.info("Send to sessionId {}", sessionId);
    // template.convertAndSendToUser(sessionId, "/topic/group", message);
  }
}

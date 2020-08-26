package org.khoivu.kafkachat.service;

import java.time.LocalDateTime;
import org.khoivu.kafkachat.constants.KafkaConstants;
import org.khoivu.kafkachat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatProducerService {

  @Autowired
  private KafkaTemplate<String, Message> kafkaTemplate;

  public void sendMessage(@RequestBody Message message) {

    log.info("Send message {}", message);
    log.info("Sender {}", message.getSender());
    log.info("Message {}", message.getContent());

    message.setTimestamp(LocalDateTime.now().toString());
    ListenableFuture<SendResult<String, Message>> future =
        this.kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message);
    future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {

      @Override
      public void onSuccess(SendResult<String, Message> result) {
        // TODO Auto-generated method stub
        log.info("Sent message: " + message//
            + " with offset: " + result.getRecordMetadata().offset());
      }

      @Override
      public void onFailure(Throwable ex) {
        // TODO Auto-generated method stub
        log.error("Unable to send message: " + message, ex);
      }
    });
  }
}

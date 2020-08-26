package org.khoivu.kafkachat.model;

import org.khoivu.kafkachat.constants.MessageType;

public class Message {

  private String sender;
  private String receiver;
  private String content;
  private String timestamp;
  private String pageId;
  private MessageType messageType; 
  
  public Message() {}

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public Message(String sender, String content) {
    this.sender = sender;
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Message {" + "sender='" + sender + '\''//
        + " receiver='" + receiver + '\''//
        + ", content='" + content + '\''//
        + " pageId='" + pageId + '\''//
        + ", timestamp='" + timestamp + '\''//
        + '}';
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public MessageType getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }
}

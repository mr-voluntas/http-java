package voluntas.tcpjava.shared;

public class Message {

  private MessageType messageType;
  private String sender;
  private String content;

  public Message(MessageType messageType, String sender, String content) {
    this.messageType = messageType;
    this.sender = sender;
    this.content = content;
  }

  public MessageType getMessageType() {
    return messageType;
  }

  public String getSender() {
    return sender;
  }

  public String getContent() {
    return content;
  }
}

package voluntas.tcpjava.shared;

public class MessageFactory {

  private String sender;

  public MessageFactory(String sender) {
    this.sender = sender;
  }

  public Message clientConnected() {
    return new Message(MessageType.CONNECT, sender, "client connected");
  }

  public Message message(String content) {
    return new Message(MessageType.MESSAGE, sender, content);
  }

  public Message clientDisconnected() {
    return new Message(MessageType.QUIT, sender, "client disconnected");
  }
}

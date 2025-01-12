package voluntas.tcpjava.server;

public class ChatServerMain {

  public static void main(String[] args) {
    var chatServer = new ChatServer(42069);

    chatServer.start();
  }
}

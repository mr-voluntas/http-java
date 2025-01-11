package voluntas.tcpjava.client;

public class ChatMain {

  public static void main(String[] args) {
    ChatClient chatClient = new ChatClient("127.0.0.1", 42069);

    chatClient.start();
  }

}

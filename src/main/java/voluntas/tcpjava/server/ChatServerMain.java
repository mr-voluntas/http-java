package voluntas.tcpjava.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServerMain {

  public static void main(String[] args) {
    final int PORT = 42069;
    var chatServer = new ChatServer();

    ServerSocket server = chatServer.createServer(PORT);

    System.out.println("Server started on port " + PORT);
    System.out.println("Waiting for connections...");

    while (true) {
      try {
        new ClientHandler(server.accept()).run();
      } catch (IOException e) {
        throw new RuntimeException("Failed to connect client", e);
      }
    }

  }
}
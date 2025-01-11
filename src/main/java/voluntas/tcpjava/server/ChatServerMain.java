package voluntas.tcpjava.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServerMain {

  public static void main(String[] args) {
    final int PORT = 42069;
    var chatServer = new ChatServer();

    ServerSocket server = chatServer.createServer(PORT);
    CopyOnWriteArrayList<Socket> clients = new CopyOnWriteArrayList<>();

    System.out.println("Server started on port " + PORT);
    System.out.println("Waiting for connections...\n");

    while (true) {
      try {
        Socket newClient = server.accept();
        clients.add(newClient);
        for (Socket socket : clients) {
          System.out.println(socket.getInetAddress());
        }
        new ClientThread(newClient, clients).start();
      } catch (IOException e) {
        throw new RuntimeException("Failed to connect client", e);
      }
    }

  }
}

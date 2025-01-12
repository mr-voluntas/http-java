package voluntas.tcpjava.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {

  private int port;
  private CopyOnWriteArrayList<Socket> clients;
  private ServerSocket server;

  public ChatServer(int port) {
    this.port = port;
    this.clients = new CopyOnWriteArrayList<>();
  }

  public void start() {
    try {
      server = new ServerSocket(port);
      System.out.printf("Server started on port %d\n", port);
      System.out.println("Waiting for connections...\n");

      connectClients();

    } catch (IllegalArgumentException e) {
      throw new RuntimeException(
          "port parameter is outside the specified range of valid port values, which is between 0 and 65535, inclusive.",
          e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void connectClients() {
    while (true) {
      try {
        Socket newClient = server.accept();
        clients.add(newClient);
        new ClientThread(newClient, clients).start();
      } catch (IOException e) {
        throw new RuntimeException("Failed to connect client", e);
      }
    }
  }

}

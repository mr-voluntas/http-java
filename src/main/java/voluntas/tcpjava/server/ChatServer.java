package voluntas.tcpjava.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;

import voluntas.tcpjava.shared.Client;
import voluntas.tcpjava.shared.MessageProcessorMain;

public class ChatServer {

  private int port;
  private CopyOnWriteArrayList<Client> connectedClients;
  private ServerSocket server;
  private Gson gson;

  public ChatServer(int port) {
    this.port = port;
    this.connectedClients = new CopyOnWriteArrayList<>();
    this.gson = new Gson();
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
    MessageProcessorMain messageProcessor = new MessageProcessorMain(connectedClients, gson);
    while (true) {
      try {
        Socket newClientConnection = server.accept();
        Client newClient = new Client(newClientConnection);
        connectedClients.add(newClient);
        new ClientThread(newClient, messageProcessor, gson).start();
      } catch (IOException e) {
        throw new RuntimeException("Failed to connect client", e);
      }
    }
  }

}

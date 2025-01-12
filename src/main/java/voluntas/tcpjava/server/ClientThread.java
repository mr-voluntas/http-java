package voluntas.tcpjava.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

class ClientThread extends Thread {

  private Socket clientConnection;
  private BufferedReader clientConnectionIn;
  private CopyOnWriteArrayList<Socket> clients;

  ClientThread(Socket connection, CopyOnWriteArrayList<Socket> clients) {
    this.clientConnection = connection;
    this.clients = clients;
  }

  @Override
  public void run() {
    try {
      clientConnectionIn = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));

      while (true) {
        try {
          String messageFromClient = clientConnectionIn.readLine();
          if (messageFromClient.equals("/quit")) {
            clientConnection.close();
            clients.remove(clientConnection);
            System.out.println("client disconnected");
            break;
          }
          System.out.println(messageFromClient);
          broadcastToAllClients(messageFromClient);
        } catch (SocketException e) {
          throw new RuntimeException(e.getMessage());
        }
        interrupt();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  private void broadcastToAllClients(String message) {
    for (Socket client : clients) {
      if (clientConnection != client) {
        try (PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);) {
          clientOut.println(message);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

}

package voluntas.tcpjava.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientThread extends Thread {

  private Socket clientSocket;
  private BufferedReader clientInput;

  ClientThread(Socket connection) {
    this.clientSocket = connection;
  }

  @Override
  public void run() {

    try {
      clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      String username = clientInput.readLine();

      System.out.println("New client connected: " + username);

      boolean clientConnected = true;
      while (clientConnected) {
        try {
          String clientMessage = clientInput.readLine();
          if (clientMessage.equals("quit")) {
            clientConnected = false;
          } else {
            System.out.println(username + ": " + clientMessage);
          }
        } catch (NullPointerException e) {
          clientConnected = false;
        }
      }
      clientSocket.close();
      System.out.println(username + " disconnected");
      interrupt();
    } catch (

    IOException e) {
      throw new RuntimeException(e);
    }

  }

}

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

      while (true) {
        System.out.println(username + ": " + clientInput.readLine());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}

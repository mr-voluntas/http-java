package voluntas.tcpjava.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientHandler implements Runnable {

  private Socket clientSocket;
  private BufferedReader clientInput;

  ClientHandler(Socket connection) {
    this.clientSocket = connection;
  }

  @Override
  public void run() {
    System.out.println("New client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

    try {
      clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      while (true) {
        System.out.println("Message: " + clientInput.readLine());
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

}

package voluntas.tcpjava.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

  private final String ipAddress;
  private final int port;

  public ChatClient(String ipAddress, int port) {
    this.ipAddress = ipAddress;
    this.port = port;
  }

  public void start() {
    try (var userInput = new Scanner(System.in);) {

      System.out.print("Please enter a username: ");
      String username = userInput.nextLine();

      try (var clientConnection = new Socket(ipAddress, port);
          var clientConnectionIn = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
          var clientConnectionOut = new PrintWriter(clientConnection.getOutputStream(), true);) {

        clientConnectionOut.println(username + " has connected");

        new Thread(new MessageListener(clientConnectionIn)).start();

        while (true) {
          String newMessage = userInput.nextLine();
          if (newMessage.equals("quit")) {
            clientConnectionOut.println("quit");
            break;
          }
          clientConnectionOut.println(username + ": " + newMessage);
        }

      } catch (ConnectException e) {
        throw new RuntimeException("Failed to connect to the server. Please check server is running", e);
      } catch (IOException e) {
        throw new RuntimeException("Failed to start client", e);
      } finally {
        System.out.printf("Client connected to server (%s:%d)\n", ipAddress, port);
      }
    }
  }

  private class MessageListener implements Runnable {
    private BufferedReader clientConnectionIn;

    MessageListener(BufferedReader clientConnectionIn) {
      this.clientConnectionIn = clientConnectionIn;
    };

    @Override
    public void run() {
      try {
        String messageFromServer;
        while ((messageFromServer = clientConnectionIn.readLine()) != null) {
          System.out.println(messageFromServer);
        }
      } catch (IOException e) {
        throw new RuntimeException("Error getting message from the server.", e);
      }
    }
  }

}

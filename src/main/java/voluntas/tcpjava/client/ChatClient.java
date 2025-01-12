package voluntas.tcpjava.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

  private Scanner userInput;
  private Socket clientConnection;
  private BufferedReader clientConnectionIn;
  private PrintWriter clientConnectionOut;
  private String username;
  private MessageListener messageListenerThread;

  public ChatClient(String ipAddress, int port) {
    try {
      this.clientConnection = new Socket(ipAddress, port);
      this.clientConnectionIn = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
      this.clientConnectionOut = new PrintWriter(clientConnection.getOutputStream(), true);
    } catch (ConnectException e) {
      throw new RuntimeException("Client failed to connect to the server. Please check server is running", e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void start() {
    userInput = new Scanner(System.in);
    System.out.print("Please enter a username: ");
    username = userInput.nextLine();

    clientConnectionOut.println(username + " has connected");

    // Listens for messages sent from the server.
    messageListenerThread = new MessageListener(clientConnectionIn);
    messageListenerThread.start();

    while (true) {
      String newMessage = userInput.nextLine();
      if (newMessage.equals("/quit")) {
        clientConnectionOut.println("/quit");
        break;
      }
      clientConnectionOut.println(username + ": " + newMessage);
    }

    disconnectFromServer();

  }

  private void disconnectFromServer() {
    try {
      clientConnection.close();
      messageListenerThread.interrupt();
      userInput.close();
      System.out.println("Disconnected from server");
    } catch (IOException e) {
      throw new RuntimeException("Failed to close client connection", e);
    }
  }

  private class MessageListener extends Thread {
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
        if (!e.getMessage().equalsIgnoreCase("Socket closed")) {
          throw new RuntimeException();
        }
      }
    }

  }

}

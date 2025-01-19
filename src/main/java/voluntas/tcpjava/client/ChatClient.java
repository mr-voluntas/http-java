package voluntas.tcpjava.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import com.google.gson.Gson;

import voluntas.tcpjava.shared.Client;
import voluntas.tcpjava.shared.Message;
import voluntas.tcpjava.shared.MessageFactory;

public class ChatClient {

  private final String ipAddress;
  private final int port;

  private Client client;

  private MessageListener messageListenerThread;
  private Gson gson = new Gson();
  private Scanner userInput = new Scanner(System.in);

  public ChatClient(String ipAddress, int port) {
    this.ipAddress = ipAddress;
    this.port = port;
  }

  public void start() {
    // Creates a new client connected to the server.
    initClient();

    System.out.print("Please enter a username: ");
    client.setUsername(userInput.nextLine());
    System.out.println("");

    MessageFactory messageFactory = new MessageFactory(client.getUsername());

    sendMessage(messageFactory.clientConnected());

    // Thread listening for messages sent from the server.
    messageListenerThread = new MessageListener(client);
    messageListenerThread.start();

    while (true) {
      String message = userInput.nextLine();
      if (message.equals("/quit")) {
        sendMessage(messageFactory.clientDisconnected());
        break;
      } else {
        sendMessage(messageFactory.message(message));
      }
    }
    disconnectFromServer();
  }

  private void initClient() {
    try {
      client = new Client(new Socket(ipAddress, port));
    } catch (ConnectException e) {
      throw new RuntimeException("Client failed to connect to the server. Please check server is running", e);
    } catch (UnknownHostException e) {
      throw new RuntimeException("Client failed to connect to server with the specified ip-address and port", e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void sendMessage(Message message) {
    String messageJson = gson.toJson(message);
    client.getOutputStream().println(messageJson);
  }

  private void disconnectFromServer() {
    try {
      client.getSocket().close();
      messageListenerThread.interrupt();
      userInput.close();
      System.out.println("Disconnected from server");
    } catch (IOException e) {
      throw new RuntimeException("Failed to close client connection", e);
    }
  }

  private class MessageListener extends Thread {
    private Client client;

    MessageListener(Client client) {
      this.client = client;
    };

    @Override
    public void run() {
      try {
        String messageFromServer;
        while ((messageFromServer = client.getInputStream().readLine()) != null) {
          Message message = gson.fromJson(messageFromServer, Message.class);
          System.out.printf("%s: %s\n", message.getSender(), message.getContent());
        }
      } catch (IOException e) {
        if (!e.getMessage().equalsIgnoreCase("Socket closed")) {
          throw new RuntimeException();
        }
      }
    }

  }

}

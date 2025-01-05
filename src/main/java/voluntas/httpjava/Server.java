package voluntas.httpjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private static class ClientThread implements Runnable {

    private Socket clientSocket;
    private BufferedReader clientInput;

    private ClientThread(Socket connection) {
      this.clientSocket = connection;
    }

    @Override
    public void run() {
      System.out.println("New client: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

      try {
        clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        while (true) {
          String newMessage = clientInput.readLine();

          System.out.println("Message: " + newMessage);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      // PrintWriter clientOutput = new
      // PrintWriter(connection.getOutputStream(), true);

    }

  }

  private static ServerSocket createServer(int port) throws IOException {
    try {
      ServerSocket server = new ServerSocket(port);
      System.out.println("Server started on port " + port);
      return server;
    } catch (IllegalArgumentException iae) {
      System.out.println(
          "Port is outside the valid range, which is between 0 and 65535");
      throw iae;
    }
  }

  public static void main(String[] args) {

    try (ServerSocket server = createServer(42069)) {
      while (true) {
        new ClientThread(server.accept()).run();
      }

    } catch (IOException e) {
      System.out.println("Fatel Error: error starting the server");
      e.printStackTrace();
    }

  }
}

package voluntas.tcpjava.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

class ClientThread extends Thread {

  private Socket clientSocket;
  private CopyOnWriteArrayList<Socket> clients;

  ClientThread(Socket connection, CopyOnWriteArrayList<Socket> clients) {
    this.clientSocket = connection;
    this.clients = clients;
  }

  @Override
  public void run() {

    try {
      var clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      while (true) {
        try {
          String clientMessage = clientInput.readLine();
          if (clientMessage.equals("quit")) {
            clients.remove(clientSocket);
            System.out.println("client disconnected");
            break;
          } else {
            System.out.println(clientMessage);
            for (Socket socket : clients) {
              if (clientSocket != socket) {
                PrintWriter clientsOut = new PrintWriter(socket.getOutputStream(), true);
                clientsOut.println(clientMessage);
              }
            }
          }
        } catch (NullPointerException e) {
          break;
        }
      }
      clientSocket.close();
      interrupt();
    } catch (

    IOException e) {
      throw new RuntimeException(e);
    }

  }

}

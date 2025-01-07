package voluntas.tcpjava.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

  public static void main(String[] args) {

    try (Socket clientConnection = new Socket("127.0.0.1", 42069);
        Scanner userInput = new Scanner(System.in);
        PrintWriter out = new PrintWriter(clientConnection.getOutputStream(), true);) {

      while (true) {
        System.out.print("New message: ");

        String newMessage = userInput.nextLine();

        out.println(newMessage);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}

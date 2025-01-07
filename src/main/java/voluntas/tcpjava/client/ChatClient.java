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

      System.out.print("Please enter your username: ");

      out.println(userInput.nextLine());

      while (true) {
        System.out.print("New message: ");
        out.println(userInput.nextLine());
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}

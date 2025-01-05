package voluntas.httpjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

  public static void main(String[] args) {

    try (Socket client = new Socket("127.0.0.1", 42069)) {

      PrintWriter out = new PrintWriter(client.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

      while (true) {
        System.out.print("New message: ");
        Scanner userInput = new Scanner(System.in);

        String newMessage = userInput.nextLine();

        out.println(newMessage);
        // System.out.println("Messge from server: " + in.readLine());
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}

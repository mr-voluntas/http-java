package voluntas.tcpjava.shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

  private String username;
  private final Socket socket;
  private BufferedReader inputStream;
  private PrintWriter outputStream;

  public Client(final Socket socket) {
    this.socket = socket;
    try {
      this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.outputStream = new PrintWriter(socket.getOutputStream(), true);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Socket getSocket() {
    return socket;
  }

  public BufferedReader getInputStream() {
    return this.inputStream;
  }

  public PrintWriter getOutputStream() {
    return this.outputStream;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

}

package voluntas.tcpjava.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer {

  public ServerSocket createServer(int port) {
    try {
      ServerSocket server = new ServerSocket(port);
      return server;
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(
          "port parameter is outside the specified range of valid port values, which is between 0 and 65535, inclusive.",
          e);
    } catch (IOException e) {
      throw new RuntimeException(
          "Could not start the server. ", e);
    }
  }

}

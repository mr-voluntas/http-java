package voluntas.tcpjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import voluntas.tcpjava.server.ChatServer;

public class ChatServerTest {

  ChatServer chatServer = new ChatServer();

  @Test
  void shouldStartCorrectly() {
    try (var server = chatServer.createServer(42069)) {
      assertTrue(server.isClosed() == false);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void shouldThrowErrorWithInvalidPort() {
    assertThrows(RuntimeException.class, () -> {
      chatServer.createServer(123456789);
    });
  }
}

package voluntas.tcpjava.server;

import java.io.IOException;

import com.google.gson.Gson;

import voluntas.tcpjava.shared.Client;
import voluntas.tcpjava.shared.Message;
import voluntas.tcpjava.shared.MessageProcessorMain;

class ClientThread extends Thread {

  private Client client;
  private MessageProcessorMain messageProcessor;
  private Gson gson;

  ClientThread(Client client, MessageProcessorMain messageProcessor, Gson gson) {
    this.client = client;
    this.messageProcessor = messageProcessor;
    this.gson = gson;
  }

  @Override
  public void run() {

    try {
      String messageFromClient;
      while ((messageFromClient = client.getInputStream().readLine()) != null) {
        Message message = gson.fromJson(messageFromClient, Message.class);
        messageProcessor.process(client, message);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}

package voluntas.tcpjava.shared.processors;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import com.google.gson.Gson;

import voluntas.tcpjava.shared.Client;
import voluntas.tcpjava.shared.Message;
import voluntas.tcpjava.shared.MessageProcessor;

public class MessageContentProcessor implements MessageProcessor {

  private CopyOnWriteArrayList<Client> clients;
  private Gson gson;
  private static final Logger LOGGER = Logger.getLogger(MessageContentProcessor.class.getName());

  public MessageContentProcessor(CopyOnWriteArrayList<Client> clients, Gson gson) {
    this.clients = clients;
    this.gson = gson;
  }

  @Override
  public void process(Client currentClient, Message message) {
    String messageJson = gson.toJson(message);
    LOGGER.info(messageJson);
    for (Client client : clients) {
      if (currentClient != client) {
        client.getOutputStream().println(messageJson);
      }
    }
  }
}

package voluntas.tcpjava.shared;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;

import voluntas.tcpjava.shared.processors.ConnectMessageProcessor;
import voluntas.tcpjava.shared.processors.MessageContentProcessor;
import voluntas.tcpjava.shared.processors.QuitMessageProcessor;

public class MessageProcessorMain implements MessageProcessor {

  private Map<MessageType, MessageProcessor> processors;

  public MessageProcessorMain(CopyOnWriteArrayList<Client> clients, Gson gson) {
    processors = new HashMap<>();
    processors.put(MessageType.CONNECT, new ConnectMessageProcessor(clients, gson));
    processors.put(MessageType.MESSAGE, new MessageContentProcessor(clients, gson));
    processors.put(MessageType.QUIT, new QuitMessageProcessor(clients, gson));
  }

  @Override
  public void process(Client client, Message message) {
    MessageProcessor processor = processors.get(message.getMessageType());
    if (processor != null) {
      processor.process(client, message);
    } else {
      throw new RuntimeException("No processor found for this message type: " + message.getMessageType());
    }
  }

}

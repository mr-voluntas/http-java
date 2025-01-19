package voluntas.tcpjava.shared;

public interface MessageProcessor {

  public void process(Client client, Message message);

}

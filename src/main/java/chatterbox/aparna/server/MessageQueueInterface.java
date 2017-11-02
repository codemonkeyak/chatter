package chatterbox.aparna.server;


public interface MessageQueueInterface {

  public void addToMsgQueue(String str);

  public String getAndDeleteFirstMsg();







}

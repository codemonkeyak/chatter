package chatterbox.aparna.server;

import java.net.*;
import java.util.*;
import java.io.*;

import chatterbox.aparna.server.ChatUserConnection;
import chatterbox.aparna.server.MessageQueue;
import chatterbox.aparna.server.MessageQueueInterface;

public class Server {

  public ServerSocket server = null;
	public Socket socket = null;
  public int port = 0;
  public ChatUserConnection chat = null;
	public MessageQueueInterface msgQueue = null;
	public LinkedList<ChatUserConnection> chatUserList = null;
  public MessageRouter msgRouter = null;

  public static void main(String args[]) {
    Server serv = new Server();
    serv.getInteger(args[0]);
		serv.msgQueue = new MessageQueue();
	  serv.chatUserList = new LinkedList<ChatUserConnection>();
	  serv.msgRouter = MessageRouter.getMessageRouter(serv.msgQueue,serv.chatUserList);
		serv.createConnection();
	}

  private int getInteger(final String str) {
		try {
      port = Integer.parseInt(str);
		} catch (NumberFormatException e) {
      System.err.println("String is not a number "+e);
		}
		return port;
	}

  public void createConnection() {
	  try {
      server = new ServerSocket(port);
      while(true) {
		    socket = server.accept();
        chat = ChatUserConnection.getChatUserConnection(socket,msgQueue);
				synchronized(chatUserList) {
          chatUserList.add(chat);
				}
      }
	  } catch (IOException e) {
      System.err.println("IOException caught");
		}
  }

 }












package chatterbox.aparna.server;

import java.io.*;
import java.util.*;
import java.net.*;

import chatterbox.aparna.server.MessageQueueInterface;
import chatterbox.aparna.server.ChatUserConnection;

public class MessageRouter implements Runnable {
  
  private MessageQueueInterface msgQueue = null;
	public LinkedList<ChatUserConnection> chatUserslist = null;
  
  private MessageRouter(MessageQueueInterface msgQ,LinkedList<ChatUserConnection> chatUserlist) {
    this.msgQueue = msgQ;
		this.chatUserslist = chatUserlist;
  }

	public static MessageRouter getMessageRouter(MessageQueueInterface msgQ,LinkedList<ChatUserConnection> chatUserlist) {
    MessageRouter mr = new MessageRouter(msgQ,chatUserlist);
		new Thread(mr).start();
		return mr;
	}

  
	public void run() {
	  String firstInMsgQ = "";
    while(firstInMsgQ != null) {
		  firstInMsgQ = msgQueue.getAndDeleteFirstMsg();
			synchronized(chatUserslist) {
		    for(int i=0; i< chatUserslist.size(); i++) {
		      chatUserslist.get(i).writeToClient(firstInMsgQ);
		    }
			}
		}
	}







}

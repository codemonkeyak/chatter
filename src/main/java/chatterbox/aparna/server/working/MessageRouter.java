package chatterbox.aparna.server;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.LinkedList;

import chatterbox.aparna.server.InMemoryMessageQueue;
import chatterbox.aparna.server.ChatUserConnection;

public class MessageRouter implements Runnable {

 InMemoryMessageQueue msgQueue = null;
 LinkedList<ChatUserConnection> connectionsList = null;

 public MessageRouter(InMemoryMessageQueue msgQ, LinkedList<ChatUserConnection> conList) {
   this.msgQueue = msgQ;
	 this.connectionsList = conList;
	 new Thread(this).start();
 }
  

 public void broadcastMsgs() {
   while(true) {
     String str = msgQueue.getAndDeleteMsg();
		if(str != null) {
      System.out.println("got "+str+" now broadcasting ");
			for(int num = 0; num < connectionsList.size(); num++) {
			  connectionsList.get(num).sendMsg(str);
			}
		} else {
		  System.out.println("Nothing in msgQueue, sleeping now ...");
      try {
			  Thread.sleep(2000);
		  } catch (InterruptedException e) {
        System.out.println("Interrupted Exception caught");
			}
		}
	}
 }

 public void run() {
   broadcastMsgs();
 }

}

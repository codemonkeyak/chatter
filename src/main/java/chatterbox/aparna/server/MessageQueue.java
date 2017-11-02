package chatterbox.aparna.server;

import java.io.*;
import java.util.*;
import java.net.*;
import chatterbox.aparna.server.MessageQueueInterface;

public class MessageQueue implements MessageQueueInterface {

  LinkedList<String> list = new  LinkedList<String>();

  public synchronized void addToMsgQueue(final String str) {
    list.add(str);
		notify();
	}

  public synchronized String getAndDeleteFirstMsg() {
	  String firstElement = null;
		while(true) {
	    try {
        firstElement = list.removeFirst();
				return firstElement;
		  } catch (NoSuchElementException e) {
        System.err.println("No more elements in the list");
			  try {
					wait();
			  } catch (InterruptedException e1) {
          System.err.println("InterruptedException");
			  }
		  }
		}
	}


}

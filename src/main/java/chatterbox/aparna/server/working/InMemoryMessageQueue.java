package chatterbox.aparna.server;

import java.util.*;
import java.net.*;
import java.io.*;


public class InMemoryMessageQueue {

  private LinkedList<String> list = new LinkedList<String>();
  
  public void  addToList(final String str) {
      list.add(str);
	}

  public String getAndDeleteMsg() {
	  String str = "";
		try {
      str = list.removeFirst();
			return str;
		} catch (NoSuchElementException e) {
      System.err.println("no more elements left");
			return null;
		}
	}




}

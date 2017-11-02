package chatterbox.aparna.server;

import java.net.*;
import java.io.IOException;
import java.io.*;
import java.util.*;

import chatterbox.aparna.server.ChatUserConnection;
import chatterbox.aparna.server.InMemoryMessageQueue;
import chatterbox.aparna.server.MessageRouter;

public class Server {

  ServerSocket con = null;
  Socket socket = null;
	BufferedReader reader = null;
  PrintWriter write = null;
	Socket[]  soc = new Socket[3];
	ChatUserConnection chat = null;
	InMemoryMessageQueue msgQueue = null;
	MessageRouter router = null;
	int num= 0;
	int connection =0;
	LinkedList<ChatUserConnection> connectionsList = null;

  public static void main (String args[]) {
    Server server = new Server();
		String s = args[0];
		int port = server.getInteger(s);
		server.msgQueue = new InMemoryMessageQueue();
		server.connectionsList = new LinkedList<ChatUserConnection>();
    server.router = new MessageRouter(server.msgQueue, server.connectionsList);
		server.createCons(port);
	}

	public int getInteger(String s) {
    int x = 0;
		try {
      x = Integer.parseInt(s);
		} catch (NumberFormatException e) {
      System.err.println("given string cannot be converted into integer "+s);
		}
		return x;
	}

  public void createCons(int port) {
	  try {
      con = new ServerSocket(port);
			while(true) {
		    socket = con.accept();
			  chat = new ChatUserConnection(socket, msgQueue);
			  new Thread(chat).start();
			  connectionsList.add(chat);
			  num++;
			}
	  } catch(IOException e) {
      System.err.println(" Could not create a connection "+e);
	  }
	}


}

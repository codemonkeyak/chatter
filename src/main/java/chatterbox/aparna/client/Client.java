package chatterbox.aparna.client;

import java.util.*;
import java.lang.Thread;
import java.io.*;
import java.net.Socket;
import java.io.BufferedReader;
import java.net.*;

public class Client implements Runnable {
  
	public static Socket socket = null;
	public BufferedReader reader = null;
	public PrintWriter write = null;

  public static void main(String args[]) {
    Client c = new Client();
    String host = "127.0.0.1";
		int port = 2000;
		try {
		  socket = new Socket(host, port);
		} catch (UnknownHostException e) {
      System.err.println("unknown host exception");
		} catch (IOException e1) {
      System.err.println("io exception");
		}
	  new Thread(c).start();
		while(!socket.isClosed()) {
		  c.createOutBound();
		}
  }

  

  public void createOutBound() {
	  try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		  PrintWriter write = new PrintWriter(socket.getOutputStream());
		  String line = reader.readLine();
		  System.out.println("Server response = "+line);
		} catch (IOException e) {
      System.err.println("IO exception in create Outbound");
		}
  }

	public void run() {
	  try {
      write = new PrintWriter(socket.getOutputStream());
		  reader = new BufferedReader(new InputStreamReader(System.in));
      String line ="";
		  while(!line.equals("quit")) {
        line = reader.readLine();
			  write.println(line);
			  write.flush();
		  }
		  write.flush();
		  reader.close();
		  write.close();
		  socket.close();
		} catch (IOException e) {
		  System.err.println("IO exception in run");
    }
	}



}

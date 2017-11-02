package chatterbox.aparna.server;

import java.util.Calendar;
import java.net.Socket;
import java.util.Date;
import java.io.*;
import java.io.IOException;
import java.lang.Thread;

import chatterbox.aparna.server.Server;
import chatterbox.aparna.server.InMemoryMessageQueue;

public class ChatUserConnection implements Runnable { 
  
  public Socket socket = null;
	public BufferedReader reader= null;
  public PrintWriter write = null;
	public String name = null;
	private InMemoryMessageQueue msgQueue = null;


	public ChatUserConnection(Socket sock, InMemoryMessageQueue msgQ) {
   this.socket = sock;
   this.msgQueue = msgQ;
	 try {
     reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	   write = new PrintWriter(sock.getOutputStream());
	 } catch (IOException e) {
     System.err.println("error init-ing ChatUserConnection "+e);
	 }
	 new Thread(this).start();
	}

  public void sendMsg(String str) {
    write.println(str);
		write.flush();
  }

	public void run() {
    String line = "";
		Server server = new Server();
		  try {
		    while(!(line.equals("quit"))) {
			    line = reader.readLine().trim();
					System.out.println("server got = "+line);
          msgQueue.addToList(line);
				}
				write.flush();
				write.close();
				reader.close();
				socket.close();
			 } catch (IOException e) {
			   System.err.println("caught IOException "+e);
			}
	}

// client name and last message team
//store reader and writer as well
//send msg to all
// send msg to 1 client only









}

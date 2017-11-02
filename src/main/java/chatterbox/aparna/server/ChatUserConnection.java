package chatterbox.aparna.server;

import java.net.*;
import java.util.*;
import java.io.*;

import chatterbox.aparna.server.MessageQueueInterface;
import chatterbox.aparna.server.AuthorizationModule;

public class ChatUserConnection implements Runnable {

  private PrintWriter writer = null;
	private BufferedReader reader = null;
  private Socket socket = null;
	private MessageQueueInterface msgQueue = null;
  private AuthorizationModule auth = null;
  
  private ChatUserConnection(Socket sock,MessageQueueInterface msgQ) {
    this.socket = sock;
    try {
		  reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		  writer = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
      System.err.println("IOException caught");
		}
		this.msgQueue = msgQ;
	}

	public static ChatUserConnection getChatUserConnection(Socket sock,MessageQueueInterface msgQ) {
		ChatUserConnection cuc = new ChatUserConnection(sock, msgQ);
		new Thread(cuc).start();
		return cuc;
	}

  public boolean verifyCred() {
    auth = new AuthorizationModule();
		String pass = null;
		String uname = null;
	  writer.println("Enter your username");
		writer.flush();
		try {
		  uname = reader.readLine();
		} catch (IOException e) {
		  System.err.println("IOException caught");
		}
		writer.println("Enter your password");
		writer.flush();
		try {
		  pass = reader.readLine();
		} catch (IOException e) {
		  System.err.println("IOException caught");
		}
		return auth.verifyCreds(uname,pass);
  }

  public void run() {
    String str = "";
		if(verifyCred()) {
		  writer.println("You have been authenticated");
			writer.flush();
      while(str != null) {
		    if(!str.equals("quit")) {
			    try {
            str = reader.readLine();
            System.out.println(" Server got "+str);
				    msgQueue.addToMsgQueue(str);
				  } catch (IOException e) {
				    System.err.println("IOException caught");
				  }
			  } else {
            writer.flush();
					  try {
              reader.close();
							socket.close();
					  } catch (IOException e) {
					    System.err.println("IOException caught");
					  }
				    writer.close();
			   } 
		   }
     } else {
       System.err.println("credentials dont match, please try again ..");
			 try {
			   socket.close();
			 } catch (IOException e) {
			   System.err.println("IOException caught");
			 }
		 }
	 }

	public void writeToClient(final String str) {
     writer.println(" Server sent "+str);
		 writer.flush();
  }


}

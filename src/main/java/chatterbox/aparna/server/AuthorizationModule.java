package chatterbox.aparna.server;

import java.util.*;
import java.net.*;


public class AuthorizationModule {


  private String[] ucreds = {"bhanu","appu"};
	private String[] pcreds = {"a","b"};

  public boolean verifyCreds(String uname, String upass){
    for(int i=0; i < ucreds.length; i++) {
      if((ucreds[i].equals(uname)) && (pcreds[i].equals(upass))) {
        return true;
			}
		}
    return false;
	}





}

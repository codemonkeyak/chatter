package chatterbox.aparna.server;


java.io.*;
java.util.*;

public class AppuLinkedList {

  private Node firstNode = null;
	private Node node = null;

  private class Node {
		int data = null;
		Node next = null;
	}


  public void add(int num) {
	  Node nextNode = null;
    if(firstNode == null) {
      Node newNode = new Node();
			int newNode.data = num;
			Node newNode.next = null;
			firstNode = newNode ;
		} else {
      while(nextNode == null) {
        Node newNode = new Node();
				String newNode.data = str;
        Node newNode.next = null;
		    firstNode.next = newNode;
        
			}

		}

  }



}


package mychatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
   public static void main(String[] args){
       try{
       ServerSocket server= new ServerSocket(5000);
       
       Socket client = server.accept();
       
       System.out.println("Client connected.");
       
       client.close();
       server.close();
   }catch(IOException e){
       e.printStackTrace();
   }
   }
}

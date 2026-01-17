
package mychatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatServer {
   public static void main(String[] args){
       try{
       ServerSocket server= new ServerSocket(5000);
   
       Socket client = server.accept();
       
       System.out.println("Client connected.");
       
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream())); 
         
        while(true){
               String message = reader.readLine();
               if (message.equalsIgnoreCase("exit")){
                   break ;
               }
               System.out.println("Client says: "+ message);
        }
       client.close();
       server.close();
       
   }catch(IOException e){
       e.printStackTrace();
    }
   }
}

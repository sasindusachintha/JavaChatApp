
package mychatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ChatServer {
    
    private static volatile boolean running = true;
   public static void main(String[] args){
       try{
       ServerSocket server= new ServerSocket(5000);
   
       new Thread(() -> {
           Scanner sc = new Scanner(System.in);
           while(running){
               String cmd = sc.nextLine();
               if(cmd.equalsIgnoreCase("stop")){
                   System.out.println("Shutting Down Server!");
                   running = false;
                   try{ server.close();} catch(IOException e){
                       e.printStackTrace();
                   }
                   System.exit(0);
               }
           }
       }).start();
       while(true){
       Socket client = server.accept();
       
       System.out.println("Client connected.");
       ClientHandler handler = new ClientHandler(client);
       Thread t = new Thread(handler);
       t.start();
       
  
       }
       

       
   }catch(IOException e){
       e.printStackTrace();
    }
   }
}


package mychatapp;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class ClientHandler implements Runnable {
     private Socket clientSocket;
     private PrintWriter writer;
     private String username;
     
     private static List<ClientHandler> clientList = new ArrayList<>();
       
     public ClientHandler(Socket socket){
         this.clientSocket = socket;
        
     }     
     @Override
     public void run(){
         try{
         BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         writer = new PrintWriter(clientSocket.getOutputStream(), true);
         
         username = reader.readLine();
         
         clientList.add(this);
         
         String joinMsg = getTimeStamp() +" " +username + " joined the chat!";
         System.out.println( joinMsg) ;
         broadcast (joinMsg);
          
         String message;
         while((message = reader.readLine()) != null){
             if(message.equalsIgnoreCase("exit")){
                String leaveMsg = getTimeStamp() + " " + username + " left the chat!";
                System.out.println(leaveMsg);
                broadcastToAll(leaveMsg);
                break;
             }
             
             if (message.startsWith("/pm ")){
                 handlePrivateMessage(message);
                 continue;
             }
             
             if(message.trim().isEmpty()){
                 continue;
             }
             System.out.println(getTimeStamp() + " " + username +" says :" + message);
             broadcast(getTimeStamp() + " " + username + ": " + message);
         }
         reader.close();
         writer.close();
         clientList.remove(this);
         clientSocket.close();
     }catch(IOException e){
         e.printStackTrace();
     }
}
      private void broadcast(String message){
           for (ClientHandler client : clientList ){
               if(client != this){
               client.writer.println(message);
           }
           }
      }
      
      private void broadcastToAll(String message){
          for (ClientHandler client : clientList){
              client.writer.println(message);
          }
      }
      
      private String getTimeStamp(){
          LocalTime time = LocalTime.now();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
          return "[" + time.format(formatter) + "]";
      }
       
      private ClientHandler getClientByUsername(String name){
          for( ClientHandler client : clientList){
              if(client.username.equalsIgnoreCase(name)){
                  return client;
              }
          }
          return null;
      }
      
      private void handlePrivateMessage(String message){
          String[] parts = message.split(" ", 3);
          
          if (parts.length > 3){
              writer.println(" !! Usage: /pm username message");
              return;
          }
          
          String targetUsername = parts[1];
          String privateMsg = parts[2];
          
          ClientHandler targetClient = getClientByUsername(targetUsername);
          
          if (targetClient == null){
              writer.println("!! User " + targetUsername + "not found.");
              return;
          }
          
          String msg = getTimeStamp()+ " PM from " + username + ": " + privateMsg;
          targetClient.writer.println(msg);
      }
      
}

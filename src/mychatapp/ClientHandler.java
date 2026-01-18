
package mychatapp;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientHandler implements Runnable {
     private Socket clientSocket;
     private PrintWriter writer;
     
     private static List<ClientHandler> clientList = new ArrayList<>();
       
     public ClientHandler(Socket socket){
         this.clientSocket = socket;
        
     }     
     
   
     

     @Override
     public void run(){
         try{
         BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         writer = new PrintWriter(clientSocket.getOutputStream(), true);
         
          clientList.add(this);
          
         String message;
         while((message = reader.readLine()) != null){
             if(message.equalsIgnoreCase("exit")){
                 break;
             }
             System.out.println("Client says: "+ message);
             broadcast(message) ;
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
}

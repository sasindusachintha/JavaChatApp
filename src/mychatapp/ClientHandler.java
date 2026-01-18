
package mychatapp;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class ClientHandler implements Runnable {
     private Socket clientSocket;
     
     public ClientHandler(Socket socket){
         this.clientSocket = socket;
     }
     
     @Override
     public void run(){
         try{
         BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
         
         String message;
         while((message = reader.readLine()) != null){
             if(message.equalsIgnoreCase("exit")){
                 break;
             }
             System.out.println("Client says: "+ message);
         }
         reader.close();
         writer.close();
         clientSocket.close();
     }catch(IOException e){
         e.printStackTrace();
     }
}
}

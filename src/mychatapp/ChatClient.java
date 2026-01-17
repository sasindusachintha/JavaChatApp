
package mychatapp;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
        
public class ChatClient {
    public static void main(String[] args){
        try{
        Socket socket = new Socket("localhost", 5000);
       
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println(" Hello Server");
        socket.close();
        
        
    }catch(IOException e){
        e.printStackTrace();
    }
}
}

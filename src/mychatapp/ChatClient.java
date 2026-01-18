
package mychatapp;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
        
public class ChatClient {
    public static void main(String[] args){
        try{
        Socket socket = new Socket("localhost", 5000);
       
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader( new InputStreamReader(System.in));
        BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            Thread readerThread = new Thread(() ->{
                try{
                    String msg;
                while((msg = serverReader.readLine()) != null){
                System.out.println(msg);
            }
                }catch(IOException e){
                    e.printStackTrace();
                }
            });
            readerThread.start();
        String input;
        while(true){
            input = console.readLine();
            writer.println(input);
            if(input.equalsIgnoreCase("exit")){
                break;
            }
        }
        socket.close();
        
        
    }catch(IOException e){
        e.printStackTrace();
    }
}
}

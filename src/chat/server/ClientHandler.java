package chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private static final String HOST = "localhost";
    private static final int PORT = 3443;
    private Socket clientSocket;
    private static int clientsCount = 0;

    public ClientHandler(Socket clientSocket, Server server){
        try{
            clientsCount++;
            this.clientSocket = clientSocket;
            this.server = server;
            this.outMessage = new PrintWriter(clientSocket.getOutputStream());
            this.inMessage = new Scanner(clientSocket.getInputStream());
        }catch (IOException exc){
            exc.printStackTrace();
        }
    }
    @Override
    public void run() {
        server.sendMessageToAllClients("New client has entered the chat");
        server.sendMessageToAllClients("Number of chat participants = " + clientsCount);
        try{
            while(true){
                if (inMessage.hasNext()){
                    String clientMessage = inMessage.nextLine();
                    if(clientMessage.equalsIgnoreCase("##session##end##"))
                        break;
                    //System.out.println(clientMessage);
                    server.sendMessageToAllClients(clientMessage);
                }
                Thread.sleep(1000);
            }
        }catch (InterruptedException exc){
            exc.printStackTrace();
        }finally {
            this.close();
        }
    }

    public void sendMsg(String msg){
        try{
            outMessage.println(msg);
            outMessage.flush();
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public void close(){
        server.removeClient(this);
        clientsCount--;
        server.sendMessageToAllClients("Number of chat participants = " + clientsCount);
    }
}

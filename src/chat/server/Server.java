package chat.server;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Server {
    private ConnectionDB cdb;
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private static final int PORT = 3443;
    private List<ClientHandler> clients = new LinkedList<ClientHandler>();

    public Server(){
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("The server is running");
            while (true){
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        }catch (IOException exc){
            exc.printStackTrace();
        }finally {
            try{
                clientSocket.close();
                System.out.println("The server is stoped");
                serverSocket.close();
            }catch (IOException exc){
                exc.printStackTrace();
            }
        }
    }

    public void sendMessageToAllClients(String msg){
        for (ClientHandler client : clients)
            client.sendMsg(msg);
    }

    public void removeClient(ClientHandler client){
        clients.remove(client);
    }
}

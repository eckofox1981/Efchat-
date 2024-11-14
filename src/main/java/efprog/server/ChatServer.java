package efprog.server;

import efprog.client.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    public static List<ClientThread> clientThreadList = new ArrayList<>();

    public static void main (String[] args) {
        int portNumber = 4444;
        ServerSocket serverSocket;
        //ServerSocket listens on assigned portnumber
        try {
            serverSocket = new ServerSocket(portNumber);
            acceptsClients(serverSocket);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + portNumber + "\nApplication shutting down.");
            System.exit(1);
        }
    }

    private static void acceptsClients(ServerSocket serverSocket){
        while (true){
            try{
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket);
                Thread thread = new Thread(client);
                thread.start();
                clientThreadList.add(client);
            } catch (IOException e) {
                System.err.println("acceptsClient failed: " + e.getMessage());
            }
        }
    }
}

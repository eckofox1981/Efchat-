package efprog.server;

import efprog.client.ClientManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;  //listens  for incoming connections or client
                                        //creates a socket for communications


    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start(){
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();  //accept() => blocks the program until a client connects, when
                // connect socket will get its value
                System.out.println("New client connected.");
                ClientManager clientManager = new ClientManager(socket);

                Thread thread = new Thread(clientManager); //new thread implement the clientManager
                thread.start();
            }

        } catch (IOException e) { //TODO: it got messy, redo when finished
            System.err.println("serverSocket.accept error: " + e.getMessage());
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException ea) {
                System.err.println("serverSocket could not close upon error" + e.getMessage());
                System.err.println("serversocket not close printstck");
                ea.printStackTrace();
            }
            System.err.println("serverSocket.accpt prinstack:");
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        int socketPort = 4444;
        try {
            ServerSocket serverSocket = new ServerSocket(socketPort, 50, InetAddress.getByName("0.0.0.0")); //4444 randomly picked, lower numbers may already be used by system
            Server chatServer = new Server(serverSocket);       /** the new server will listen on that socket*/
            chatServer.start();


        } catch (IOException e) {
            System.err.println("serSocket in ServerMain err: " + e.getMessage());
        }
    }
}

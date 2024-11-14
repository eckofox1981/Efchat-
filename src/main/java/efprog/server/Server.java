package efprog.server;

import efprog.client.ClientManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

                Thread thread = new Thread(clientManager); //new thread implement the clientManagaer
                thread.start();

            }
        } catch (IOException e) {
            System.err.println("serverSocket.accept error: " + e.getMessage());
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException ea){
                System.err.println("serverSocket could not close upon error" + e.getMessage());
                System.err.println("serversocket not close printstck");
                ea.printStackTrace();
            }
            System.err.println("serverSocket.accpt prinstack:");
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4444);

        } catch (IOException e) {
            System.err.println("serSocket in ServerMain err" + e.getMessage() + "\n serSock in main prinstack");
        }

    }
}

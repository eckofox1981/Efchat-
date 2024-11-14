package efprog.client;

import efprog.server.ChatServer;

import java.io.*;
import java.net.Socket;

public class ClientManager extends ChatServer implements Runnable {
    /**
     * each object opf ClientManager will be responsible for communicating with a client.
     * ClientManageris also responsible for communication with  a client.
     * Runnable since we need it to run on separate thread for each client (otherwise it could only do one at a time)
     */
    private Socket socket;
    private BufferedReader msgIn;
    private PrintWriter msgOut;
    private ChatServer chatServer;

    public ClientManager(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            msgOut = new PrintWriter(socket.getOutputStream(), true);
            msgIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (!socket.isClosed()){
                String input = msgIn.readLine();
                if (input != null){
                    for (ClientManager client : chatServer.clientThreadList) {
                        client.getWriter().write(input);
                    }
                }
            }

        } catch (IOException e){
            System.err.println("msgOut/msgIN error" + e.getMessage());
        }
    }

    public PrintWriter getWriter(){
        return msgOut;
    }
}

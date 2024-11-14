package efprog.client;

import efprog.server.ChatServer;

import java.io.*;
import java.net.Socket;

public class ClientThread extends ChatServer implements Runnable {
    private Socket socket;
    private BufferedReader msgIn;
    private PrintWriter msgOut;
    private ChatServer chatServer;

    public ClientThread(Socket socket) {
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
                    for (ClientThread client : chatServer.clientThreadList) {
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

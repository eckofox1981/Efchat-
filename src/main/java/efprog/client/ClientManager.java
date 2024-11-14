package efprog.client;

import efprog.server.ChatServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientManager implements Runnable {
    /** each object opf ClientManager will be responsible for communicating with a client.
     * ClientManageris also responsible for communication with  a client.
     * Runnable since we need it to run on separate thread for each client (otherwise it could only do one at a time)
     */
    public static List<ClientManager> clients = new ArrayList<>(); //lists all clients

    private Socket socket;
    private BufferedReader msgIn;
    private PrintWriter msgOut;
    /**
     *java has two types of streams: byteStream (data) and characterStream (characters). We obviously want the latter
     * to send messages.
     * when using reader/writer we can tell the difference:
     * OutputStream() for data and OutputStreamWriter() for chars.
     * */



    private String userName;



    public ClientManager(Socket socket) {
        try {
            this.socket = socket; //maybe add a try/catch
            this.msgOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.msgIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userName = msgIn.readLine();
            clients.add(this);
        } catch (IOException e) {
            System.err.println("msgOut/In err at constructor: " + e.getMessage() + "\n msgOut/In err prinstack:");
            e.printStackTrace();
        }
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

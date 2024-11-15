package efprog.client;


import java.io.*;
import java.net.Socket;
import java.util.function.Predicate;

public class Client {
    private Socket socket;
    private BufferedReader msgIn;
    private PrintWriter msgOut;
    private String userName;

    public Client(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
        try {
            this.msgIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.msgOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("Error @ Client constructor for msgIn/Out " + e.getMessage() + " printstack:");
            e.printStackTrace();
        }
    }
}

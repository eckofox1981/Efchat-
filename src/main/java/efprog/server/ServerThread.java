package efprog.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Socket socket;
    private String userName; //TODO initialyze somewhere...
    private BufferedReader serverIn;
    private BufferedReader userIn;
    private PrintWriter serverOut;

    public ServerThread(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
    }

    @Override
    public void run() {
        try {
            serverOut = new PrintWriter(socket.getOutputStream());
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            userIn = new BufferedReader(new InputStreamReader(System.in));

            while (!socket.isClosed()) {
                if (serverIn.ready()) {
                    String input = serverIn.readLine();
                    if (input != null) {
                        System.out.println(input);
                    }
                }
                if (userIn.ready()) {
                    serverOut.println(userName + ":> " + userIn.readLine());
                }
            }

        } catch (IOException e) {
            System.err.println("ServerThread run error: " + e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }
}

package efprog.client;

import efprog.server.ServerThread;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private int portNumber = 4444;
    private ServerThread serverThread;

    private String userName;

    public Client(String userName) {
        this.userName = userName;
    }

    public static void main (String[] args){
        System.out.print("Enter your username:");
        Scanner scanner = new Scanner(System.in);
        String userNameForClientConstructor = scanner.nextLine();


        ServerThread serverThread1;
        try {
            serverThread1 = new ServerThread(new Socket("localhost", 4444), userNameForClientConstructor);
        } catch (IOException e) {
            System.err.println("construction serverThread1: " + e.getMessage());
            e.printStackTrace();
        }
        Client client = new Client(userNameForClientConstructor);
        client.start();

    }


    private void start() {
        try {
            socket = serverThread.getSocket();
            Thread.sleep(1000);
            Thread server = new Thread(new ServerThread(socket, userName));
            server.start();
        } catch (InterruptedException ea) {
            System.out.println("Thread sleep in Client main interrupted." + ea.getMessage());
        }
    }


}

/* Create a socket in the main method of Login, specifying the host address and port number.
Since this is a login window, the socket can be an instance variable as it will be passed to
the actual chat window later.

TIP: "localhost" is used in the socket parameters for debugging purposes to specify that
the server is running on the same computer as the client. Later this can be changed to take
a host address to connect to a remote server.*/

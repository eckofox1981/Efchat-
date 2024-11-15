package efprog.client;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader msgIn;
    private BufferedWriter msgOut;
    private String userName;

    public Client(Socket socketIn, String userName) {
        this.socket = socketIn;
        this.userName = userName;
        try {
            this.msgIn = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
            this.msgOut = new BufferedWriter(new OutputStreamWriter(socketIn.getOutputStream()));
        } catch (IOException e) {
            System.err.println("Error @ Client constructor for msgIn/Out " + e.getMessage() + " printstack:");
            e.printStackTrace();
        }
    }

    public void sendMsgOut() {
        try {
            msgOut.write(userName);
            msgOut.newLine();
            msgOut.flush();

        Scanner scanner = new Scanner(System.in);

            while (!socket.isClosed()){
                String msgToSend = scanner.nextLine();
                msgOut.write(userName + ":> " + msgToSend);
                msgOut.newLine();
                msgOut.flush();
            }

        } catch (IOException e) {
            System.err.println("Error @ sendMsgOut(): msgOut.write/newLine/flsuh " + e.getMessage() + " printstack:");
            e.printStackTrace();
        }
    }

    public void l

}

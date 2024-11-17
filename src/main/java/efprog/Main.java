package efprog;

import efprog.client.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type the IP you want to connect to: ");
        String ipToConnect = scanner.nextLine();
        System.out.println("Enter your username:" + "\033[0;34m");
        String userName = scanner.nextLine();
        Socket socket;
        Client client;
        try {
            socket = new Socket(ipToConnect, 4444);
            client = new Client(socket, userName);
            client.listenForMsgIn();    //separate thead on whileloop
            client.sendMsgOut();
        } catch (IOException e){
            System.err.println("Didn't even get past main...tttt" + e.getMessage() + "stacktrace");
            e.printStackTrace();
        }

               //separate thead on whileloop
    }
}
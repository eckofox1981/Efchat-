package efprog.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientManager implements Runnable {
    /**
     * each object of ClientManager will be responsible for communicating with a client.
     * ClientManager is also responsible for communication with  a client.
     * Runnable since we need it to run on separate threads for each client (otherwise it could only do one at a time)
     *
     */
    public static List<ClientManager> clients = new ArrayList<>(); //lists all clients to the group chat

    private Socket socket;
    private BufferedReader msgIn;
    private BufferedWriter msgOut; //I had multiple issues trying with PrintWriter, BufferedWriter I found "easier" to use but causes IOExceptions (lotsa try-catch)
    /**
     * java has two types of streams: byteStream (data) and characterStream (characters). We obviously want the latter
     * to send messages.
     * when using reader/writer we can tell the difference:
     * OutputStream() for data and OutputStreamWriter() for chars.
     */

    private String userName;


    public ClientManager(Socket socket) {
        try {
            this.socket = socket; //maybe add a try/catch
            this.msgOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); //this stream to send from (on this socket)
            this.msgIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); //this stream to read (on this socket)
            this.userName = msgIn.readLine(); //using the reader which will read from the client
            clients.add(this); //added to group chat
            broadCastMsg("\033[0;34m"  + "\u001B[42m" + "SERVER: " + userName + " has entered the chat!" + "\033[0;34m" ); //first code is coloring the text green, last to get it white
        } catch (IOException e) {
            System.err.println("msgOut/In err at constructor: " + e.getMessage() + "\n msgOut/In err prinstack:");
            e.printStackTrace();
            closingEverything(socket, msgIn, msgOut);
        }
    }

    @Override
    public void run() { //overriden since implemented by runnable
        /**this method will be run on a separate thread since it will listen for new messages on the server
         *          => if not run on a separate thread, the program would wait to continue until it finished
         *              its task: receiving a message and all operations would come to a halt.
         *              This is called a blocking operation (another example in Server start() method*/
        try {

            while (!socket.isClosed()) {
                String incomingMsg = msgIn.readLine();  //this would be the blocking code if not run on a separate thread,
                                                        //it would wait until it'd have something to read
                broadCastMsg(incomingMsg);
            }
        } catch (IOException e) {
            System.err.println("msgOut/msgIN error" + e.getMessage());
            closingEverything(socket, msgIn, msgOut);
        }
    }

    public void broadCastMsg(String message) {
        for (ClientManager client : clients) {
            try {
                if (!client.userName.equals(userName)) {
                    client.msgOut.write(message);
                    client.msgOut.newLine();
                    client.msgOut.flush();
                }
            } catch (IOException e) {
                System.err.println("Client disconnected or Error at msgOut.write/flush @ broadcastMsg " + e.getMessage());
                closingEverything(socket, msgIn, msgOut);
            }
        }
    }

    public void removeClientManager(){
        /** will remove the client from the clientManager list and print it on the display*/
        clients.remove(this);
        System.out.println("\u001B[42m" + "SERVER: " + userName + " has left the chat. + \033[0;34m");
    }

    public void closingEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientManager();
        if (bufferedReader != null){
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.err.println("Could not close msgIn @ closeEveryting " + e.getMessage() + " printstack:");
                e.printStackTrace();
            }
        }

        if (bufferedWriter != null){
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                System.err.println("Could not close msgOut @ closes everything " + e.getMessage() + " printstack:");
                e.printStackTrace();
            }
        }

        if (socket != null){
            try {
                socket.close();
            }catch (IOException e){
                System.err.println("Could not close socket @ closeEverything " + e.getMessage() + " printstack:");
                e.printStackTrace();
            }
        }
    }
}

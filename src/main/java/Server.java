import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {
    public static void main(String[] args) {

        ServerSocket ss;
        try {
            ss = new ServerSocket(7777);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("ServerSocket awaiting connections...");

        Socket socket;
        try {
            socket = ss.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        manageClient(socket);

        System.out.println("Closing sockets.");
        try {
            ss.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static void manageClient( Socket socket ){
        System.out.println("Connection from " + socket + "!");

        // get the input stream from the connected socket
        InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // read the list of messages from the socket
        List<Message> listOfMessages;
        try {
            listOfMessages = (List<Message>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Received [" + listOfMessages.size() + "] messages from: " + socket);
        // print out the text of every message
        System.out.println("All messages:");
        listOfMessages.forEach((msg)-> System.out.println(msg.toString()));

    }
}
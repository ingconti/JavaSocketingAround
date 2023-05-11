import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {
    public static void main(String[] args) {
        // don't need to specify a hostname, it will be the current machine
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(7777);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("ServerSocket awaiting connections...");

        Socket socket = null;
        try {
            socket = ss.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connection from " + socket + "!");

        // get the input stream from the connected socket
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // create a DataInputStream so we can read data from it.
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // read the list of messages from the socket
        List<Message> listOfMessages = null;
        try {
            listOfMessages = (List<Message>) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Received [" + listOfMessages.size() + "] messages from: " + socket);
        // print out the text of every message
        System.out.println("All messages:");
        listOfMessages.forEach((msg)-> System.out.println(msg.toString()));

        System.out.println("Closing sockets.");
        try {
            ss.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
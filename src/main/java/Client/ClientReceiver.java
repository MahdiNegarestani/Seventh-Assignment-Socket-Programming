package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiver implements Runnable {
    // TODO: Declare a variable to hold the input stream from the socket
    private final BufferedReader in;

    public ClientReceiver(BufferedReader in) {
        // TODO: Modify this constructor to receive either a Socket or an InputStream as a parameter
        // TODO: Initialize the input stream variable using the received parameter
        this.in = in;
    }

    public ClientReceiver(Socket socket)throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                //TODO: Listen for new messages from server
                //TODO: print the  new message in CLI
                System.out.println("Server: " + message);
            }
        } catch (Exception e) {
            System.out.println("Error receiving message: " + e.getMessage());

        } finally {
            try {
                in.close();
            } catch (IOException e) {
                System.out.println("Error closing input stream: " + e.getMessage());
            }
        }
    }

}

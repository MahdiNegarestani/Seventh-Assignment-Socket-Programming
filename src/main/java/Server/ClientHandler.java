package Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket socket;
    // TODO: Declare a variable to hold the input stream from the socket
    // TODO: Declare a variable to hold the output stream from the socket
    private BufferedReader in;
    private PrintWriter out;
    private List<ClientHandler> allClients;
    private String username;

    public ClientHandler(Socket socket, List<ClientHandler> allClients) {
        // TODO: Modify the constructor as needed
        this.socket = socket;
        this.allClients = allClients;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Error initializing streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // TODO: Read incoming message from the input stream
                // TODO: Process the message
                String message = in.readLine();
                if (message == null) {
                    break;
                }
                processMessage(message);
            }
        } catch (Exception e) {
            System.out.println("Error in clientHandler: " + e.getMessage());

        } finally {
            try {
                allClients.remove(this);
                socket.close();
            }catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
            //TODO: Update the clients list in Server
        }
    }

    private void processMessage(String message) throws IOException, ClassNotFoundException {
        if (message.startsWith("LOGIN:")) {
            String[] parts = message.split(":");
            String username = parts[1];
            String password = parts[2];
            handleLogin(username, password);
        } else if (message.startsWith("CHAT:")) {
            String chatMessage = message.substring(5);
            broadcast(chatMessage);
        } else if (message.startsWith("UPLOAD:")) {
            String FileName = message.split(":")[1];
            receiveFile(FileName, Integer.parseInt(message.split(":")[2]));
        }
    }

    private void sendMessage(String msg){
        //TODO: send the message (chat) to the client
        out.println(msg);
    }
    private void broadcast(String msg) throws IOException {
        //TODO: send the message to every other user currently in the chat room
        for (ClientHandler client : allClients) {
            if (client != this) {
                client.sendMessage(msg);
            }
        }
    }

    private void sendFileList(){
        // TODO: List all files in the server directory
        // TODO: Send a message containing file names as a comma-separated string
        File folder = new File("resources/Server/"); // Adjust the path as needed
        File[] files = folder.listFiles();
        if (files != null) {
            StringBuilder fileList = new StringBuilder();
            for (File file : files) {
                fileList.append(file.getName()).append(",");
            }
            sendMessage("FILE_LIST:" + fileList.toString());
        }
    }
    private void sendFile(String fileName){
        // TODO: Send file name and size to client
        // TODO: Send file content as raw bytes
        File file = new File("resources/Server/" + fileName); // Adjust the path as needed
        if (file.exists()) {
            try {
                out.println("FILE:" + file.getName() + ":" + file.length());
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    out.write(new String(buffer, 0, bytesRead));
                    out.flush();
                }
                fileInputStream.close();
            } catch (IOException e) {
                System.out.println("Error sending file: " + e.getMessage());
            }
        } else {
            sendMessage("ERROR: File not found.");
        }
    }
    private void receiveFile(String filename, int fileLength)
    {
        // TODO: Receive uploaded file content and store it in a byte array
        // TODO: after the upload is done, save it using saveUploadedFile
        try {
            byte[] data = new byte[fileLength];
            InputStream inputStream = socket.getInputStream();
            int bytesRead = 0;
            while (bytesRead < fileLength) {
                int result = inputStream.read(data, bytesRead, fileLength - bytesRead);
                if (result == -1) break; // End of stream
                bytesRead += result;
            }
            saveUploadedFile(filename, data);
        } catch (IOException e) {
            System.out.println("Error receiving file: " + e.getMessage());
        }
    }
    private void saveUploadedFile(String filename, byte[] data) throws IOException {
        // TODO: Save the byte array to a file in the Server's resources folder
        File file = new File("resources/Server/" + filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
    }

    private void handleLogin(String username, String password) throws IOException, ClassNotFoundException {
        // TODO: Call Server.authenticate(username, password) to check credentials
        // TODO: Send success or failure response to the client
        if (Server.authenticate(username, password)) {
            this.username = username;
            sendMessage("Authentication successful!");
            broadcast(username + "has joined the chat");
        } else {
            sendMessage("Authentication failed!");
        }
    }

}

package Shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
    /**
     * Note: This class is used to represent structured data exchanged between the client and server
     *       over sockets — such as requests, responses, or commands.
     *
     * This is NOT just a chat message class.
     * It may be used to carry login information, commands, filenames, or other types of data,
     * depending on the message type.
     *
     * You may use Java Serialization or JSON (e.g., via libraries like Gson or Jackson) to send this object.
     *
     * You are allowed — and sometimes expected — to modify or extend this class to suit your needs.
     */
    public int type; // e.g., 0 = login, 1 = chat message, 2 = file upload, etc.
    public String sender;
    public String content;    // chat text, filename, password, etc.

    public Message() {}

    public Message(int type, String sender, String content) {
        this.type = type;
        this.sender = sender;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender(String sender) {
        return this.sender = sender;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String tojson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static Message fromJson(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, Message.class);
    }
}
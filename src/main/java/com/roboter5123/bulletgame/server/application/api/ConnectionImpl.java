package com.roboter5123.bulletgame.server.application.api;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionImpl extends Thread implements Connection {

    private PrintWriter out;
    private BufferedReader in;
    private final List<String> messages;
    private boolean ready;

    public ConnectionImpl(Socket socket) {
        this.messages = new ArrayList<>();
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ready = true;
        this.start();
    }

    @Override
    public void run() {
        while (ready) {
            main();
        }
    }

    private void main() {
        try {
            messages.add(in.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public List<String> readMessages() {
        List<String> messagesCopy = new ArrayList<>(this.messages);
        messagesCopy.clear();
        return messagesCopy;
    }

    @Override
    public void closeConnection() {
        this.ready = false;
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

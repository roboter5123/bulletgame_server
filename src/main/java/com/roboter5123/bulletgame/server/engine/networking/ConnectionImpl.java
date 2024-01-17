package com.roboter5123.bulletgame.server.engine.networking;
import com.roboter5123.bulletgame.server.engine.AbstractGameState;
import com.roboter5123.bulletgame.server.engine.exception.SocketException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionImpl extends Thread implements Connection {

    private final PrintWriter out;
    private final BufferedReader in;
    private final List<String> messages;
    private boolean ready;
    private Gson gson;


    public ConnectionImpl(Socket socket) {
        this.messages = new ArrayList<>();
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new SocketException();
        }
        gson = new Gson();
        ready = true;
        this.start();
    }

    @Override
    public void run() {
        while (ready) {
            listenForMessage();
        }
    }

    private void listenForMessage() {
        try {
            String msg = in.readLine();
            if (msg != null) {
                messages.add(msg);
            } else {
                closeConnection();
            }
        } catch (IOException e) {
            throw new SocketException();
        }
    }
    
    @Override
    public void sendMessage(AbstractGameState gameState) {
        String gameStateJson = this.gson.toJson(gameState);
        out.println(gameStateJson);
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
            throw new SocketException();
        }
    }

    public boolean isDisconnected() {
        return !ready;
    }
}

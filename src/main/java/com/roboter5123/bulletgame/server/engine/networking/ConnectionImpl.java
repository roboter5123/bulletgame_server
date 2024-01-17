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

    private PrintWriter out;
    private BufferedReader in;
    private List<Message> messages;
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
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            listenForMessage();
        }
    }

    private void listenForMessage() {
        try {
            String msg = in.readLine();
            if (msg != null) {
                messages.add(gson.fromJson(msg, Message.class));
            } else {
                closeConnection();
            }
        } catch (IOException e) {
            this.interrupt();
        }
    }

    @Override
    public void sendMessage(AbstractGameState gameState) {
        String gameStateJson = this.gson.toJson(gameState);
        out.println(gameStateJson);
    }

    @Override
    public List<Message> readMessages() {
        List<Message> messagesCopy = new ArrayList<>(this.messages);
        messagesCopy.clear();
        return messagesCopy;
    }

    @Override
    public void closeConnection() {
        this.interrupt();
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            log.warning("Error closing socket connection");
        }finally {
            in = null;
            out = null;
        }
        this.messages = null;
        this.gson = null;
    }

    public boolean isDisconnected() {
        return this.isInterrupted();
    }
}

package com.roboter5123.bulletgame.server;

import com.roboter5123.bulletgame.server.engine.AbstractApplication;
import com.roboter5123.bulletgame.server.engine.networking.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Application extends AbstractApplication {

    private static final Logger log = Logger.getLogger(Application.class.getCanonicalName());
    private boolean running;
    List<String> chatLog = new ArrayList<>();

    @Override
    protected void applicationLoop() {
        while (this.running) {
            try {
                Thread.sleep(1000);
                BroadcastGameState();
            } catch (InterruptedException e) {
                this.running = false;
            }
        }
    }

    private void BroadcastGameState() {
        for (Connection sender : connections) {
            List<String> messages = sender.readMessages();
            chatLog.addAll(messages);
            broadcastMessages(messages);
        }
    }

    private void broadcastMessages(List<String> messages) {
        for (Connection receiver : connections) {
            messages.forEach(receiver::sendMessage);
        }
    }
}

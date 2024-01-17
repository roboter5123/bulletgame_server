package com.roboter5123.bulletgame.server;

import com.roboter5123.bulletgame.server.engine.AbstractApplication;
import com.roboter5123.bulletgame.server.engine.AbstractGameState;
import com.roboter5123.bulletgame.server.engine.networking.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Application extends AbstractApplication {

    private static final Logger log = Logger.getLogger(Application.class.getCanonicalName());
    private boolean running;
    List<String> chatLog = new ArrayList<>();
    private GameState gameState = new GameState();

    @Override
    protected void applicationLoop() {
        while (this.running) {
            try {
                Thread.sleep(1000);
                calculateGameState();
                broadcastGameState();
            } catch (InterruptedException e) {
                this.running = false;
            }
        }
    }

    private void calculateGameState() {
        this.gameState = new GameState();
        List<String> messages = new ArrayList<>();
        for (Connection connection : connections) {
            messages.addAll(connection.readMessages());
        }
        gameState.setMessages(messages);
    }

    private void broadcastGameState() {
        for (Connection receiver : connections) {
            receiver.sendMessage(this.gameState);
        }
    }
}

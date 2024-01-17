package com.roboter5123.bulletgame.server;

import com.roboter5123.bulletgame.server.engine.AbstractApplication;
import com.roboter5123.bulletgame.server.engine.AbstractGameState;
import com.roboter5123.bulletgame.server.engine.networking.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Application extends AbstractApplication {
    private boolean running;
    private GameState gameState;

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
        if (gameState.getMessages() == null || !gameState.getMessages().isEmpty()){
            connections.parallelStream().forEach(receiver -> receiver.sendMessage(this.gameState));
        }
    }
}

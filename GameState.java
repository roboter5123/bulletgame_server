package com.roboter5123.bulletgame.server;

import com.roboter5123.bulletgame.server.engine.AbstractGameState;

import java.util.ArrayList;
import java.util.List;

public class GameState extends AbstractGameState {

    private List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}

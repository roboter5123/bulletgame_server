package com.roboter5123.bulletgame.server.engine.networking;
import com.roboter5123.bulletgame.server.engine.AbstractGameState;

import java.util.List;

public interface Connection {
    void sendMessage(AbstractGameState gameState);

    public List<Message> readMessages();

    void closeConnection();

    boolean isDisconnected();
}

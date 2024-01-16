package com.roboter5123.bulletgame.server.application.api;
import java.util.List;

public interface Connection {

    void sendMessage(String message);

    List<String> readMessages();

    void closeConnection();
}

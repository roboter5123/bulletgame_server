package com.roboter5123.bulletgame.server.application.api;
import java.util.List;

public interface ConnectionManager {

    void setConnections(List<Connection> connections);
    List<Connection> getConnections();
    void start();

    void ready(boolean ready);
}

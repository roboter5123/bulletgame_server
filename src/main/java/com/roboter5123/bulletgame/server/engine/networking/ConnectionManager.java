package com.roboter5123.bulletgame.server.engine.networking;
import java.util.List;

public interface ConnectionManager {
    List<Connection> getConnections();
    void start();
    void interrupt();
}

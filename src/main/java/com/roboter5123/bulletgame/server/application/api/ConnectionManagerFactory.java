package com.roboter5123.bulletgame.server.application.api;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManagerFactory {

    private static ConnectionManager connectionManager;

    private ConnectionManagerFactory() {
    }

    public static ConnectionManager getConnectionManager(List<Connection> connections) {
        if (connectionManager == null) {
            connectionManager = new ConnectionManagerImpl(connections);
        }
        return connectionManager;
    }

    public static ConnectionManager getConnectionManager() {
        if (connectionManager == null) {
            connectionManager = new ConnectionManagerImpl(new ArrayList<>());
        }
        return connectionManager;
    }
}

package com.roboter5123.bulletgame.server.application.api;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class ConnectionManagerImpl extends Thread implements ConnectionManager{

    private List<Connection> connections;
    private ServerSocket serverSocket;

    public ConnectionManagerImpl(List<Connection> connections) {
        this.connections = connections;
    }

    @Override
    public void run() {

        acceptConnections();
    }

    private void acceptConnections() {
        while (true) {
            try {
                this.serverSocket = new ServerSocket(6666);
                Connection connection = new ConnectionImpl(serverSocket.accept());
                this.connections.add(connection);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Connection> getConnections() {

        return connections;
    }

    public void setConnections(List<Connection> connections) {

        this.connections = connections;
    }
}

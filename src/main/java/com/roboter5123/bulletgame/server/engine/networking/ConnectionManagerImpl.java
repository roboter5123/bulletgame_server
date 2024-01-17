package com.roboter5123.bulletgame.server.engine.networking;

import com.roboter5123.bulletgame.server.engine.exception.SocketException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ConnectionManagerImpl extends Thread implements ConnectionManager {

    private List<Connection> connections;
    private ServerSocket serverSocket;
    private static final Logger log = Logger.getLogger(ConnectionManager.class.getCanonicalName());

    public ConnectionManagerImpl(List<Connection> connections) {
        this.connections = connections;
        startServerSocket();
    }

    @Override
    public void run() {
        log.info(() -> "Listening for connections on port: " + serverSocket.getLocalPort());
        // TODO: Make unused connection detection less like complete shit.
        new Thread(this::connectionWatchdog).start();
        while (!this.isInterrupted()) {
            acceptConnection();
        }
        shutdown();
    }

    private void connectionWatchdog() {
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            shutdown();
        }
        while (!this.isInterrupted()) {
            clearUnusedConnections();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                shutdown();
            }
        }
    }

    private void clearUnusedConnections() {
        int connectionCount = connections.size();
        log.info(() -> "Starting clearing of unused connections. Connections:[" + connectionCount + "]");
        connections.removeIf(Connection::isDisconnected);
        log.info(() -> "Finished clearing unused connections. Removed connection Count:" + (connectionCount - connections.size()));
    }

    private void startServerSocket() {
        int port = 6666;
        log.info(() -> "Attempting to start Serversocket on port: " + port);
        try {
            this.serverSocket = new ServerSocket(port);
            log.info(() -> "Successfully started Serversocket on port: " + port);
        } catch (IOException e) {
            log.severe("Starting Serversocket on port: " + port + " failed");
            this.interrupt();
        }
    }

    private void shutdown() {
        log.info("Attempting to disconnect all clients");
        for (Connection connection : connections) {
            connection.closeConnection();
        }
        connections.clear();
        connections = null;
        log.info("Disconnected all clients");
        log.info("Shutting down server-socket");
        try {
            serverSocket.close();
        } catch (IOException e) {
            log.severe("An error occurred while shutting down down server-socket");
        } finally {
            serverSocket = null;
        }
        log.info("Serversocket shutdown");
    }

    private void acceptConnection() {
        try {
            Socket clientSocket = serverSocket.accept();
            log.info(() -> "Client connected. IpAdress: " + clientSocket.getInetAddress());
            Connection connection = new ConnectionImpl(clientSocket);
            this.connections.add(connection);
        } catch (IOException e) {
            log.info(() -> "An error happened when a client tried to connect. error: " + e.getMessage());
            throw new SocketException();
        }
    }

    public List<Connection> getConnections() {
        return connections;
    }
}

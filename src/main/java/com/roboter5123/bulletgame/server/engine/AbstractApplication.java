package com.roboter5123.bulletgame.server.engine;

import com.roboter5123.bulletgame.server.engine.networking.Connection;
import com.roboter5123.bulletgame.server.engine.networking.ConnectionManager;
import com.roboter5123.bulletgame.server.engine.networking.ConnectionManagerFactory;

import java.util.logging.Logger;

public abstract class AbstractApplication extends Thread {

    private ConnectionManager connectionManager;
    protected List<Connection> connections;
    private static final Logger log = Logger.getLogger(AbstractApplication.class.getCanonicalName());

    @Override
    public void run() {
        initialize();
        gameLoop();
    }

    private void initialize() {
        log.info("Initializing Application");
        this.connectionManager = ConnectionManagerFactory.getConnectionManager();
        this.connectionManager.start();
        this.connections = connectionManager.getConnections();
        log.info("Finished initializing Application");
    }

    abstract protected void gameLoop();

}

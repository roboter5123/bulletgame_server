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
        applicationLoop();
        shutdown();
    }

    private void initialize() {
        log.info("Initializing Application");
        this.connectionManager = ConnectionManagerFactory.getConnectionManager();
        this.connectionManager.start();
        this.connections = connectionManager.getConnections();
        log.info("Finished initializing Application");
    }

    protected void shutdown() {
        log.info("Shuting down Application");
        this.connectionManager.interrupt();
        this.connectionManager = null;
        this.connections = null;
        log.info("Finished Shutting Down Application");
    }

    abstract protected void applicationLoop();

}

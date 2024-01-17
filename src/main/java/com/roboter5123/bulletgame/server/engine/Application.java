package com.roboter5123.bulletgame.server.engine;
import com.roboter5123.bulletgame.server.engine.networking.ConnectionManager;
import com.roboter5123.bulletgame.server.engine.networking.ConnectionManagerFactory;

import java.util.logging.Logger;

public class Application extends Thread {

    private ConnectionManager connectionManager;
    private static final Logger log = Logger.getLogger(Application.class.getCanonicalName());

    @Override
    public void run() {
        initialize();
    }

    private void initialize() {
        log.info("Initializing Application");
        this.connectionManager = ConnectionManagerFactory.getConnectionManager();
        this.connectionManager.start();
        log.info("Finished initializing Application");
    }
}

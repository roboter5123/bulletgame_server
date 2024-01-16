package com.roboter5123.bulletgame.server.application;
import com.roboter5123.bulletgame.server.application.api.ConnectionManager;
import com.roboter5123.bulletgame.server.application.api.ConnectionManagerFactory;

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

package com.roboter5123.bulletgame.server;

import com.roboter5123.bulletgame.server.engine.AbstractApplication;

import java.util.logging.Logger;

public class Application extends AbstractApplication {

    private static final Logger log = Logger.getLogger(Application.class.getCanonicalName());

    @Override
    protected void gameLoop() {
        while (!this.isInterrupted()) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                this.interrupt();
            }
            log.info(this.connections.toString());
        }
    }
}

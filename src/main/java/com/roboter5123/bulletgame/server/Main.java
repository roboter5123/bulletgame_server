package com.roboter5123.bulletgame.server;
import com.roboter5123.bulletgame.server.application.Application;

import java.util.logging.Logger;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getCanonicalName());
    public static void main(String[] args) {

        log.info("Attempting to start application");
        Application application = new Application();
        application.start();
    }
}

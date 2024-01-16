package com.roboter5123.bulletgame.server.application;
import com.roboter5123.bulletgame.server.application.api.Connection;
import com.roboter5123.bulletgame.server.application.api.ConnectionManager;
import com.roboter5123.bulletgame.server.application.api.ConnectionManagerImpl;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Application extends Thread {
    private List<Connection> connections = new ArrayList<>();
    private ConnectionManager connectionManager;

    @Override
    public void run() {

        this.connectionManager = new ConnectionManagerImpl(connections);
        this.connectionManager.start();
    }


}

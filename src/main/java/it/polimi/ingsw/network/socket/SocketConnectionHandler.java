package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * This class runs in a separate thread and accepts socket connections.
 */
public class SocketConnectionHandler extends Thread {

    @Override
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();
        boolean serverIsOn = true;
        try (ServerSocket serverSocket = new ServerSocket(ServerMain.getSocketPort())) {
            SagradaLogger.log(Level.INFO, "Socket server ready on port " + ServerMain.getSocketPort() + ", waiting for connections...");
            while(serverIsOn) {
                Socket clientSocket = serverSocket.accept();
                SagradaLogger.log(Level.INFO, "A new socket client has connected!");
                //executor.submit(new ServerEncoderDecoder());
            }
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Socket server exception", e);
        }
    }
}

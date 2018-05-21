package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.ClientMain;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.network.IServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiFromClientToServer implements IFromClientToServer {

    IServer rmiServer;

    public RmiFromClientToServer(String ip) {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, ClientMain.PORT);
            rmiServer = (IServer) registry.lookup("IServer");
        } catch (Exception e) {
            System.err.println("Rmi client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void login(String playerName, String ip, String gameMode) {
        rmiServer.login(playerName, ip, gameMode);
    }

    @Override
    public void exitGame(String playerName) {
        rmiServer.exitGame(playerName);
    }
}

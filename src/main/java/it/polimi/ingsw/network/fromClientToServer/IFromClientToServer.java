package it.polimi.ingsw.network.fromClientToServer;

public interface IFromClientToServer {

    public void login(String playerName, String ip, int port, String gameMode);
}

package it.polimi.ingsw.network;

public interface IFromClientToServer {

    public void login(String playerName, String ip, int port, String gameMode);
}

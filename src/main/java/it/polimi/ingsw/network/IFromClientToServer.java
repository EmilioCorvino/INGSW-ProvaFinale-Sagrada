package it.polimi.ingsw.network;

/**
 * This interface lists all the methods the client can require from the server.
 * @see it.polimi.ingsw.network.rmi.RmiFromClientToServer
 * @see it.polimi.ingsw.network.socket.SocketFromClientToServer
 */
public interface IFromClientToServer {

    /**
     * Lets the player log in the match.
     * @param playerName name the player chooses for himself in the application.
     * @param ip address to which the server is located.
     * @param gameMode can be either single player or multi-player.
     */
    public void login(String playerName, String ip, String gameMode);

    /**
     * Lets the player log out from the game.
     * @param playerName player who wants to log out.
     */
    public void exitGame(String playerName);
}

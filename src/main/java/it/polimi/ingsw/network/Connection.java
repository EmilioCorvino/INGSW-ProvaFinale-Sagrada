package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ServerImplementation;

/**
 * This class is used to identify the connection between a client and the server. The client has a unique
 * {@link PlayerColor} in the {@link it.polimi.ingsw.controller.WaitingRoom} and during a match.
 */
public class Connection {

    /**
     * Reference to the client.
     */
    private IFromServerToClient client;

    /**
     * Reference to the server.
     */
    private ServerImplementation server;

    /**
     * Color identifying the player.
     */
    private PlayerColor playerColor;

    public Connection(IFromServerToClient client, ServerImplementation server) {
        this.client = client;
        this.server = server;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public IFromServerToClient getClient() {
        return client;
    }

    public void setClient(IFromServerToClient client) {
        this.client = client;
    }
}

package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ServerImplementation;

import java.io.Serializable;

/**
 * This class is used to identify the connection between a client and the server. The client has a unique
 * {@link PlayerColor} in the {@link it.polimi.ingsw.controller.WaitingRoom} and during a match.
 */
public class Connection implements Serializable {

    /**
     * Reference to the client.
     */
    private transient IFromServerToClient client;

    /**
     * Reference to the server.
     */
    private transient ServerImplementation server;

    /**
     * Name of the player owning the connection.
     */
    private final String userName;

    public Connection(String userName) {
        this.userName = userName;
    }

    public IFromServerToClient getClient() {
        return client;
    }

    public ServerImplementation getServer() {
        return server;
    }

    public String getUserName() {
        return userName;
    }

    public void setClient(IFromServerToClient client) {
        this.client = client;
    }

    public void setServer(ServerImplementation server) {
        this.server = server;
    }
}

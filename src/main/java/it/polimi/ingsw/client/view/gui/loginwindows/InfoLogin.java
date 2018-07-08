package it.polimi.ingsw.client.view.gui.loginwindows;

/**
 * This class manages the data of the player at the login phase.
 */
public class InfoLogin {

    /**
     * The ip address of the server.
     */
    private String ipAddress;

    /**
     * The type of connection chosen by the server.
     */
    private String typeConn;

    /**
     * The username of the player that wants to connect.
     */
    private String username;

    /**
     * The type of game chosen.
     */
    private String gameMode;


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTypeConn() {
        return typeConn;
    }

    public void setTypeConn(String typeConn) {
        this.typeConn = typeConn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}
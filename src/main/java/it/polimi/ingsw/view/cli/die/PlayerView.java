package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.model.player.Player;

/**
 * This class identify the player.
 */
public class PlayerView {

    private String userName;

    /**
     * Window pattern card own by this player
     */
    private WindowPatternCardView wp;

    public String getUserName() {
        return userName;
    }

    public WindowPatternCardView getWp() {
        return wp;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWp(WindowPatternCardView wp) {
        this.wp = wp;
    }

    public boolean isSamePlayerAs(Player player) {
        return this.getUserName().equals(player.getPlayerName());
    }
}

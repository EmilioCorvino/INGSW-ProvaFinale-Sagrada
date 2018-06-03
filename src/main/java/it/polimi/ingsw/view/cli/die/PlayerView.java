package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.model.player.Player;

/**
 * This class identify the player.
 */
public class PlayerView {

    private String userName;

    private String privateObjCard;

    private int favorToken;

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

    public String getPrivateObjCard() {
        return privateObjCard;
    }

    public int getFavorToken() {
        return favorToken;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWp(WindowPatternCardView wp) {
        this.wp = wp;
    }

    public void setPrivateObjCard(String privateObjCard) {
        this.privateObjCard = privateObjCard;
    }

    public void setFavorToken(int favorToken) {
        this.favorToken = favorToken;
    }


    public boolean isSamePlayerAs(Player player) {
        return this.getUserName().equals(player.getPlayerName());
    }
}

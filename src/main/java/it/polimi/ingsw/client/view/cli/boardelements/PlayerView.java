package it.polimi.ingsw.client.view.cli.boardelements;

import it.polimi.ingsw.client.view.cli.die.WindowPatternCardView;

/**
 * This class that identifies the player in the view.
 */
public class PlayerView {

    /**
     * Name of the player.
     */
    private String userName;

    /**
     * Private Objective Card owned by this player.
     */
    private String privateObjCard;

    /**
     * Number of Favor Tokens owned by this player.
     */
    private int favorToken;

    /**
     * Window pattern card owned by this player.
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

    public void setPrivateObjCard(String privateObjCard) {
        this.privateObjCard = privateObjCard;
    }

    public void setFavorToken(int favorToken) {
        this.favorToken = favorToken;
    }

    /**
     * This method reset all the attribute of the player except the user name, it is used after a new game request.
     */
    public void resetPlayer(){
        this.privateObjCard = null;
        this.favorToken = 0;
        this.wp = null;
    }

    /**
     * Converts the Private Objective Card into a string.
     * @return a string representing the Private Objective Card.
     */
    public String privateObjToString(){
        String privateObj = "\nIl tuo obiettivo privato e': ";

        return  privateObj.concat(this.privateObjCard);
    }

    /**
     * Converts the Favor Tokens into a string.
     * @return a string representing the Favor Tokens.
     */
    public String favTokensToString(){
        String favTokens = "\nIl numero di segnalini favore e': ";
        return favTokens + this.favorToken;
    }
}

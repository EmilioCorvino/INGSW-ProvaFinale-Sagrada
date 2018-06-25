package it.polimi.ingsw.view.gui;

import javax.swing.text.html.ImageView;

public class PlayersData {

    private String username;

    private WpGui personalWp;

    private ImageView privateCard;

    public PlayersData() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public WpGui getPersonalWp() {
        return personalWp;
    }

    public void setPersonalWp(WpGui personalWp) {
        this.personalWp = personalWp;
    }

    public ImageView getPrivateCard() {
        return privateCard;
    }

    public void setPrivateCard(ImageView privateCard) {
        this.privateCard = privateCard;
    }
}
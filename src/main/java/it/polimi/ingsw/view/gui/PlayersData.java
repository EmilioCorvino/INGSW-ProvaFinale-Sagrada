package it.polimi.ingsw.view.gui;

public class PlayersData {

    private String username;

    private WpGui personalWp;

    private int idPrivateCard;


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

    public int getIdPrivateCard() {
        return idPrivateCard;
    }

    public void setIdPrivateCard(int idPrivateCard) {
        this.idPrivateCard = idPrivateCard;
    }
}
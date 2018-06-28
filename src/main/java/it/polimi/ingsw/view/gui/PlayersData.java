package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;

public class PlayersData {

    private String username;

    private WpGui personalWp;

    private int idPrivateCard;

    private int numFavTok;

    private SetUpInformationUnit setUpInformationUnit;



    public PlayersData() {
        setUpInformationUnit = new SetUpInformationUnit();

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
        System.out.println("settata la mappa personale");
        this.personalWp = personalWp;
    }

    public int getIdPrivateCard() {
        return idPrivateCard;
    }

    public void setIdPrivateCard(int idPrivateCard) {
        this.idPrivateCard = idPrivateCard;
    }

    public int getNumFavTok() {
        return numFavTok;
    }

    public void setNumFavTok(int numFavTok) {
        this.numFavTok = numFavTok;
    }
}
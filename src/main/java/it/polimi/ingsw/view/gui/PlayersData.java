package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class manages the personal data of a player during the game.
 */
public class PlayersData {

    /**
     * The username of the player.
     */
    private String username;

    /**
     * This is the personal map of the player.
     */
    private WpGui personalWp;

    /**
     * This is the id of the personal private objective card.
     */
    private int idPrivateCard;

    /**
     * This is the number of favor tokens of the player.
     */
    private int numFavTok;

    /**
     * This is the object used to collect the inputs of the player.
     */
    private SetUpInformationUnit setUpInformationUnit;

    /**
     * This indicates the slot of the tool card chosen.
     */
    private int slotChosen;

    /**
     * This is the list of the maps pf the other players.
     */
    private List<VBox> otherPLayersMaps;

    private List<WpGui> otherMaps;

    public PlayersData() {
        setUpInformationUnit = new SetUpInformationUnit();
        otherPLayersMaps = new ArrayList<>();
        otherMaps = new ArrayList<>();
    }

    public void constructOtherPlayerMap(Map<String, SimplifiedWindowPatternCard> players) {
        players.entrySet().forEach( entry -> {
            if(!entry.getKey().equals(this.username))
                constructSingleotherMap(entry.getKey(), entry.getValue());
        });
    }

    private void constructSingleotherMap(String name, SimplifiedWindowPatternCard map) {
        VBox box = new VBox();
        box.setMinWidth(245);
        box.setMaxWidth(245);
        box.setSpacing(20);
        box.setPadding(new Insets(10));
        box.getStylesheets().add("style/backgrounds.css");

        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("text-label");

        WpGui wpGui = new WpGui();
        this.otherMaps.add(wpGui);
        wpGui.setName(name);
        wpGui.constructMap(map);

        box.getChildren().addAll(nameLabel, wpGui.getGlassWindow());

        this.otherPLayersMaps.add(box);
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

    public SetUpInformationUnit getSetUpInformationUnit() {
        return setUpInformationUnit;
    }

    public void setSetUpInformationUnit(SetUpInformationUnit setUpInformationUnit) {
        this.setUpInformationUnit = setUpInformationUnit;
    }

    public int getSlotChosen() {
        return slotChosen;
    }

    public void setSlotChosen(int slotChosen) {
        this.slotChosen = slotChosen;
    }

    public List<VBox> getOtherPLayersMaps() {
        return otherPLayersMaps;
    }

    public List<WpGui> getOtherMaps() {
        return otherMaps;
    }
}
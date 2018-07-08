package it.polimi.ingsw.client.view.gui.loginwindows;

import it.polimi.ingsw.client.view.gui.GUIMain;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the representation of the connected player befor the match starts.
 */
public class ShowPlayersGUI extends BorderPane {

    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * The main container of this window.
     */
    private VBox mainContainer;

    /**
     * The container for the connected players.
     */
    private VBox playersList;

    /**
     * List of already connected player.
     */
    private List<String> knownPlayer;

    /**
     * List of names to show.
     */
    private List<String> players;

    public ShowPlayersGUI() {
        players = new ArrayList<>();
        mainContainer = new VBox();
        Label names = new Label("Giocatori momentaneamente connessi:");
        names.getStyleClass().add("title");
        playersList = new VBox();

        mainContainer.getChildren().addAll(names, playersList);
        this.getStylesheets().add("style/backgrounds.css");
        this.getStyleClass().add("background");

        customMainContainer();
        customPlayersList();

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this:: pressedWindow);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this:: draggedWindow);

        this.knownPlayer = new ArrayList<>();
    }

    /**
     * This methos customs the main container to repsents the information of the connected players.
     */
    private void customMainContainer() {
        this.mainContainer.setSpacing(30);
        this.mainContainer.setMaxWidth(550);
        this.mainContainer.setMaxHeight(400);
        this.mainContainer.setMinWidth(550);
        this.mainContainer.setMinHeight(400);
        this.mainContainer.getStyleClass().add("VBox");
        this.mainContainer.setAlignment(Pos.CENTER);
        this.autosize();
    }

    /**
     * This method customs the form for this particular window.
     */
    private void customPlayersList() {
        this.setCenter(this.mainContainer);
        this.playersList.setAlignment(Pos.CENTER);
        this.playersList.setSpacing(20);
        this.autosize();
    }

    /**
     * This method represents the names of the connected players.
     */
    public void showPlayers() {
        this.players.forEach(name -> {
            if(!knownPlayer.contains(name)){
                putLabel(name);
                knownPlayer.add(name);
            }
        });
    }

    /**
     * This method constructs the label to repsents a single name.
     * @param username the name to represents.
     */
    private void putLabel(String username){
        Label name = new Label(username);
        name.getStyleClass().add("text-label");
        playersList.getChildren().add(name);
    }

    /**
     * This method manages the pressure of the window
     * @param event the event to consider.
     */
    protected void pressedWindow(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    /**
     * This method manages the dragging of the window.
     * @param event the event to consider.
     */
    protected void draggedWindow(MouseEvent event) {
        GUIMain.getStage().setX(event.getScreenX() - xOffset);
        GUIMain.getStage().setY(event.getScreenY() - yOffset);
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }
}
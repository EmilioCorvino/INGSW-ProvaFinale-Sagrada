package it.polimi.ingsw.view.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ShowPlayersGUI extends BorderPane {

    private double xOffset = 0;
    private double yOffset = 0;

    private VBox mainContainer;

    private VBox playersList;

    private List<String> knownPlayer;

    public ShowPlayersGUI() {

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

    public void customMainContainer() {
        this.mainContainer.setSpacing(30);
        this.mainContainer.setMaxWidth(550);
        this.mainContainer.setMaxHeight(400);
        this.mainContainer.setMinWidth(550);
        this.mainContainer.setMinHeight(400);
        this.mainContainer.getStyleClass().add("VBox");
        BorderPane.setMargin(this.mainContainer, new Insets(10, 250, 10, 250));
        this.mainContainer.setAlignment(Pos.CENTER);
        this.autosize();
    }

    /**
     * This method customs the form for this particular window.
     */
    public void customPlayersList() {
        BorderPane.setMargin(this.mainContainer, new Insets(10, 250, 10, 250));
        this.setCenter(this.mainContainer);
        this.playersList.setAlignment(Pos.CENTER);
        this.playersList.setSpacing(20);
        this.autosize();
    }


    public void showPlayers(List<String> players) {
        players.forEach(name -> {
            if(!knownPlayer.contains(name)){
                putLabel(name);
                knownPlayer.add(name);
            }
        });
    }

    private void putLabel(String username){
        Label name = new Label(username);
        name.getStyleClass().add("text-label");
        playersList.getChildren().add(name);
    }

    protected void pressedWindow(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    protected void draggedWindow(MouseEvent event) {
        GUIMain.getStage().setX(event.getScreenX() - xOffset);
        GUIMain.getStage().setY(event.getScreenY() - yOffset);
    }
}

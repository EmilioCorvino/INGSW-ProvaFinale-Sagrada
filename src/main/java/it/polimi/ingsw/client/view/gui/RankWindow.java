package it.polimi.ingsw.client.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * This class manages the representation of the results of the game.
 */
public class RankWindow extends ParentWindow {

    private GUICommunicationManager manager;

    public RankWindow(GUICommunicationManager manager) {

        this.manager = manager;

        this.getStylesheets().add("style/backgrounds.css");
        this.getStyleClass().add("background");
        this.getStyleClass().add("VBox");
        this.setMinHeight(400);
        this.setMinWidth(550);

        GUIMain.centerScreen();
        GUIMain.dragWindow(this);

        initializeHeader();

        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(30));
    }

    /**
     * This method associates the name of a player with the personal score in a proper container to display.
     * @param name the name of the player.
     * @param score the score of the player.
     */
    public void updateNames(String name, int score) {
        HBox nameScore = new HBox();
        nameScore.setSpacing(30);

        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("text-label");

        Label scoreLabel = new Label(score + "");
        scoreLabel.getStyleClass().add("text-label");

        nameScore.getChildren().addAll(nameLabel, scoreLabel);
        this.getChildren().add(nameScore);
    }

    /**
     * This method constructs the header of this window.
     */
    @Override
    public void initializeHeader() {
        Label title = new Label("Classifica:");
        this.getChildren().add(title);
        title.getStyleClass().add("title");
        title.setPadding(new Insets(10));
    }


    @Override
    public void addHandlers() {
        HBox commandsEndGame = new HBox();

        Button newGame = new Button("Nuova partita");
        newGame.getStyleClass().add("button-style");

        Button exit = new Button("Esci");
        exit.getStyleClass().add("button-style");

        commandsEndGame.getChildren().addAll(newGame, exit);
        this.getChildren().addAll(commandsEndGame);

        newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(this.manager.isCommandContained("Nuova Partita"))
                this.manager.executeCommandIfPresent("Nuova Partita");
            else
                this.manager.communicateMessage("Comando non supportato");
        });

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(this.manager.isCommandContained("Logout"))
                this.manager.executeCommandIfPresent("Logout");
            else
                this.manager.communicateMessage("Comando non supportato");
        });
    }

    @Override
    public void showMessage(String mex) {

    }
}
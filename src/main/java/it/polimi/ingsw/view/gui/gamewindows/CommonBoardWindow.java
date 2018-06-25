package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.view.gui.ParentWindow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CommonBoardWindow extends ParentWindow {

    private VBox mainContainer;

    private HBox header;

    private HBox secondContainer;

    private HBox gameData;

    public CommonBoardWindow() {
        mainContainer = new VBox();
        header = new HBox();
        secondContainer = new HBox();
        gameData = new HBox();
    }


    @Override
    public void addHandlers() {

    }
}

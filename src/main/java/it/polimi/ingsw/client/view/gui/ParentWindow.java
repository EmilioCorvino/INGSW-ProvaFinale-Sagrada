package it.polimi.ingsw.client.view.gui;

import javafx.scene.layout.VBox;

public abstract class ParentWindow extends VBox {

    public abstract void initializeHeader();

    public abstract void addHandlers();

    public abstract void showMessage(String mex);
}
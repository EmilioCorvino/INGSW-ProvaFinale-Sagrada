package it.polimi.ingsw.client.view.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public abstract class ParentWindow extends VBox {

    protected Button reconnect = new Button("Riconnettiti");

    public abstract void initializeHeader();

    public abstract void addHandlers();

    public abstract void showMessage(String mex);

    public Button getReconnect() {
        return reconnect;
    }

    public void setReconnect(Button reconnect) {
        this.reconnect = reconnect;
    }
}

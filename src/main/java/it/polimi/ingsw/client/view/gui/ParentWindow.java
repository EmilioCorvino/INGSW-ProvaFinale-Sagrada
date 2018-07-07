package it.polimi.ingsw.client.view.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public abstract class ParentWindow extends VBox {

    /**
     * The button for reconnection.
     */
    protected Button reconnect;

    protected ParentWindow() {
        this.reconnect = new Button("Riconnettiti");
        this.reconnect.setVisible(false);

    }

    public abstract void initializeHeader();

    public abstract void addHandlers();


    public Button getReconnect() {
        return reconnect;
    }

    public void setReconnect(Button reconnect) {
        this.reconnect = reconnect;
    }
}
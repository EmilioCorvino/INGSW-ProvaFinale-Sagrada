package it.polimi.ingsw.client.view.gui.loginwindows;

import it.polimi.ingsw.client.view.gui.GUIMain;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class LoginRootGUI extends BorderPane {

    private double xOffset = 0;
    private double yOffset = 0;

    public LoginRootGUI() {

    }


    protected void pressedWindow(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    protected void draggedWindow(MouseEvent event) {
        GUIMain.getStage().setX(event.getScreenX() - xOffset);
        GUIMain.getStage().setY(event.getScreenY() - yOffset);
    }

    /**
     * This method customs the form for this particular window.
     * @param loginFormGUI the form to custom.
     */
    public void customForm(LoginFormGUI loginFormGUI) {
        loginFormGUI.getvBox().setAlignment(Pos.CENTER);
        this.setCenter(loginFormGUI.getvBox());
        this.autosize();
    }
}
package it.polimi.ingsw.view.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class RootLoginGui extends BorderPane {

    private double xOffset = 0;
    private double yOffset = 0;

    public RootLoginGui() {


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
        BorderPane.setMargin(loginFormGUI.getvBox(), new Insets(10, 250, 10, 250));
        loginFormGUI.getvBox().setAlignment(Pos.CENTER);
        this.setCenter(loginFormGUI.getvBox());
        this.autosize();
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }
}

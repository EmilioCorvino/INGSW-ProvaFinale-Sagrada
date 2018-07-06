package it.polimi.ingsw.view.gui.gamewindows.toolcardsGUImanagers;

import it.polimi.ingsw.view.gui.EnlargementHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class manages the tool card in the GUI view.
 */
public class ToolCardGUI extends VBox {

    /**
     * The id of the tool used to identify proper methods to invoke to manage it.
     */
    private int idTool;

    /**
     * The slot of the tool inside the proper GUI tool container to send to the server.
     */
    private int toolSlot;


    public ToolCardGUI(String path) {
        this.getStylesheets().add("style/backgrounds.css");

        Image tool = new Image(path);
        ImageView view = new ImageView(tool);
        view.setFitHeight(240);
        view.setPreserveRatio(true);

        EnlargementHandler.addToAnimation(view);

        this.getChildren().add(view);

        HBox toolInfo = new HBox();
        this.getChildren().add(toolInfo);
        toolInfo.setSpacing(10);

        HBox costInfo = new HBox();
        Label titleCost = new Label("Costo:");
        titleCost.getStyleClass().add("text-label");
        costInfo.getChildren().add(titleCost);
        costInfo.setSpacing(10);
        costInfo.setPadding(new Insets(10));

        //The effective cost of the tool, updated by the server.
        Label cost = new Label("1");
        costInfo.getChildren().add(cost);
        toolInfo.getChildren().add(costInfo);
        cost.getStyleClass().add("text-label");

        VBox buttonsTool = new VBox();

        Button okButton = new Button("Ok");
        okButton.setVisible(false);
        okButton.getStyleClass().add("button-style");
        buttonsTool.getChildren().add(okButton);

        Button show = new Button("Mostra");
        show.setVisible(false);
        show.getStyleClass().add("button-style");
        buttonsTool.getChildren().add(show);

        toolInfo.getChildren().add(buttonsTool);

        this.setSpacing(40);
    }

    /**
     * This method manages the hiding and the showing of the button for those tool that needs extra commands.
     * @param val the value that determines the visibility.
     */
    public void hideShowButton(Boolean val) {
        HBox toolInfoCont = (HBox)this.getChildren().get(1);
        VBox toolComm = (VBox)toolInfoCont.getChildren().get(1);

        Button showButton = (Button)toolComm.getChildren().get(1);
        showButton.setVisible(val);
    }

    /**
     * This method updates the cost of a tool in the GUI view.
     * @param cost the cost to set.
     */
    public void updateCost(int cost) {
        HBox costCont = (HBox)((HBox)this.getChildren().get(1)).getChildren().get(0);
        Label costToUpd = (Label)costCont.getChildren().get(1);
        costToUpd.setText(cost + "");
    }

    public int getIdTool() {
        return idTool;
    }

    public void setIdTool(int idTool) {
        this.idTool = idTool;
    }

    public int getToolSlot() {
        return toolSlot;
    }

    public void setToolSlot(int toolSlot) {
        this.toolSlot = toolSlot;
    }
}
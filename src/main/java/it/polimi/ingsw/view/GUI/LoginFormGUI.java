package it.polimi.ingsw.view.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LoginFormGUI extends VBox {

    private VBox vBox;

    private GridPane gridPane;

    private HBox buttonContainer;



    public LoginFormGUI() {
        setvBox(new VBox());
        this.gridPane = new GridPane();
        this.buttonContainer = new HBox();
    }

    public void formatvBox(String label, String button1, String button2) {
        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(buttonContainer);
        vBox.setMaxWidth(400);
        vBox.setMaxHeight(300);
        vBox.setStyle("-fx-background-color: #ccddff;");
        vBox.setPadding(new Insets(10, 50, 10, 50));
        vBox.setSpacing(20);

        formatGridpane(label);
        formatButtonContainer(button1, button2);


    }

    public void formatGridpane(String nameLabel) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(25, 0, 25, 0));
        gridPane.setStyle("-fx-background-color : #ffffcc");

        gridPane.setMaxWidth(Double.MAX_VALUE);

        TextField text = new TextField();
        Label label = new Label(nameLabel);

        this.gridPane.add(label, 0, 0);
        this.gridPane.add(text, 0, 1);

        text.setMaxWidth(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);

    }

    public void formatButtonContainer(String nameButton1, String nameButton2) {
        Button btn1 = new Button(nameButton1);
        Button btn2 = new Button(nameButton2);

        buttonContainer.getChildren().add(btn1);
        buttonContainer.getChildren().add(btn2);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(20);

        //btn1.setMaxWidth(75);
        //btn2.setMaxWidth(75);

        btn1.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btn1, Priority.ALWAYS);
        HBox.setHgrow(btn2, Priority.ALWAYS);

    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public HBox getButtonContainer() {
        return buttonContainer;
    }

    public void setButtonContainer(HBox buttonContainer) {
        this.buttonContainer = buttonContainer;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }
}
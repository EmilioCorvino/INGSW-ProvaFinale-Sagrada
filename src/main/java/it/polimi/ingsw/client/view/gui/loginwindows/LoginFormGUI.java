package it.polimi.ingsw.client.view.gui.loginwindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class manages the form style used in the login.
 */
public class LoginFormGUI extends VBox {

    /**
     * The main container for the fields of the form.
     */
    private VBox vBox;

    /**
     * The grid that will contains the input parameter of the user.
     */
    private GridPane gridPane;

    /**
     * The container that the user will use for specific choices of the game.
     */
    private HBox buttonContainer;

    /**
     * The button to start the game.
     */
    private Button goAhead;

    private Button exit;


    public LoginFormGUI() {
        setvBox(new VBox());

        this.gridPane = new GridPane();
        this.buttonContainer = new HBox();
        this.goAhead = new Button("Gioca");
        this.exit = new Button("Esci");
        this.getStylesheets().add("style/backgrounds.css");
        this.goAhead.getStyleClass().add("button-style");
        this.exit.getStyleClass().add("button-style");

        this.exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleClose());
    }

    public void handleClose() {
        System.exit(0);
    }

    /**
     * This method formats the main container for this window.
     * @param label
     * @param button1
     * @param button2
     */
    public void formatvBox(String label, String button1, String button2) {
        this.vBox.getStyleClass().add("VBox");
        HBox controls = new HBox();
        controls.setSpacing(30);
        controls.setAlignment(Pos.CENTER);
        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(buttonContainer);


        vBox.setMaxWidth(550);
        vBox.setMaxHeight(400);
        vBox.setMinWidth(550);
        vBox.setMinHeight(400);

        vBox.setPadding(new Insets(10, 50, 10, 50));
        vBox.setSpacing(30);

        formatGridpane(label);
        formatButtonContainer(button1, button2);

        this.exit.setMaxWidth(120);
        this.goAhead.setMaxWidth(120);

        controls.getChildren().add(this.goAhead);
        controls.getChildren().add(this.exit);
        vBox.getChildren().add(controls);
        goAhead.setVisible(false);
        exit.setVisible(false);
    }

    /**
     *
     * @param nameLabel
     */
    public void formatGridpane(String nameLabel) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(25, 0, 25, 0));

        gridPane.setMaxWidth(Double.MAX_VALUE);

        TextField text = new TextField();
        text.getStyleClass().add("text-field");
        text.setText("localhost");
        text.setAlignment(Pos.CENTER);

        Label label = new Label(nameLabel);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("text-label");


        this.gridPane.add(label, 0, 0);
        this.gridPane.add(text, 0, 1);

        text.setMaxWidth(Double.MAX_VALUE);
        label.setMaxWidth(Double.MAX_VALUE);
    }

    /**
     *
     * @param nameButton1
     * @param nameButton2
     */
    public void formatButtonContainer(String nameButton1, String nameButton2) {
        ToggleButton btn1 = new ToggleButton(nameButton1);
        ToggleButton btn2 = new ToggleButton(nameButton2);

        btn1.setId("1");
        btn1.setId("2");

        buttonContainer.getChildren().add(btn1);
        buttonContainer.getChildren().add(btn2);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(20);

        btn1.setMaxWidth(150);
        btn2.setMaxWidth(150);

        btn1.getStyleClass().add("button-style");
        btn2.getStyleClass().add("button-style");

        HBox.setHgrow(btn1, Priority.ALWAYS);
        HBox.setHgrow(btn2, Priority.ALWAYS);
    }

    /**
     *
     * @param message
     */
    public void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        alert.setOnCloseRequest(event -> alert.hide());
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

    public GridPane getGridPane() {
        return gridPane;
    }


    public Button getGoAhead() {
        return goAhead;
    }

    public Button getExit() {
        return exit;
    }

}
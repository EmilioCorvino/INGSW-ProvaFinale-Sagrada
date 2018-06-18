package it.polimi.ingsw.view.GUI;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GameHome extends BorderPane {

    private LoginFormGUI loginFormGUI;

    public GameHome(String w1, String w2, String w3) {

        loginFormGUI = new LoginFormGUI();
        loginFormGUI.formatvBox(w1, w2, w3);
        customForm(loginFormGUI);

        HBox buttons = loginFormGUI.getButtonContainer();
        buttons.getChildren().forEach(button -> button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleTypeConnection()));

    }

    public void customForm(LoginFormGUI loginFormGUI) {
        BorderPane.setMargin(loginFormGUI.getvBox(), new Insets(10, 250, 10, 250));
        loginFormGUI.getvBox().setAlignment(Pos.CENTER);
        this.setCenter(loginFormGUI.getvBox());

        this.autosize();
    }

    public void handlePlayButton () {
       // Scene scene = new Scene(new SceneTwo(), 500, 500);
       // GUIMain.getStage().setScene(scene);
    }

    public void handleTypeConnection() {
        Parent root = new GameHome("Inserire username", "Giocatore singolo", "Multigiocatore");
        root.setStyle("-fx-background-color : #001a4d");
        handleGamePlayButton(((GameHome) root).getLoginFormGUI());
        GUIMain.getScene().setRoot(root);

    }

    public void handleGamePlayButton(LoginFormGUI login) {
        HBox buttons = login.getButtonContainer();
        buttons.getChildren().forEach(button -> button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Parent root = new ProvisionalNewScene();
            GUIMain.getScene().setRoot(root);
        }));

    }



    public LoginFormGUI getLoginFormGUI() {
        return loginFormGUI;
    }

    public void setLoginFormGUI(LoginFormGUI loginFormGUI) {
        this.loginFormGUI = loginFormGUI;
    }
}

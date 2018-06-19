package it.polimi.ingsw.view.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * This class manages the inputs from user which are: username and type game desired.
 */
public class LoginUsernameGameModeGUI extends BorderPane {

    /**
     * This is the attribute where all the necessary information to validate the connection is saved.
     */
    private InfoLogin info;

    /**
     * This attribute is the container to take and display inputs tu the users.
     */
    private LoginFormGUI loginFormGUI;

    /**
     * This attribute indicates if all the information to proceed the login are complete.
     */
    private boolean finished = false;

    public LoginUsernameGameModeGUI() {

        info = new InfoLogin();

        loginFormGUI = new LoginFormGUI();
        loginFormGUI.formatvBox("Inserire lo username: ", "Giocatore singolo", " Multigiocatore  ");
        customForm(loginFormGUI);
        loginFormGUI.getGoAhead().setVisible(true);

        this.setStyle("-fx-background-color : #001a4d");

        HBox buttons = loginFormGUI.getButtonContainer();

        buttons.getChildren().get(0).setId("2");
        buttons.getChildren().get(1).setId("1");
        buttons.getChildren().forEach(button -> button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            handleTypeConnection((Button)button);
        }));

        loginFormGUI.getGoAhead().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleGoAhead());
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

    /**
     * This method manages the actions to perform when a button of the button container of the general
     * login form structure.
     * @param button the button to handle.
     */
    public void handleTypeConnection(Button button) {
        String text = ((TextField)loginFormGUI.getGridPane().getChildren().get(1)).getText();

        if(text.isEmpty()) {
            loginFormGUI.showAlertMessage("Questo campo non può essere lasciato vuoto");
            return;
        }
        this.info.setUsername(text);
        System.out.println(button.getId() + "");
        this.info.setGameMode(button.getId());
    }

    /**
     * This method manages the button that enables to validate the login.
     */
    public void handleGoAhead() {
        String text = ((TextField)loginFormGUI.getGridPane().getChildren().get(1)).getText();
        System.out.println(text);
        if(text.isEmpty()) {
            loginFormGUI.showAlertMessage("Devi inserire lo username");
            return;
        }

        System.out.println(this.info + "");
        System.out.println(this.info.getUsername());
        System.out.println(this.info.getGameMode());
        if( this.info.getGameMode() == null) {
            loginFormGUI.showAlertMessage("Devi scegliere una modalità di gioco");
            return;
        }
        this.setFinished(true);
        GUIMain.getGuiView().createConnection(GUIMain.getGuiView());
    }

    public InfoLogin getInfo() {
        return info;
    }

    public void setInfo(InfoLogin info) {
        this.info = info;
    }

    public LoginFormGUI getLoginFormGUI() {
        return loginFormGUI;
    }

    public void setLoginFormGUI(LoginFormGUI loginFormGUI) {
        this.loginFormGUI = loginFormGUI;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
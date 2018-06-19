package it.polimi.ingsw.view.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * This class manages the inputs from user which are: ip address and type connection desired.
 */
public class LoginIpAddrTypeConnGUI extends BorderPane {

    /**
     * This attribute is the container to take and display inputs tu the users.
     */
    private LoginFormGUI loginFormGUI;

    /**
     * This attribute is the container to take and display inputs tu the users.
     */
    private InfoLogin infoLogin;

    /**
     * This attribute indicates if all the information to proceed the login are complete.
     */
    private boolean finished = false;

    /**
     * This attribute indicates if all the information to proceed the login are complete.
     */
    private boolean proceed = false;

    public LoginIpAddrTypeConnGUI() {
        infoLogin = new InfoLogin();
        this.setStyle("-fx-background-color : #001a4d");
        loginFormGUI = new LoginFormGUI();
        loginFormGUI.formatvBox("Indirizzo ip del server: ", "  RMI  ", "Socket ");
        customForm(loginFormGUI);

        HBox buttons = loginFormGUI.getButtonContainer();
        buttons.getChildren().forEach(button -> button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            handleTypeGameMode((Button)button);
        }));
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
     * This method manages the input from the user for the type of game mode to set.
     * @param button the button to handle.
     */
    public void handleTypeGameMode(Button button) {
        String text = ((TextField)loginFormGUI.getGridPane().getChildren().get(1)).getText();

        if(text.isEmpty()) {
            loginFormGUI.showAlertMessage("Questo campo non può essere lasciato vuoto");
            return;
        }
        this.infoLogin.setIpAddress(text);
        this.infoLogin.setTypeConn(button.getId());

        ((TextField) loginFormGUI.getGridPane().getChildren().get(1)).clear();
        this.setFinished(true);

        this.proceed = true;
        Parent root = new LoginUsernameGameModeGUI();
        ((LoginUsernameGameModeGUI) root).setInfo(this.infoLogin);
        GUIMain.getScene().setRoot(root);
    }

    public LoginFormGUI getLoginFormGUI() {
        return loginFormGUI;
    }

    public void setLoginFormGUI(LoginFormGUI loginFormGUI) {
        this.loginFormGUI = loginFormGUI;
    }

    public InfoLogin getInfoLogin() {
        return infoLogin;
    }

    public void setInfoLogin(InfoLogin infoLogin) {
        this.infoLogin = infoLogin;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isProceed() {
        return proceed;
    }

    public void setProceed(boolean proceed) {
        this.proceed = proceed;
    }
}
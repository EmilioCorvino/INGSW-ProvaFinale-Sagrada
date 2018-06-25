package it.polimi.ingsw.view.gui.loginwindows;

import it.polimi.ingsw.view.gui.GUIMain;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * This class manages the inputs from user which are: ip address and type connection desired.
 */
public class LoginIpAddrTypeConnGUI extends LoginRootGUI {

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

        //super();

        this.getStylesheets().add("style/backgrounds.css");
        this.getStyleClass().add("background");


        infoLogin = new InfoLogin();
        loginFormGUI = new LoginFormGUI();
        loginFormGUI.formatvBox("Indirizzo ip del server: ", "  RMI  ", "Socket ");
        super.customForm(loginFormGUI);

        HBox buttons = loginFormGUI.getButtonContainer();
        buttons.getChildren().forEach(button -> button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            handleTypeGameMode((ToggleButton)button);
        }));

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this:: pressedWindow);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this:: draggedWindow);
    }



    /**
     * This method manages the input from the user for the type of game mode to set.
     * @param button the button to handle.
     */
    public void handleTypeGameMode(ToggleButton button) {
        button.setSelected(true);
        String text = ((TextField)loginFormGUI.getGridPane().getChildren().get(1)).getText();

        if(text.isEmpty()) {
            button.setSelected(false);
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
        GUIMain.setRoot(root);
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
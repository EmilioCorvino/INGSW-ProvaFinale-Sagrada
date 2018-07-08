package it.polimi.ingsw.client.view.gui.loginwindows;

import it.polimi.ingsw.client.view.gui.GUICommunicationManager;
import it.polimi.ingsw.client.view.gui.GUIMain;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * This class manages the inputs from user which are: username and type game desired.
 */
public class LoginUsernameGameModeGUI extends LoginRootGUI {

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

    private GUICommunicationManager manager;

    public LoginUsernameGameModeGUI() {
        manager = new GUICommunicationManager();
        this.getStylesheets().add("style/backgrounds.css");
        this.getStyleClass().add("background");
        
        info = new InfoLogin();

        loginFormGUI = new LoginFormGUI();
        loginFormGUI.formatvBox("Inserire lo username: ", "Giocatore singolo", " Multigiocatore  ");
        super.customForm(loginFormGUI);
        loginFormGUI.getGoAhead().setVisible(true);
        loginFormGUI.getExit().setVisible(true);

        HBox buttons = loginFormGUI.getButtonContainer();

        buttons.getChildren().get(0).setId("2");
        buttons.getChildren().get(1).setId("1");
        buttons.getChildren().forEach(button -> button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            handleTypeConnection((ToggleButton)button);
        }));

        loginFormGUI.getGoAhead().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleGoAhead());

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this::pressedWindow);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this ::draggedWindow);
    }

    /**
     * This method manages the actions to perform when a button of the button container of the general
     * login form structure.
     * @param button the button to handle.
     */
    public void handleTypeConnection(ToggleButton button) {

        if(button.getText().equals("Giocatore singolo")) {
            this.manager.communicateMessage("Non ancora supportato");
            return;
        }

        loginFormGUI.getButtonContainer().getChildren().forEach(but -> {
            if(((ToggleButton)but).isSelected())
                ((ToggleButton)but).setSelected(false);
        });
        String text = ((TextField)loginFormGUI.getGridPane().getChildren().get(1)).getText();

        if(text.isEmpty()) {
            button.setSelected(false);
            loginFormGUI.showAlertMessage("Questo campo non può essere lasciato vuoto");
            return;
        }

        button.setSelected(true);

        this.info.setUsername(text);
        this.info.setGameMode(button.getId());
    }

    /**
     * This method manages the button that enables to validate the login.
     */
    public void handleGoAhead() {
        String text = ((TextField)loginFormGUI.getGridPane().getChildren().get(1)).getText();

        if(text.isEmpty()) {
            loginFormGUI.showAlertMessage("Devi inserire lo username");
            return;
        }
        if( this.info.getGameMode() == null) {
            loginFormGUI.getButtonContainer().getChildren().forEach(button -> ((ToggleButton)button).setSelected(false));
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

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
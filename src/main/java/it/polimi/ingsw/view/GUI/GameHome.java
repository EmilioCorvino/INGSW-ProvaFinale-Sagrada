package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.view.IViewMaster;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        buttons.getChildren().forEach(button -> button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            handleTypeConnection();
        }));


        loginFormGUI.getGoAhead().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleGoAhead());
    }

    public void customForm(LoginFormGUI loginFormGUI) {
        BorderPane.setMargin(loginFormGUI.getvBox(), new Insets(10, 250, 10, 250));
        loginFormGUI.getvBox().setAlignment(Pos.CENTER);
        this.setCenter(loginFormGUI.getvBox());
        this.autosize();
    }



    public void handleTypeConnection() {

        String text = ((TextField)loginFormGUI.getGridPane().getChildren().get(1)).getText();
        ((TextField) loginFormGUI.getGridPane().getChildren().get(1)).clear();

        if(text.isEmpty()) {
            loginFormGUI.showAlertMessage("error");
            return;
        }

        ((Label)loginFormGUI.getGridPane().getChildren().get(0)).setText("Inserire lo username:");
        HBox box = loginFormGUI.getButtonContainer();
        ((Button)box.getChildren().get(0)).setText("Giocatore singolo");
        ((Button)box.getChildren().get(1)).setText("Multigiocatore");
        loginFormGUI.getGoAhead().setVisible(true);
    }

    public void handleGoAhead() {
        String text = ((TextField)loginFormGUI.getGridPane().getChildren().get(1)).getText();

        if(text.isEmpty()) {
            loginFormGUI.showAlertMessage("Devi inserire il cazzo di username");
            return;
        }

        ProvisionalNewScene provisionalNewScene = new ProvisionalNewScene();
        GUIMain.getScene().setRoot(provisionalNewScene);

    }



    public String getIp() {

        return loginFormGUI.getWordTextField();

    }

    public IFromClientToServer chooseNetworkInterface(String ip, IViewMaster client) throws BrokenConnectionException {
        return null;
    }



    public LoginFormGUI getLoginFormGUI() {
        return loginFormGUI;
    }

    public void setLoginFormGUI(LoginFormGUI loginFormGUI) {
        this.loginFormGUI = loginFormGUI;
    }
}

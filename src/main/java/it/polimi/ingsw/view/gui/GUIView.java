package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.network.rmi.RmiFromClientToServer;
import it.polimi.ingsw.network.socket.SocketFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.gui.loginwindows.LoginIpAddrTypeConnGUI;
import it.polimi.ingsw.view.gui.loginwindows.LoginUsernameGameModeGUI;
import it.polimi.ingsw.view.gui.loginwindows.ShowPlayersGUI;
import it.polimi.ingsw.view.gui.setupwindows.ChooseWpGUI;
import it.polimi.ingsw.view.IViewMaster;
import javafx.application.Platform;
import javafx.scene.Parent;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class GUIView implements IViewMaster {

    /**
     * The network interface for the connection.
     */
    private IFromClientToServer server;

    private LoginIpAddrTypeConnGUI loginManager;

    private Parent listPlayers;

    private ChooseWpGUI chooseWpGUI;

    private PlayersData playersData;



    public GUIView() {
        listPlayers = new ShowPlayersGUI();
        chooseWpGUI = new ChooseWpGUI();
        playersData = new PlayersData();

    }

    /**
     * This method create the connection and logs the player
     * @param viewMaster Is the view connected to the net.
     */
    @Override
    public void createConnection(IViewMaster viewMaster) {

        if(loginManager.isFinished()) {
            try {
                String ipAddress = loginManager.getInfoLogin().getIpAddress();
                if (Integer.parseInt(loginManager.getInfoLogin().getTypeConn()) == 2)
                    this.server = new RmiFromClientToServer(ipAddress, this);

                if (Integer.parseInt(loginManager.getInfoLogin().getTypeConn()) == 1)
                    this.server = new SocketFromClientToServer();

            } catch (BrokenConnectionException e) {
                loginManager.setProceed(false);
                this.loginManager.getLoginFormGUI().showAlertMessage("Indirizzo ip non valido.");
                Parent root = new LoginIpAddrTypeConnGUI();
                this.setLoginManager((LoginIpAddrTypeConnGUI)root);
                GUIMain.setRoot(root);
            }
        }
            if(loginManager.isProceed()) {
                try {
                    String username = this.loginManager.getInfoLogin().getUsername();
                    String gameMode = this.loginManager.getInfoLogin().getGameMode();

                    this.server.login(Integer.parseInt(gameMode), username);
                    this.playersData.setUsername(username);

                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Connection broken during register", e);
                    loginManager.setProceed(false);
                    this.loginManager.getLoginFormGUI().showAlertMessage("Indirizzo ip non valido.");
                    Parent root = new LoginIpAddrTypeConnGUI();
                    this.setLoginManager((LoginIpAddrTypeConnGUI)root);
                    GUIMain.setRoot(root);
                } catch (UserNameAlreadyTakenException e) {
                    this.loginManager.getLoginFormGUI().showAlertMessage("Username già in uso!");
                    Parent root = new LoginUsernameGameModeGUI();
                    ((LoginUsernameGameModeGUI) root).setInfo(loginManager.getInfoLogin());
                    GUIMain.setRoot(root);

                } catch (TooManyUsersException e) {
                    this.loginManager.getLoginFormGUI().showAlertMessage("\nPartita piena, numero massimo di giocatori raggiunto!\nArrivederci.");
                    System.exit(0);
                } catch (MatchAlreadyStartedException e) {
                    this.loginManager.getLoginFormGUI().showAlertMessage("\nSpiacente la partita e' gia' iniziata!\nArrivederci.");
                    System.exit(0);
                }
            }
        System.out.println("connection ok");

    }


    @Override
    public void showRoom(List<String> players) {
       Platform.runLater(() -> {
           GUIMain.setRoot(this.listPlayers);
           ((ShowPlayersGUI) this.listPlayers).showPlayers(players);
       });



    }

    @Override
    public void showPrivateObjective(int idPrivateObj) {

        Platform.runLater(() -> {
            GUIMain.setRoot(this.chooseWpGUI);
            this.chooseWpGUI.assignPrivateObjectiveCard(idPrivateObj);
        });

    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
       Platform.runLater(() -> {
           for(int i=0; i<listWp.size();i++) {
               WpGui wp = new WpGui();
               wp.constructMap(listWp.get(i));
               this.chooseWpGUI.getMaps().add(wp);
               System.out.println("mappa " +i+ " added");
           }

           this.chooseWpGUI.formatMapsContainer();
       });

    }



    @Override
    public void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) {

    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft) {

    }

    @Override
    public void setFavorToken(int nFavTokens) {

    }

    @Override
    public void showCommand(List<Commands> commands) {

    }

    @Override
    public void addOnOwnWp(SetUpInformationUnit unit) {

    }

    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit) {

    }

    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {

    }

    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {

    }

    @Override
    public void addOnDraft(SetUpInformationUnit info) {

    }

    @Override
    public void removeOnDraft(SetUpInformationUnit info) {

    }

    @Override
    public void addOnRoundTrack(SetUpInformationUnit info) {

    }

    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info) {

    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken) {

    }

    @Override
    public void showNotice(String notice) {

    }

    @Override
    public IFromClientToServer getServer() {
        return null;
    }

    @Override
    public void updateToolCost(int idSlot, int cost) {

    }

    @Override
    public void showDie(SetUpInformationUnit informationUnit) {

    }

    @Override
    public void showRank(String[] playerNames, int[] scores) {

    }

    @Override
    public void forceLogOut() {

    }

    public LoginIpAddrTypeConnGUI getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginIpAddrTypeConnGUI loginManager) {
        this.loginManager = loginManager;
    }

    public Parent getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(Parent listPlayers) {
        this.listPlayers = listPlayers;
    }
}

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
import it.polimi.ingsw.view.Bank;
import it.polimi.ingsw.view.IViewMaster;
import it.polimi.ingsw.view.gui.gamewindows.CommonBoardWindow;
import it.polimi.ingsw.view.gui.gamewindows.GUIDefaultMatchManager;
import it.polimi.ingsw.view.gui.loginwindows.LoginIpAddrTypeConnGUI;
import it.polimi.ingsw.view.gui.loginwindows.LoginUsernameGameModeGUI;
import it.polimi.ingsw.view.gui.loginwindows.ShowPlayersGUI;
import it.polimi.ingsw.view.gui.setupwindows.ChooseWpGUI;
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

    private GUICommunicationManager manager;

    private Bank bank;

    private CommonBoardWindow commonWindow;

    private ParentWindow current;

    private DieFactory dieFactory;




    public GUIView() {
        bank = new Bank();
        manager = new GUICommunicationManager();
        listPlayers = new ShowPlayersGUI();
        chooseWpGUI = new ChooseWpGUI(manager);
        playersData = new PlayersData();
        commonWindow = new CommonBoardWindow(manager);

        chooseWpGUI.setPlayersData(this.playersData);
        commonWindow.setData(this.playersData);
        dieFactory = new DieFactory();
    }

    /**
     * This method create the bank, create the default manager and the tool card manager and populate its maps.
     */
    private void configureBank (){
        GUIDefaultMatchManager matchManager = new GUIDefaultMatchManager(this);
        bank.setDefaultMatchManager(matchManager);
        matchManager.setServer(this.server);
        matchManager.setPlayersData(this.playersData);
        GUIToolCardManager toolManager = new GUIToolCardManager(this);
        toolManager.setServer(this.server);
        toolManager.setPlayersData(this.playersData);
        bank.setToolCardManager(toolManager);
        bank.populateBank();
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

                configureBank();

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
            this.current = chooseWpGUI;
            this.playersData.setIdPrivateCard(idPrivateObj);
            GUIMain.setRoot(this.chooseWpGUI);
            this.chooseWpGUI.assignPrivateObjectiveCard(idPrivateObj);
            GUIMain.centerScreen();
        });
    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
       Platform.runLater(() -> {
           for(int i=0; i<listWp.size();i++) {
               WpGui wp = new WpGui();
               wp.constructMap(listWp.get(i));
               this.chooseWpGUI.getMaps().add(wp);
           }
           this.chooseWpGUI.formatMapsContainer();
       });

    }



    @Override
    public void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) {
        Platform.runLater(() -> {
            commonWindow.formatSecondContainer();
            commonWindow.setPublicImages(idPubObj);
            commonWindow.setToolImages(idTool);
            commonWindow.setRoundTrack();
            this.current = commonWindow;
            this.current.addHandlers();
            GUIMain.setRoot(current);
            GUIMain.centerScreen();
        });

    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft) {
        Platform.runLater(() ->
            this.commonWindow.getDraftPoolGUI().formatDraftPool(draft));
    }

    @Override
    public void setFavorToken(int nFavTokens) {
        Platform.runLater(() -> {
            this.playersData.setNumFavTok(nFavTokens);
            this.commonWindow.setFavorTokens();

        });

    }

    @Override
    public void showCommand(List<Commands> commands) {
        Platform.runLater(() -> {
            Map<String, Runnable> functions = this.bank.getCommandMap(commands);
            this.manager.setFunctions(functions);
            this.current.addHandlers();
        });
    }

    @Override
    public void addOnOwnWp(SetUpInformationUnit unit) {
        Platform.runLater(() -> {
            DieGUI die = this.dieFactory.getsDieGUI(unit);
            this.commonWindow.getData().getPersonalWp().addOnThisWp(die, unit.getDestinationIndex());
        });

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
        Platform.runLater(() -> {
            DraftPoolGUI draft = this.commonWindow.getDraftPoolGUI();
            draft.getChildren().remove(info.getSourceIndex());
            draft.reFormatDraft();
        });
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
        Platform.runLater( () -> {
            if(this.current != null)
                this.current.showMessage(notice);
        });


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

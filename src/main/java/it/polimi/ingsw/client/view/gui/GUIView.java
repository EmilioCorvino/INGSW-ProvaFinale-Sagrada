package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.view.Bank;
import it.polimi.ingsw.client.view.IViewMaster;
import it.polimi.ingsw.client.view.gui.gamewindows.*;
import it.polimi.ingsw.client.view.gui.loginwindows.LoginIpAddrTypeConnGUI;
import it.polimi.ingsw.client.view.gui.loginwindows.LoginUsernameGameModeGUI;
import it.polimi.ingsw.client.view.gui.loginwindows.ShowPlayersGUI;
import it.polimi.ingsw.client.view.gui.setupwindows.ChooseWpGUI;
import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.network.IFromClientToServer;
import it.polimi.ingsw.common.network.rmi.RmiFromClientToServer;
import it.polimi.ingsw.common.network.socket.SocketFromClientToServer;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.common.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.common.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.common.utils.exceptions.UserNameAlreadyTakenException;
import javafx.application.Platform;
import javafx.scene.Parent;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * This class represents the mean through the server communicates with the GUI and consequently with the player.
 */
public class GUIView implements IViewMaster {

    /**
     * The network interface for the connection.
     */
    private IFromClientToServer server;

    /**
     * The first window of the game.
     */
    private LoginIpAddrTypeConnGUI loginManager;

    /**
     * This is the list of the connected players.
     */
    private Parent listPlayers;

    /**
     * This window allows the player to choose among four present window pattern card.
     */
    private ChooseWpGUI chooseWpGUI;

    /**
     * This represent a general object containing the most important data of a single player.
     */
    private PlayersData playersData;

    /**
     * This represents the mean to let the GUI communicate with the server and the players.
     */
    private GUICommunicationManager manager;

    /**
     * This object represents alla the possibile method the GUI can invoke to send the information to the server.
     */
    private Bank bank;

    /**
     * This is the common board window of the GUI.
     */
    private CommonBoardWindow commonWindow;

    /**
     * This represents the current window of the game.
     */
    private ParentWindow current;

    /**
     * This object is responsible for the construction of the dice during the game.
     */
    private DieFactory dieFactory;

    /**
     * This is the final window of the game that shows the rank for each player.
     */
    private RankWindow rankWindow;


    public GUIView() {
        bank = new Bank();
        manager = new GUICommunicationManager();
        listPlayers = new ShowPlayersGUI();
        chooseWpGUI = new ChooseWpGUI(manager);
        playersData = new PlayersData();
        commonWindow = new CommonBoardWindow(manager);
        rankWindow = new RankWindow(this.manager);

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
                    SagradaLogger.log(Level.SEVERE, "Connection broken during register");
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
    }


    /**
     * This methods allows the representation of all the current player connected to the game.
     * @param players the players to show.
     */
    @Override
    public void showRoom(List<String> players) {
       Platform.runLater(() -> {
           GUIMain.setRoot(this.listPlayers);
           ((ShowPlayersGUI) this.listPlayers).showPlayers(players);
       });
    }

    /**
     * This method allows the representation of the personal private objective card assigned to a single player.
     * @param idPrivateObj the id of the private objective card assigned by the server.
     */
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

    /**
     * This method constructs the four maps among which the player has to choose.
     * @param listWp the list of map to represent.
     */
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

    /**
     * This method constructs the common board window of the GUI.
     * @param players the other players with their relative personal map.
     * @param idPubObj the ids of the public objective cards extracted for the game.
     * @param idTool the ids of the tool cards extracted for the game.
     */
    @Override
    public void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) {
        Platform.runLater(() -> {
            commonWindow.formatSecondContainer();
            commonWindow.setPublicImages(idPubObj);
            commonWindow.setToolImages(idTool);
            commonWindow.setRoundTrack();
            this.current = commonWindow;
            this.commonWindow.getDraftPoolGUI().cellAsSource();
            this.playersData.getPersonalWp().cellMapAsDestinationHandler();
            this.playersData.constructOtherPlayerMap(players);

            this.current.addHandlers();
            GUIMain.setRoot(current);
            GUIMain.centerScreen();
        });
    }

    /**
     * This method is responsible for update all the dice of the draft pool.
     * @param draft the dice to represent.
     */
    @Override
    public void setDraft(List<SetUpInformationUnit> draft) {
        Platform.runLater(() -> {
            this.commonWindow.resetIsAlreadyUsedFlag();
            this.commonWindow.getDraftPoolGUI().formatDraftPool(draft);
            this.commonWindow.getDraftPoolGUI().cellAsSource();
            this.commonWindow.manageToolButtons(false);
        });
    }

    /**
     * This method is responsible for setting the favor token for the player.
     * @param nFavTokens the number of favor tokens to set.
     */
    @Override
    public void setFavorToken(int nFavTokens) {
        Platform.runLater(() -> {
            this.playersData.setNumFavTok(nFavTokens);
            this.commonWindow.setFavorTokens();
        });
    }

    /**
     * This method restores the personal and the other window pattern card of the players.
     * @param diceToRestore the dice to add to the map.
     */
    @Override
    public void setRestoredWindowPatternCards(Map<String, List<SetUpInformationUnit>> diceToRestore) {
        Platform.runLater(() -> {
            diceToRestore.entrySet().forEach(entry -> {
                this.playersData.getOtherMaps().clear();
                WpGui wpToRestore = new WpGui();
                SimplifiedWindowPatternCard baseMap = this.playersData.getMapsCopy().get(entry.getKey());
                wpToRestore.constructMap(baseMap);
                wpToRestore.setName(entry.getKey());
                List<SetUpInformationUnit> list = entry.getValue();
                list.forEach(elem -> {
                    DieGUI dieToAdd = this.dieFactory.getsDieGUI(elem);
                    wpToRestore.addOnThisWp(dieToAdd, elem.getDestinationIndex());
                });

                if(entry.getKey().equals(this.playersData.getUsername()))
                    this.playersData.setPersonalWp(wpToRestore);
                else
                    this.playersData.getOtherMaps().add(wpToRestore);
            });
        });
    }

    /**
     * This method is responsible for updating all the available commands the user can execute at each time.
     * @param commands the list of available commands.
     */
    @Override
    public void showCommand(List<Commands> commands) {
        Platform.runLater(() -> {
            Map<String, Runnable> functions = this.bank.getCommandMap(commands);
            this.manager.setFunctions(functions);
            if(this.manager.isCommandContained("reconnect"));
                this.current.getReconnect().setVisible(true);
            this.current.addHandlers();
        });
    }

    /**
     * This method add a die on the personal map of the player.
     * @param unit the die to add.
     */
    @Override
    public void addOnOwnWp(SetUpInformationUnit unit) {
        Platform.runLater(() -> {
            this.commonWindow.resetIsAlreadyUsedFlag();
            this.commonWindow.manageToolButtons(false);
            DieGUI die = this.dieFactory.getsDieGUI(unit);
            this.commonWindow.getData().getPersonalWp().setWpCellClicked(false);
            this.commonWindow.getData().getPersonalWp().getCellsClicked().clear();
            this.commonWindow.getData().getPersonalWp().addOnThisWp(die, unit.getDestinationIndex());
        });
    }

    /**
     * This methos removes a die form the personal map of a player.
     * @param unit the die to remove.
     */
    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit) {
        Platform.runLater(() -> {
            System.out.println("Sto per rimuovere l'elemento... " + unit.getSourceIndex());
            this.commonWindow.getData().getPersonalWp().removeFromThisWp(unit.getSourceIndex());
        });
    }

    /**
     * This method adds a die to a map of another player.
     * @param userName the name of the player whose map has to be modified.
     * @param infoUnit the info to use.
     */
    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {
        Platform.runLater(() -> {
            List<WpGui> list = this.playersData.getOtherMaps();
            list.forEach( map -> {
                if(map.getName().equals(userName)) {
                    DieGUI die = this.dieFactory.getsDieGUI(infoUnit);
                    map.addOnThisWp(die, infoUnit.getDestinationIndex());
                }
            });
        });
    }

    /**
     * This method removes a die from a window pattern card of another player.
     * @param userName the name of the player whose map has to be modified.
     * @param infoUnit the info to use to remove the die.
     */
    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) {
        Platform.runLater(() -> {
            List<WpGui> list = this.playersData.getOtherMaps();
            list.forEach( map -> {
                if(map.getName().equals(userName))
                    map.removeFromThisWp(infoUnit.getSourceIndex());
            });
        });
    }

    /**
     * This method adds a single die to the draft pool.
     * @param info the die to add.
     */
    @Override
    public void addOnDraft(SetUpInformationUnit info) {
        Platform.runLater(() -> this.commonWindow.getDraftPoolGUI().addOneDie(info));
    }

    /**
     * This method removes a die from the draf pool.
     * @param info the die to remove.
     */
    @Override
    public void removeOnDraft(SetUpInformationUnit info) {
        Platform.runLater(() -> {
            DraftPoolGUI draft = this.commonWindow.getDraftPoolGUI();
            draft.setDraftCellChosen(false);
            draft.getChildren().remove(info.getSourceIndex());
            draft.reFormatDraft();
        });
    }

    /**
     * This method adds a die to the round track.
     * @param info the die to add.
     */
    @Override
    public void addOnRoundTrack(SetUpInformationUnit info) {
        Platform.runLater(() -> this.commonWindow.getRoundTrack().addDieToRound(info));
    }

    /**
     * This method removes a die from the round track.
     * @param info the die to remove.
     */
    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info) {
        Platform.runLater(() -> this.commonWindow.getRoundTrack().removeOneDie(info));
    }

    /**
     * This method updates the favor tokens of the player when a tool card is used.
     * @param nFavorToken the number of favor tokens to set.
     */
    @Override
    public void updateFavTokenPlayer(int nFavorToken) {
        Platform.runLater(() -> this.commonWindow.updateFavorTokens(nFavorToken));
    }

    /**
     * This method is responsible for showing all the messages from the server.
     * @param notice the message to show.
     */
    @Override
    public void showNotice(String notice) {
        Platform.runLater( () -> {
            String newMex = notice;
            if(notice.contains(" Digita 'comandi' per visualizzare i comandi ancora disponibili."))
                newMex = notice.replace(" Digita 'comandi' per visualizzare i comandi ancora disponibili.", "");
            if(this.current != null) {
                this.current.showMessage(newMex);
                if(notice.contains("HAI VINTO PER ABBANDONO!"))
                    System.out.println("hai vinto per abbandono");
            }
        });
    }

    @Override
    public IFromClientToServer getServer() {
        return null;
    }

    /**
     * This method updates the cost of the tool card when it is used.
     * @param idSlot the slot of the tool.
     * @param cost the cost to set.
     */
    @Override
    public void updateToolCost(int idSlot, int cost) {
        Platform.runLater(() -> this.commonWindow.updateToolCost(idSlot, cost));
    }

    /**
     * This method shows a die extracted in particular situations.
     * @param informationUnit the extracted die.
     */
    @Override
    public void showDie(SetUpInformationUnit informationUnit) {
        Platform.runLater(() -> {
            this.playersData.setSetUpInformationUnit(informationUnit);
            this.commonWindow.showDraftedDie(informationUnit);
        });
    }

    @Override
    public void setServer(IFromClientToServer server) {
        this.server = server;
    }

    /**
     * This method shows the rank for each player.
     * @param playerNames the names of the players.
     * @param scores the scores of the player.
     */
    @Override
    public void showRank(String[] playerNames, int[] scores) {
        Platform.runLater(() -> {
            for(int i=0; i<playerNames.length; i++)
                this.rankWindow.updateNames(playerNames[i], scores[i]);
            this.rankWindow.addHandlers();
            this.current = rankWindow;
            GUIMain.setRoot(current);
            GUIMain.centerScreen();
        });
    }

    /**
     * This method executes the logout for the player.
     */
    @Override
    public void forceLogOut() {
        Platform.runLater( () -> {
            this.manager.communicateMessage("Logout effettuato");
            System.exit(0);
        });
    }

    public void setLoginManager(LoginIpAddrTypeConnGUI loginManager) {
        this.loginManager = loginManager;
    }

    public CommonBoardWindow getCommonWindow() {
        return commonWindow;
    }
}
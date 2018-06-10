package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.die.CommonBoardView;
import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.PlayerView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;
import it.polimi.ingsw.view.cli.stateManagers.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CliView extends AViewMaster{

    /**
     * This attribute is set true when is the turn of this client else is set false.
     */
    private boolean isMyTurn;

    /**
     * The object that create the map functions, taking info from a map of possible function.
     */
    private Bank bank;

    /**
     * The map populate a ran time, with all the possible command usable in a stage of the game.
     */
    private Map<String, Runnable> functions;

    /**
     * The player connected with the client, with the info of his private objective card, and the number of his favor toke.
     * (No his wp, that information his inside the common board to this player in the players list)
     */
    private PlayerView player;

    /**
     * The structure that contain the draft pool the cards (public objective and tool).
     */
    private CommonBoardView commonBoard;

    /**
     * Input Output Manager.
     */
    private InputOutputManager inputOutputManager;

    /**
     * The network interface for the connection
     */
    private IFromClientToServer server;

    /**
     *The manager of the connection and register state
     */
    private LoginCli loginState;

    /**
     * The manager of the game set up.
     */
    private SetUpGameCli initializationState;

    /**
     * The manager of the game play.
     */
    private GamePlayCli gamePlaySate;

    /**
     * The manager of the game play.
     */
    private EndGameCli endGameState;

    public CliView(){
        bank = new Bank(this);
        player = new PlayerView();
        commonBoard = new CommonBoardView();
        inputOutputManager = new InputOutputManager();
        loginState = new LoginCli(inputOutputManager);
        initializationState = new SetUpGameCli(inputOutputManager);
        gamePlaySate = new GamePlayCli(inputOutputManager);
        endGameState = new EndGameCli(inputOutputManager);

        new Thread(new ScannerThread(this::analyzeStringInput, inputOutputManager)).start();
    }

//----------------------------------------------------------
//                      LOGIN STATE
//----------------------------------------------------------
    /**
     * This method create the connection and logs the player
     * @param viewMaster Is the view connected to the net.
     */
    @Override
    public void createConnection(AViewMaster viewMaster) {
        boolean userNameOk = false;
        boolean ipOk = false;

        while(!ipOk){
            try{
                String ipAddress = loginState.getIp();
                this.server = loginState.chooseNetworkInterface(ipAddress, viewMaster);
                ipOk = true;
            }catch (BrokenConnectionException e){
                inputOutputManager.print("Indirizzo IP non corretto.");
            }
        }

        inputOutputManager.print("\nConnessione stabilita.\nProcedere con il register.");

        while(!userNameOk){
            try {
                this.player.setUserName(loginState.getUserName());
                this.server.login(loginState.getGameMode(), player.getUserName());
                userNameOk = true;
            } catch (UserNameAlreadyTakenException e) {
                inputOutputManager.print("Username già in uso!");
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection broken during register", e);
            } catch (TooManyUsersException e) {
                inputOutputManager.print("Partita piena, numero massimo di giocatori raggiunto\nArrivederci.");
            }
        }
    }

    /**
     *This method print the name of all the players connected.
     * @param players: list of username of players connected.
     */
    @Override
    public void showRoom(List<String> players) {
        this.loginState.showRoom(players);
    }


//----------------------------------------------------------
//                  INITIALIZATION STATE
//----------------------------------------------------------

    /**
     * This method set the private objective of the player and than show it to him.
     * @param idPrivateObj: the id of the private objective drown.
     */
    @Override
    public void showPrivateObjective(int idPrivateObj){
        this.initializationState.createPrivateObjCard(idPrivateObj, this.player);
        inputOutputManager.print("Il tuo obiettivo privato e': " + this.player.getPrivateObjCard());
    }

    /**
     *This method print for maps to the user and return to the server the id of the map chosen colling windowPatternRequest().
     * @param listWp: The list of maps need to be choose.
     */
    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
        this.initializationState.showMapsToChoose(listWp);
    }

    @Override
    public void choseWpId() {
        try {
            server.windowPatternCardRequest(initializationState.getIdChosen());
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during map id choose.");
        }

    }

    /**
     * This method will set the common board with all the common information for each player.
     * @param players: A map witch contains a simplified wp for each userName that identify a player.
     * @param idPubObj: The ids of all the public objective cards drown by the controller.
     * @param idTool: The ids of all the tool cards drown by the controller.
     */
    @Override
    public void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool){

        for (Map.Entry<String, SimplifiedWindowPatternCard> ply : players.entrySet()) {
            PlayerView p = new PlayerView();
            p.setUserName(ply.getKey());
            p.setWp(new WindowPatternCardView(ply.getValue()));
            this.commonBoard.getPlayers().add(p);
        }

        initializationState.createPubObjCards(idPubObj, commonBoard.getPublicObjectiveCards());
        //initializationState.createToolCards(idTool, commonBoard.getToolCards());

        this.getPlayerConnectedFromCommonBoard().getWp().printWp();
    }

    /**
     * This method will populate the draft pool in each round
     * @param draft: the list of dice contain in the draft Pool.
     */
    @Override
    public void setDraft(List<SetUpInformationUnit> draft){
        this.commonBoard.setDraftPool(new DieDraftPoolView(draft));
        this.commonBoard.getDraftPool().printDraftPool();
    }

    /**
     * This method give at the player connected to this client the information of his private objective card and the number of his favor tokens.
     * @param nFavTokens : the number of favor tokens.
     */
    @Override
    public void setFavorToken(int nFavTokens){
        this.player.setFavorToken(nFavTokens);
    }

//----------------------------------------------------------
//                  GAME PLAY STATE
//----------------------------------------------------------

    /**
     * This method populate the function map when the controller give the command list
     * @param commands: the list of commands available.
     */
    @Override
    public void showCommand(List<Commands> commands) {
        functions = bank.getCommandMap(commands);
        inputOutputManager.print("Camandi disponibili: ");
        functions.forEach((k,v) -> inputOutputManager.print(k));
    }

    /**
     * This method update the wp of the player connected and print it.
     * @param unit : information for the update, index of matrix and die that needs to be place.
     */
    @Override
    public void updateOwnWp(SetUpInformationUnit unit){
        WindowPatternCardView wp = this.getPlayerConnectedFromCommonBoard().getWp();

        gamePlaySate.updateWp(wp, unit);
        wp.printWp();


    }

    /**
     * This method update the wp of all player.
     * @param userName : The userName of the player with the wp modified
     * @param infoUnit : The info of modification of the wp.
     */
    @Override
    public void updateOtherPlayerWp(String userName, SetUpInformationUnit infoUnit){

        for (PlayerView ply : this.commonBoard.getPlayers())
            if (ply.getUserName().equals(userName)) {
                gamePlaySate.updateWp(ply.getWp(), infoUnit);
                return;
            }

    }

    /**
     * This method remove a die fr<om the draft in a specified index.
     * @param info: the containers of the index information.
     */
    @Override
    public void updateDraft(InformationUnit info){
        this.commonBoard.getDraftPool().getDice().remove(info.getIndex());
    }

    /**
     * This method update the number of favor token assigned to a player
     * @param nFavorToken : number of favor token remain.
     */
    @Override
    public void updateFavTokenPlayer(int nFavorToken){
        this.player.setFavorToken(nFavorToken);
    }

    public void updateFavTokenTool(int idSlot, int nFavToken){

    }

    // DA CANCELLARE
    /**
     * This method update the wp of a specified player.
     * @param username: Username of player that need the update.
     * @param unit: The set of information needed to place a die in the wp.
     */
    @Deprecated
    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit unit) {

        for (PlayerView p : commonBoard.getPlayers())
            if(p.getUserName().equals(username))
                gamePlaySate.updateWp(p.getWp(), unit);


        if (player.getUserName().equals(username)) {
            inputOutputManager.print("Dado piazzato!");
            this.getPlayerConnectedFromCommonBoard().getWp().printWp();
        }
    }

    @Override
    public void showNotice(String notice){
        inputOutputManager.print(notice);
    }

//----------------------------------------------------------
//                  END GAME STATE
//----------------------------------------------------------



//----------------------------------------------------------
//                  CLIENT NOT SERVED
//----------------------------------------------------------


    @Override
    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
        if(!isMyTurn()) {
            OutOfTurnManager manager = new OutOfTurnManager(this);
            manager.run();
        }
    }

//----------------------------------------------------------
//                  GENERAL METHODS
//----------------------------------------------------------
    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    GamePlayCli getGamePlaySate() {
        return gamePlaySate;
    }

    public CommonBoardView getCommonBoard() {
        return commonBoard;
    }

    public PlayerView getPlayer() {
        return player;
    }

    public InputOutputManager getInputOutputManager() {
        return inputOutputManager;
    }

    /**
     * This method take the connected player from the list of players in the common board.
     * @return: the player connected.
     */
    PlayerView getPlayerConnectedFromCommonBoard(){
        PlayerView ply = new PlayerView();

        for (PlayerView p: this.commonBoard.getPlayers())
            if (p.getUserName().equals(this.player.getUserName()))
                ply = p;

        return ply;
    }

    private void analyzeStringInput(String s){
        functions.get(s).run();
    }
}

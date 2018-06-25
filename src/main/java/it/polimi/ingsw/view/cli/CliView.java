package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.MatchAlreadyStartedException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.IViewMaster;
import it.polimi.ingsw.view.cli.boardelements.CommonBoardView;
import it.polimi.ingsw.view.cli.boardelements.PlayerView;
import it.polimi.ingsw.view.Bank;
import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;
import it.polimi.ingsw.view.cli.generalmanagers.CliDefaultMatchManager;
import it.polimi.ingsw.view.cli.generalmanagers.CliToolCardCardManager;
import it.polimi.ingsw.view.cli.generalmanagers.InputOutputManager;
import it.polimi.ingsw.view.cli.generalmanagers.ScannerThread;
import it.polimi.ingsw.view.cli.statemanagers.EndGameManager;
import it.polimi.ingsw.view.cli.statemanagers.GamePlayManager;
import it.polimi.ingsw.view.cli.statemanagers.LoginManager;
import it.polimi.ingsw.view.cli.statemanagers.SetUpManager;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CliView implements IViewMaster {

    /**
     * The object that create the map functions, taking info from a map of possible function.
     */
    private Bank bank;

    /**
     * The map populate a run time, with all the possible command usable in a stage of the game.
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
    private LoginManager loginManager;

    /**
     * The manager of the game set up.
     */
    private SetUpManager setUpManager;

    /**
     * The manager of the game play.
     */
    private GamePlayManager gamePlayManager;

    /**
     * The manager of the game play.
     */
    private EndGameManager endGameManager;

    /**
     * The scanner of commands from the user.
     */
    private ScannerThread scannerThread;

    /**
     * The attribute used to start the scanner thread after the first showCommand.
     */
    private boolean isNotTheFirstTime;

    public CliView(InputOutputManager inputOutputManager) {
        player = new PlayerView();
        this.inputOutputManager = inputOutputManager;
        commonBoard = new CommonBoardView();
        loginManager = new LoginManager(inputOutputManager);
        setUpManager = new SetUpManager(inputOutputManager);
        gamePlayManager = new GamePlayManager(inputOutputManager);
        endGameManager = new EndGameManager(inputOutputManager);
        scannerThread = new ScannerThread(this::analyzeStringInput, inputOutputManager);
    }

//----------------------------------------------------------
//                      LOGIN STATE
//----------------------------------------------------------
    /**
     * This method create the connection and logs the player
     * @param viewMaster Is the view connected to the net.
     */
    @Override
    public void createConnection(IViewMaster viewMaster) {
        boolean userNameOk = false;
        boolean ipOk = false;

        while(!ipOk){
            try{
                String ipAddress = loginManager.getIp();
                this.server = loginManager.chooseNetworkInterface(ipAddress, viewMaster);
                ipOk = true;
            }catch (BrokenConnectionException e){
                inputOutputManager.print("Indirizzo IP non corretto.");
            }
        }

        this.setBank();

        inputOutputManager.print("\nConnessione stabilita.\nProcedere con la registrazione.");

        while(!userNameOk){
            try {
                this.player.setUserName(loginManager.getUserName());
                this.server.login(loginManager.getGameMode(), player.getUserName());
                userNameOk = true;
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection broken during register", e);
            } catch (UserNameAlreadyTakenException e) {
                inputOutputManager.print("\nUsername già in uso!");
            } catch (TooManyUsersException e) {
                inputOutputManager.print("\nPartita piena, numero massimo di giocatori raggiunto!\nArrivederci.");
                System.exit(0);
            } catch (MatchAlreadyStartedException e) {
                inputOutputManager.print("\nSpiacente la partita e' gia' iniziata!\nArrivederci.");
                System.exit(0);
            }
        }

    }

    /**
     *This method print the name of all the players connected.
     * @param players: list of username of players connected.
     */
    @Override
    public void showRoom(List<String> players) {
        this.loginManager.showRoom(players);
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
        this.setUpManager.createPrivateObjCard(idPrivateObj, this.player);
        inputOutputManager.print(this.player.privateObjToString());
    }

    /**
     *This method print for maps to the user and return to the server the id of the map chosen colling windowPatternRequest().
     * @param listWp: The list of maps need to be choose.
     */
    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
        this.setUpManager.showMapsToChoose(listWp);
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
            if(!ply.getKey().equals(this.player.getUserName())) {
                PlayerView p = new PlayerView();
                p.setUserName(ply.getKey());
                p.setWp(new WindowPatternCardView(ply.getValue()));
                this.commonBoard.getPlayers().add(p);
            }
            else
                this.player.setWp(new WindowPatternCardView(ply.getValue()));
        }

        setUpManager.createPubObjCards(idPubObj, commonBoard.getPublicObjectiveCards());
        setUpManager.createToolCards(idTool, commonBoard.getToolCardViews());

        inputOutputManager.print("\n------------------------------" +
                                 "\n      PLANCIA DI GIOCO: ");
        inputOutputManager.print(this.getPlayer().privateObjToString());
        inputOutputManager.print(commonBoard.pubObjToString());
        inputOutputManager.print(commonBoard.toolCardToString());
        inputOutputManager.print(this.player.getWp().wpToString());
    }

    /**
     * This method will populate the draft pool in each round
     * @param draft: the list of dice contain in the draft Pool.
     */
    @Override
    public void setDraft(List<SetUpInformationUnit> draft){
        this.commonBoard.setDraftPool(new DieDraftPoolView(draft));
        inputOutputManager.print(commonBoard.getDraftPool().diceDraftToString());
    }

    /**
     * This method give at the player connected to this client the information of his private objective card and the number of his favor tokens.
     * @param nFavTokens : the number of favor tokens.
     */
    @Override
    public void setFavorToken(int nFavTokens){
        this.player.setFavorToken(nFavTokens);
        inputOutputManager.print(this.player.favTokensToString());
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
        printCommands();
        if (!isNotTheFirstTime) {
            isNotTheFirstTime = true;
            scannerThread.start();
        }
    }

    /**
     * This method addDie the wp of the player connected and print it.
     * @param unit : information for the addDie, index of matrix and die that needs to be place.
     */
    @Override
    public void addOnOwnWp(SetUpInformationUnit unit){
        WindowPatternCardView wp = this.player.getWp();

        gamePlayManager.addOnWp(wp, unit);
        inputOutputManager.print(wp.wpToString());

    }

    /**
     * This method remove a die from a map in a specified index.
     * @param unit: the container with all the info needed for the remove.
     */
    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit){
        WindowPatternCardView wp = this.player.getWp();

        gamePlayManager.removeOnWp(wp, unit);
        inputOutputManager.print(wp.wpToString());
    }

    /**
     * This method addDie the die in all the wp of all player.
     * @param userName : The userName of the player with the wp modified
     * @param infoUnit : The info of modification of the wp.
     */
    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit){
        for (PlayerView ply : this.commonBoard.getPlayers())
            if(ply.getUserName().equals(userName)) {
                gamePlayManager.addOnWp(ply.getWp(), infoUnit);
                inputOutputManager.print("\nE' stata mofificata la mappa di " + userName + " :");
                inputOutputManager.print(ply.getWp().wpToString());
            }
    }

    /**
     * This method remove the die in all the wp of all player.
     * @param userName : The userName of the player with the wp modified
     * @param infoUnit : The info of modification of the wp.
     */
    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit){
        for (PlayerView ply : this.commonBoard.getPlayers())
            if(ply.getUserName().equals(userName))
                gamePlayManager.removeOnWp(ply.getWp(), infoUnit);
    }

    /**
     * This method addDie a die from the draft in a specified index.
     * @param info : the containers of the die info.
     */
    @Override
    public void addOnDraft(SetUpInformationUnit info){
        gamePlayManager.addOnDraft(this.commonBoard.getDraftPool(), info);
    }

    /**
     * This method remove a die from the draft in a specified index.
     * @param info : the containers of the index information.
     */
    @Override
    public void removeOnDraft(SetUpInformationUnit info){
        gamePlayManager.removeOnDraft(this.commonBoard.getDraftPool(), info);
        inputOutputManager.print(this.commonBoard.getDraftPool().diceDraftToString());
    }

    /**
     * This method addDie a die on the round track
     * @param info: The containers of the info.
     */
    @Override
    public void addOnRoundTrack(SetUpInformationUnit info){
        gamePlayManager.addOnRoundTrack(this.commonBoard.getRoundTrack(), info);
    }

    /**
     * This method remove a die on the round track
     * @param info: the container of the info of removing.
     */
    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info){
        gamePlayManager.removeOnRoundTrack(this.commonBoard.getRoundTrack(), info);
    }

    /**
     * This method addDie the number of favor token assigned to a player
     * @param nFavorToken : number of favor token remain.
     */
    @Override
    public void updateFavTokenPlayer(int nFavorToken){
        this.player.setFavorToken(nFavorToken);
    }

    /**
     * This method addDie the cost of use for a specified tool, it is used after the first use of a tool.
     * @param idSlot: It is the id of the tool updated
     * @param cost: The new cost of the tool.
     */
    @Override
    public void updateToolCost(int idSlot, int cost){
        this.commonBoard.getToolCardViews().get(idSlot).setCost(cost);
    }

    /**
     * This method is used in particular tool (6, 11) to show the new die after the extraction.
     * @param informationUnit: the container of all the info of the new die extracted.
     */
    @Override
    public void showDie(SetUpInformationUnit informationUnit){
        this.gamePlayManager.showDie(informationUnit);

    }

//----------------------------------------------------------
//                  END GAME STATE
//----------------------------------------------------------

    /**
     * This method print the rank of the match.
     * @param players: the players that played the match.
     * @param score: the score of each ( the order of both list is the same, first element of score is associated to first element of the list of player).
     */
    @Override
    public void showRank(String[] players, int[] score){
        endGameManager.showRank(players, score, this.player);
    }

    /**
     * This method is user by the server to force the logout in case of the user doesn't choose an option after the end of the game.
     */
    @Override
    public void forceLogOut(){
        this.scannerThread.stopExecution();
        this.inputOutputManager.print("IL SERVER TI HA DISCONNESSO");
        System.exit(0);
    }

//----------------------------------------------------------
//                  GENERAL METHODS
//----------------------------------------------------------
    /**
     * This method notice a message to the user.
     * @param notice: the message that need to be printed.
     */
    @Override
    public void showNotice(String notice){
        inputOutputManager.print(notice);
    }

    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public SetUpManager getSetUpManager() {
        return setUpManager;
    }

    public Map<String, Runnable> getFunctions() {
        return functions;
    }

    public CommonBoardView getCommonBoard() {
        return commonBoard;
    }

    public PlayerView getPlayer() {
        return player;
    }

    InputOutputManager getInputOutputManager() {
        return inputOutputManager;
    }

    public ScannerThread getScannerThread() {
        return scannerThread;
    }

    /**
     * This method analyze the input given by the user and run the corresponding method in the map.
     * @param s: the key related of the method in the map.
     */
    private void analyzeStringInput(String s){
        String filteredString = stringConverter(s);

        if(functions.keySet().contains(filteredString))
            functions.get(filteredString).run();
        else
            inputOutputManager.print("Comando non disponibile");
    }

    /**
     * This method check if the command is contained in the map ignoring the case.
     * @param s: It is the command.
     * @return: The corresponding string of the key set if the command is equals (ignoring the case), else input sting.
     */
    private String stringConverter(String s){

        for (String string : functions.keySet())
            if(s.equalsIgnoreCase(string))
                return string;
        return s;
    }

    /**
     * This method print all the command contained in the function map.
     */
    public void printCommands(){
        if(functions.keySet().size() > 1) {
            inputOutputManager.print("\nCamandi disponibili: ");
            for (String s : functions.keySet())
                inputOutputManager.print("\t- " + s);
        }else
            inputOutputManager.print("Nessun comando disponibile, attendi.");
    }

    /**
     * This method create the bank, create the default manager and the tool card manager and populate its maps.
     */
    private void setBank(){
        bank = new Bank();
        bank.setDefaultMatchManager(new CliDefaultMatchManager(this));
        bank.setToolCardManager(new CliToolCardCardManager(this));
        bank.populateBank();
    }
}

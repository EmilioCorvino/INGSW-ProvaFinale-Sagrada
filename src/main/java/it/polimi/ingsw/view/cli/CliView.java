package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.TooManyUsersException;
import it.polimi.ingsw.utils.exceptions.UserNameAlreadyTakenException;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.boardElements.CommonBoardView;
import it.polimi.ingsw.view.cli.commands.Bank;
import it.polimi.ingsw.view.cli.die.DieDraftPoolView;
import it.polimi.ingsw.view.cli.boardElements.PlayerView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;
import it.polimi.ingsw.view.cli.generalManagers.InputOutputManager;
import it.polimi.ingsw.view.cli.generalManagers.ScannerThread;
import it.polimi.ingsw.view.cli.stateManagers.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CliView extends AViewMaster{

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

    public CliView(){
        bank = new Bank(this);
        player = new PlayerView();
        commonBoard = new CommonBoardView();
        inputOutputManager = new InputOutputManager();
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
    public void createConnection(AViewMaster viewMaster) {
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

        inputOutputManager.print("\nConnessione stabilita.\nProcedere con la registrazione.");

        while(!userNameOk){
            try {
                this.player.setUserName(loginManager.getUserName());
                this.server.login(loginManager.getGameMode(), player.getUserName());
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
        inputOutputManager.print("Il tuo obiettivo privato e': " + this.player.getPrivateObjCard());
        scannerThread.start();
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
     * This method send to the server the id of the wp chosen.
     */
    @Override
    public void choseWpId() {
        try {
            server.windowPatternCardRequest(setUpManager.getIdChosen());
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
        setUpManager.createToolCards(idTool, commonBoard.getToolCards());

        inputOutputManager.print("\nPLANCIA DI GIOCO: ");
        setUpManager.printPubObj(commonBoard.getPublicObjectiveCards());
        setUpManager.printTool(commonBoard.getToolCards());
        this.player.getWp().printWp();
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
        inputOutputManager.print("Il numero di segnalini favore e': "+nFavTokens);
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
    }

    /**
     * This method update the wp of the player connected and print it.
     * @param unit : information for the update, index of matrix and die that needs to be place.
     */
    @Override
    public void updateOwnWp(SetUpInformationUnit unit){
        WindowPatternCardView wp = this.player.getWp();

        gamePlayManager.updateWp(wp, unit);
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
                gamePlayManager.updateWp(ply.getWp(), infoUnit);

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

    /**
     * This method update the cost of use for a specified tool, it is used after the first use of a tool.
     * @param idSlot: It is the id of the tool updated
     * @param cost: The new cost of the tool.
     */
    @Override
    public void updateToolCost(int idSlot, int cost){
        this.commonBoard.getToolCards().get(idSlot).setCost(cost);
    }

    // DA CANCELLARE
    /**
     * This method update the wp of a specified player.
     * @param username: Username of player that need the update.
     * @param unit: The set of information needed to place a die in the wp.
     */
    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit unit) {

        for (PlayerView p : commonBoard.getPlayers())
            if(p.getUserName().equals(username))
                gamePlayManager.updateWp(p.getWp(), unit);


        if (player.getUserName().equals(username)) {
            inputOutputManager.print("Dado piazzato!");
            this.getPlayer().getWp().printWp();
        }
    }

    /**
     * This method notice a message to the user.
     * @param notice: the message that need to be printed.
     */
    @Override
    public void showNotice(String notice){
        inputOutputManager.print(notice);
    }

//----------------------------------------------------------
//                  END GAME STATE
//----------------------------------------------------------


//----------------------------------------------------------
//                  GENERAL METHODS
//----------------------------------------------------------
    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }

    public SetUpManager getSetUpManager() {
        return setUpManager;
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
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


    public void printCommands(){
        inputOutputManager.print("\nCamandi disponibili: ");
        functions.forEach((k,v) -> inputOutputManager.print("\t- "+k));
    }
}

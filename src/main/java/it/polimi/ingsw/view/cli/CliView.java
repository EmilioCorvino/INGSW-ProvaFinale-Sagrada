package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.simplified_view.InformationUnit;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
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
import it.polimi.ingsw.view.cli.stateManagers.EndGameCli;
import it.polimi.ingsw.view.cli.stateManagers.GamePlayCli;
import it.polimi.ingsw.view.cli.stateManagers.LoginCli;
import it.polimi.ingsw.view.cli.stateManagers.SetUpGameCli;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

public class CliView extends AViewMaster implements Runnable{

    /**
     * This attribute is set true when is the turn of this client else is set false.
     */
    private boolean isMyTurn;

    /**
     * The user name of the player connected with the client.
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
        player = new PlayerView();
        commonBoard = new CommonBoardView();
        inputOutputManager = new InputOutputManager();
        loginState = new LoginCli();
        initializationState = new SetUpGameCli();
        gamePlaySate = new GamePlayCli();
        endGameState = new EndGameCli();
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

    // DA CANCELLARE
    /**
     *This method take the info from the server to initialize the common board at the beginning of the match.
     * @param draftPool
     * @param wp :
     */
    @Override
    public void showCommonBoard(List<SetUpInformationUnit> draftPool, SimplifiedWindowPatternCard wp){
        this.player.setWp(new WindowPatternCardView(wp));
        DieDraftPoolView draft = new DieDraftPoolView(draftPool);

        this.commonBoard.setDraftPool(draft);
        this.commonBoard.getPlayers().add(player);
        initializationState.showCommonBoard(draft, player.getWp());
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
    }

    /**
     * This method will populate the draft pool in each round
     * @param draft: the list of dice contain in the draft Pool.
     */
    @Override
    public void setDraft(List<SetUpInformationUnit> draft){
        this.commonBoard.setDraftPool(new DieDraftPoolView(draft));
    }

    /**
     * This method give at the player connected to this client the information of his private objective card and the number of his favor tokens.
     * @param nFavTokens : the number of favor tokens.
     * @param idPrivateObj : the id of his private obj card.
     */
    @Override
    public void setPlayer(int nFavTokens, int idPrivateObj){

        this.player.setFavorToken(nFavTokens);
        this.initializationState.createPrivateObjCard(idPrivateObj, this.player);

    }

//----------------------------------------------------------
//                  GAME PLAY STATE
//----------------------------------------------------------
    /**
     *This method show the available commands to the user and allow him to chose one.
     * 1: make a placement move
     * 2: make a tool move
     * 3: show the wp of all players.
     * 4: show the public objective cards.
     * 5: show the tool cards
     * 6: show the private objective card.
     * 7: end the turn
     */

    @Override
    public void showCommand() {
        int command = gamePlaySate.showCommand();
        switch (command){
            case 1:     try{
                            server.defaultMoveRequest();
                        } catch (BrokenConnectionException e){
                            SagradaLogger.log(Level.SEVERE, "Connection broken during placement move",e);
                        }
                        break;
            /*
            case 2:     try{
                            server.toolMoveRequest();
                        } catch (BrokenConnectionException e){
                            SagradaLogger.log(Level.SEVERE, "Connection broken during tool move",e);
                        }
                        break;
                */

            case 3:     gamePlaySate.printAllWp(commonBoard.getPlayers(), this.player);
                        this.showCommand();
                        break;

            case 4:     gamePlaySate.printPubObj(commonBoard.getPublicObjectiveCards());
                        this.showCommand();
                        break;

            case 5:     gamePlaySate.printTool(commonBoard.getToolCards());
                        this.showCommand();
                        break;

            case 6:     gamePlaySate.printPrivateObj(this.player.getPrivateObjCard());
                        this.showCommand();
                        break;
                        /*
            case 7:     try{
                            server.endTurn();
                        } catch (BrokenConnectionException e) {
                            SagradaLogger.log(Level.SEVERE, "Connection broken during end game", e);
                        }
                        break;
                */
            default:    this.showCommand();
                        break;
        }
    }



    /**
     * This method fill the information unit with all the input token from the user.
     * @param setInfoUnit: This object contains all of the placement info chosen by the user.
     */
    @Override
    public void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) {
        WindowPatternCardView wp = this.getClientPlayer().getWp();

        gamePlaySate.getPlacementInfo(commonBoard.getDraftPool(),wp, setInfoUnit);

        try {
            server.performMove(setInfoUnit);
        } catch (BrokenConnectionException e){
            SagradaLogger.log(Level.SEVERE, "Connection broken during normal placement",e);
        }
    }

    /**
     * This method update the wp of the player connected and print it.
     * @param unit : information for the update, index of matrix and die that needs to be place.
     */
    @Override
    public void updateOwnWp(SetUpInformationUnit unit){

        for (PlayerView p : commonBoard.getPlayers())
            if (p.getUserName().equals(this.player.getUserName())){
                gamePlaySate.updateWp(p.getWp(), unit);
                p.getWp().printWp();
                return;
            }

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
    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit unit) {

        for (PlayerView p : commonBoard.getPlayers())
            if(p.getUserName().equals(username))
                gamePlaySate.updateWp(p.getWp(), unit);


        if (player.getUserName().equals(username)) {
            inputOutputManager.print("Dado piazzato!");
            this.getClientPlayer().getWp().printWp();
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
    public void run() {
        Scanner scanner = new Scanner(System.in);

        Thread threadInputs = new Thread(() -> {
            while (!isMyTurn) {
                gamePlaySate.showNotMyTurnCommand();
                int commandChosen = Integer.parseInt(scanner.nextLine());
                switch (commandChosen) {
                    case 1:
                        gamePlaySate.printAllWp(commonBoard.getPlayers(), this.player);
                        break;

                    case 2:
                        gamePlaySate.printPubObj(commonBoard.getPublicObjectiveCards());
                        break;

                    case 3:
                        gamePlaySate.printTool(commonBoard.getToolCards());
                        break;

                    case 4:
                        gamePlaySate.printPrivateObj(this.player.getPrivateObjCard());
                        break;
                    default:
                }
            }
        });

        threadInputs.start();
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

    @Override
    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
        this.run();
    }

    public PlayerView getClientPlayer(){

        for (PlayerView p: this.commonBoard.getPlayers())
            if (p.getUserName().equals(this.player.getUserName()))
                return p;

        return null;
    }
}

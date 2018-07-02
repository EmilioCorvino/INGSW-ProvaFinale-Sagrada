package it.polimi.ingsw.controller.managers;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.cards.PublicObjectiveCardSlot;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.die.containers.WindowPatternCardDeck;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.SagradaLogger;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.EmptyException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;

/**
 * This class provides methods to support all the operations needed before the match starts.
 */
public class StartGameManager extends AGameManager {

    /**
     * List containing the players that have chosen a {@link WindowPatternCard}.
     */
    private List<String> playersWhoChose;

    /**
     * Map containing, for each player on the game, the ids of the {@link WindowPatternCard}s drawn for them.
     */
    private Map<String, List<Integer>> listOfSentWpID;

    /**
     * List containing all the players that logged out or disconnected before choosing the {@link WindowPatternCard}.
     */
    private List<String> playersDisconnectedBeforeCommonBoardSetting;

    /**
     * Signals if the setting up is over or not.
     */
    private boolean matchSetUp;

    /**
     * A part from initializing the class attributes, this constructor also loads the timer from file. This timer will
     * be used by all game managers.
     * @param controllerMaster main controller class.
     */
    public StartGameManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        this.listOfSentWpID = new HashMap<>();
        this.playersWhoChose = new ArrayList<>();
        this.playersDisconnectedBeforeCommonBoardSetting = new ArrayList<>();
        this.matchSetUp = false;

        //Back up value.
        super.timeOut = BACK_UP_TIMER;

        //Value read from file. If the loading is successful, it overwrites the back up.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(TIMER_FILE)))) {
            super.timeOut = Long.parseLong(reader.readLine());
            SagradaLogger.log(Level.CONFIG, "Timer successfully loaded from file. Its value is: " + timeOut / 1000 + "s");
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to load the turn timer from file.", e);
        }
    }

    public boolean isMatchSetUp() {
        return matchSetUp;
    }

    private void setMatchSetUp(boolean matchSetUp) {
        this.matchSetUp = matchSetUp;
    }

    public List<String> getPlayersDisconnectedBeforeCommonBoardSetting() {
        return playersDisconnectedBeforeCommonBoardSetting;
    }

//----------------------------------------------------------
//                    SET UP METHODS
//----------------------------------------------------------

    /**
     * This method gathers from the clients everything that is needed to set up the match, allowing players to see
     * the {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard} drawn for each of them and to
     * choose the {@link WindowPatternCard} among those sent to each of them.
     */
    public void setUpPrivateObjectiveCardAndWp() {
        this.setPrivateObjectiveCard();
        this.setUpWindowPattern();
    }

    /**
     * This methods shows to the clients the {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard}
     * that has been drawn for them and sets it in the view.
     */
    private void setPrivateObjectiveCard() {
        super.getControllerMaster().getCommonBoard().givePrivateObjCard();
        super.getControllerMaster().getConnectedPlayers().forEach((playerName, connection) -> {
            int privateObjId = this.privateObjCardConverter(playerName);
            try {
                connection.getClient().showPrivateObjective(privateObjId);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Impossible to set the private objective card to " + playerName, e);
                this.exitGame(playerName);
            }
        });
    }


    /**
     * This method shows to each player the 4 {@link WindowPatternCard}s drawn for him and lets him choose one among
     * those.
     * It also saves the ids of the cards sent in {@link #listOfSentWpID}, to be able to check if the player has chosen
     * a right id afterwards.
     */
    private void setUpWindowPattern() {
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        players.forEach(player -> {
            IFromServerToClient iFromServerToClient = super.getPlayerClient(player.getPlayerName());
            List<SimplifiedWindowPatternCard> listOfSentWp = chooseWindowPatternCard();

            //This list is useful to check the ids of the window pattern cards sent.
            List<Integer> sentWpIDs = new ArrayList<>();
            listOfSentWp.forEach(wp -> sentWpIDs.add(wp.getIdMap()));
            this.listOfSentWpID.put(player.getPlayerName(), sentWpIDs);

            //Sends to the player the window pattern cards drawn for him and saves their ids in the map of this class.
            try {
                iFromServerToClient.showMapsToChoose(listOfSentWp);
                iFromServerToClient.showCommand(Arrays.asList(Commands.CHOOSE_WP, Commands.LOGOUT));
                this.startTimer(player.getPlayerName());
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Impossible to send window pattern cards to " + player.getPlayerName(), e);
                this.exitGame(player.getPlayerName());
            }
        });
    }

    /**
     * This method extracts four window pattern cards among which the player will choose.
     */
    private List<SimplifiedWindowPatternCard> chooseWindowPatternCard() {
        List<SimplifiedWindowPatternCard> list1 = windowPatternCardConverter();
        List<SimplifiedWindowPatternCard> listToSend = new ArrayList<>(list1);

        List<SimplifiedWindowPatternCard> list2 = windowPatternCardConverter();
        listToSend.addAll(list2);

        return listToSend;
    }

    /**
     * If the player sends an ID among those of the window pattern cards that have been drawn for him, this method
     * sets said window pattern card to the player, adds the player to the ones that have correctly chosen the
     * window pattern card ({@link #playersWhoChose}) and starts the match if everybody has chosen.
     * If the ID is not correct, this methods says it to the player and asks for another ID.
     *
     * @param username name of the player sending the chosen {@link WindowPatternCard}.
     * @param chosenWp chosen {@link WindowPatternCard}.
     */
    public synchronized void wpToSet(String username, int chosenWp) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        if (this.listOfSentWpID.get(username).contains(chosenWp)) {
            WindowPatternCard wpToSet = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenWp);
            commonBoard.getSpecificPlayer(username).setWindowPatternCard(wpToSet);
            this.markPlayerAndEventuallyStartMatch(username);
        } else {
            try {
                super.getControllerMaster().getConnectedPlayers().get(username).getClient()
                        .showNotice("Hai selezionato una vetrata non valida, reinserisci un ID tra quelli mostrati.");
                super.getControllerMaster().getConnectedPlayers().get(username).getClient()
                        .showCommand(Arrays.asList(Commands.CHOOSE_WP, Commands.LOGOUT));
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Impossible to send a notice to the client", e);
                this.exitGame(username);
            }
        }
    }

    /**
     * Checks if all the players have chosen a window pattern card. If so, starts the match. If not, informs the player
     * of how many people still have to choose.
     *
     * @param playerName name of the player that has just chosen a correct wp.
     */
    private void markPlayerAndEventuallyStartMatch(String playerName) {
        this.playersWhoChose.add(playerName);
        if (this.playersWhoChose.size() == super.getControllerMaster().getCommonBoard().getPlayers().size()) {
            this.setCommonBoard();
            this.setMatchSetUp(true);
            super.getControllerMaster().getGamePlayManager().startRound();
        } else {
            try {
                super.getControllerMaster().getConnectedPlayers().get(playerName).getClient().showNotice(
                        "Alcuni giocatori (n: " + (super.getControllerMaster().getCommonBoard().getPlayers().size() -
                                this.playersWhoChose.size()) +
                                ") devono ancora scegliere la vetrata, attendi...");
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Impossible to send a notice to the client", e);
                super.getControllerMaster().suspendPlayer(playerName);
                if (!this.playersDisconnectedBeforeCommonBoardSetting.contains(playerName)) {
                    this.playersDisconnectedBeforeCommonBoardSetting.add(playerName);
                }
            }
        }
    }

    /**
     * This method is used to set the rest of the {@link CommonBoard} with a populated
     * {@link it.polimi.ingsw.model.die.containers.DiceDraftPool}, other players' {@link WindowPatternCard}s,
     * {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard}s and
     * {@link it.polimi.ingsw.model.cards.tool.ToolCard}s drawn.
     */
    private void setCommonBoard() {
        Map<String, SimplifiedWindowPatternCard> mapOfWp = mapsOfPlayersConverter();
        int[] idPubObj = pubObjConverter();
        int[] idTool = toolConverter();

        super.getControllerMaster().getConnectedPlayers().forEach((playerName, connection) -> {
            try {
                connection.getClient().setCommonBoard(mapOfWp, idPubObj, idTool);
                connection.getClient().setFavorToken(numberFavTokenConverter(playerName));
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Impossible to set the common board to " + playerName, e);
                super.getControllerMaster().suspendPlayer(playerName);
                if (!this.playersDisconnectedBeforeCommonBoardSetting.contains(playerName)) {
                    this.playersDisconnectedBeforeCommonBoardSetting.add(playerName);
                }
            }
        });
    }

    /**
     * This method sets a {@link CommonBoard} to a specific player.
     * @param playerName player whose {@link CommonBoard} has to be set.
     */
    public void setOneCommonBoard(String playerName) {
        Map<String, SimplifiedWindowPatternCard> mapOfWp = mapsOfPlayersConverter();
        int[] idPubObj = pubObjConverter();
        int[] idTool = toolConverter();


        IFromServerToClient client = super.getControllerMaster().getConnectedPlayers().get(playerName).getClient();
        try {
            client.setCommonBoard(mapOfWp, idPubObj, idTool);
            client.setFavorToken(numberFavTokenConverter(playerName));
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to set the common board to " + playerName, e);
            super.getControllerMaster().suspendPlayer(playerName);
            this.playersDisconnectedBeforeCommonBoardSetting.add(playerName);
        }
    }

    /**
     * Starts the timer of the turn. If it can't be loaded from file, a back up value is used.
     * @param playerName name of the player to suspend in case he didn't choose the {@link WindowPatternCard} on time.
     */
    @Override
    void startTimer(String playerName) {
        SagradaLogger.log(Level.INFO, playerName + " wp choice timer is started");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SagradaLogger.log(Level.WARNING, playerName + " wp choice timer is expired");
                if (!playersWhoChose.contains(playerName)) {
                    exitGame(playerName);
                }
            }
        }, timeOut);
    }

    /**
     * This method is called when a player wants to log out during the set up phase. If he didn't chose a
     * {@link WindowPatternCard}, a random one is assigned among the sent ones.
     * @param playerName name of the player that wants to log out.
     */
    public void exitGame(String playerName) {
        super.getControllerMaster().suspendPlayer(playerName);

        if (!this.playersWhoChose.contains(playerName)) {
            this.playersDisconnectedBeforeCommonBoardSetting.add(playerName);
            List<Integer> idSent = this.listOfSentWpID.get(playerName);
            int random = new Random().nextInt(idSent.size());
            this.wpToSet(playerName, this.listOfSentWpID.get(playerName).get(random));
        }

        if (this.playersWhoChose.size() == super.getControllerMaster().getCommonBoard().getPlayers().size()) {
            this.setCommonBoard();
            super.getControllerMaster().getGamePlayManager().startRound();
        }
    }

//----------------------------------------------------------
//           MODEL TO VIEW OBJECTS CONVERTERS
//----------------------------------------------------------

    /**
     * Converts the {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard} of a player into its id.
     *
     * @param userName name of the player owning the
     *                 {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard}.
     * @return id of the {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard} owned by
     * the player.
     */
    private int privateObjCardConverter(String userName) {
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        for (Player p : players)
            if (p.getPlayerName().equals(userName)) {
                return p.getPrivateObjectiveCard().getId();
            }
        return 0;
    }

    /**
     * This method converts the window pattern card into objects to send to the client.
     *
     * @return a list of a couple of matched window pattern card.
     */
    private List<SimplifiedWindowPatternCard> windowPatternCardConverter() {
        WindowPatternCardDeck mapDeck = super.getControllerMaster().getCommonBoard().getWindowPatternCardDeck();
        List<SimplifiedWindowPatternCard> wpToSend = new ArrayList<>();

        try {
            List<WindowPatternCard> coupleOfWP = mapDeck.drawCard();
            for (WindowPatternCard wp : coupleOfWP) {
                Cell[][] gw = wp.getGlassWindow();
                List<SetUpInformationUnit> informationUnitList = new ArrayList<>();
                for (int i = 0; i < WindowPatternCard.getMaxRow(); i++)
                    for (int j = 0; j < WindowPatternCard.getMaxCol(); j++) {
                        SetUpInformationUnit setUpInfo = new SetUpInformationUnit(i * WindowPatternCard.getMaxCol() + j,
                                gw[i][j].getDefaultColorRestriction().getColor(),
                                gw[i][j].getDefaultValueRestriction().getValue());
                        informationUnitList.add(setUpInfo);
                    }
                SimplifiedWindowPatternCard simpleWp = new SimplifiedWindowPatternCard(informationUnitList);
                simpleWp.setIdMap(wp.getIdMap());
                simpleWp.setDifficulty(wp.getDifficulty());
                wpToSend.add(simpleWp);
            }
        } catch (EmptyException empty) {
            SagradaLogger.log(Level.SEVERE, "Window pattern card deck is empty!", empty);
        }
        return wpToSend;
    }

    /**
     * Maps each player (identified by his user name) with the {@link SimplifiedWindowPatternCard} of choice.
     *
     * @return the map in which each player is mapped with his {@link SimplifiedWindowPatternCard}.
     */
    private Map<String, SimplifiedWindowPatternCard> mapsOfPlayersConverter() {
        Map<String, SimplifiedWindowPatternCard> mapOfWp = new HashMap<>();
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        for (Player player : players) {
            mapOfWp.put(player.getPlayerName(), convertOneWp(player.getWindowPatternCard().getIdMap()));
        }
        return mapOfWp;
    }

    /**
     * Converts a complex {@link WindowPatternCard} into a {@link SimplifiedWindowPatternCard}.
     *
     * @param chosenMap id of the chosen {@link WindowPatternCard}.
     * @return a {@link SimplifiedWindowPatternCard} obtained by a {@link WindowPatternCard}.
     */
    public SimplifiedWindowPatternCard convertOneWp(int chosenMap) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        WindowPatternCard wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenMap);

        Cell[][] gw = wp.getGlassWindow();

        List<SetUpInformationUnit> informationUnitList = new ArrayList<>();

        for (int i = 0; i < WindowPatternCard.getMaxRow(); i++)
            for (int j = 0; j < WindowPatternCard.getMaxCol(); j++)
                informationUnitList.add(new SetUpInformationUnit(i * WindowPatternCard.getMaxCol() + j,
                        gw[i][j].getDefaultColorRestriction().getColor(), gw[i][j].getDefaultValueRestriction().getValue()));

        SimplifiedWindowPatternCard simpleWp = new SimplifiedWindowPatternCard(informationUnitList);
        simpleWp.setDifficulty(wp.getDifficulty());
        simpleWp.setIdMap(chosenMap);
        return simpleWp;
    }

    /**
     * Converts the {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard}s drawn into an array of
     * integers, which are their id.
     *
     * @return an array containing the drawn {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard}s'
     * ids.
     */
    private int[] pubObjConverter() {
        int index = 0;
        int[] pubObj = new int[3];
        List<PublicObjectiveCardSlot> slotList = super.getControllerMaster().getCommonBoard().getPublicObjectiveCardSlots();

        for (PublicObjectiveCardSlot slot : slotList) {
            pubObj[index] = slot.getPublicObjectiveCard().getId();
            index++;
        }

        return pubObj;
    }

    /**
     * Converts the {@link it.polimi.ingsw.model.cards.tool.ToolCard}s drawn into an array of integers, which are their
     * id.
     *
     * @return an array containing the drawn {@link it.polimi.ingsw.model.cards.tool.ToolCard}s' ids.
     */
    private int[] toolConverter() {
        int index = 0;
        int[] tool = new int[3];
        List<ToolCardSlot> slotList = super.getControllerMaster().getCommonBoard().getToolCardSlots();

        for (ToolCardSlot slot : slotList) {
            tool[index] = slot.getToolCard().getId();
            index++;
        }

        return tool;
    }

    /**
     * Gets the favor tokens owned by the player.
     *
     * @param userName name of the player owning the favor tokens.
     * @return the number of favor tokens owned by the player.
     */
    private int numberFavTokenConverter(String userName) {
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        for (Player p : players)
            if (p.getPlayerName().equals(userName))
                return p.getFavorTokens();
        return 0;
    }
}

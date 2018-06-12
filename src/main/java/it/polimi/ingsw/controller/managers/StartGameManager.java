package it.polimi.ingsw.controller.managers;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.cards.PublicObjectiveCardSlot;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCardDeck;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.EmptyException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.util.*;
import java.util.logging.Level;

/**
 * This class provides methods to support all the operations needed before the match starts.
 */
public class StartGameManager extends AGameManager {

    /**
     * Counter of how many player have chosen a {@link WindowPatternCard}.
     */
    private int wpSetCount;

    /**
     * Map containing, for each player on the game, the ids of the {@link WindowPatternCard}s drawn for them.
     */
    private Map<String, List<Integer>> listOfSentWpID;

    public StartGameManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        this.listOfSentWpID = new HashMap<>();
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
                //todo handle disconnecion
            }
        });
    }

    /**
     * This method shows to the client four window pattern cards among which to choose.
     */
    private List<SimplifiedWindowPatternCard> chooseWindowPatternCard() {
        List<SimplifiedWindowPatternCard> listToSend = new ArrayList<>();

        List<SimplifiedWindowPatternCard> list1 = windowPatternCardConverter();
        listToSend.addAll(list1);

        List<SimplifiedWindowPatternCard> list2 = windowPatternCardConverter();
        listToSend.addAll(list2);

        return listToSend;
    }

    /**
     * If the player sends an ID among those of the window pattern cards that have been drawn for him, this methods
     * sets said window pattern card to the player, increments the counter of players that have correctly chosen the
     * window pattern card ({@link #wpSetCount}) and starts the match if everybody has chosen.
     * If the ID is not correct, this methods says it to the player and asks for another ID.
     * @param username name of the player sending the chosen {@link WindowPatternCard}.
     * @param chosenWp chosen {@link WindowPatternCard}.
     */
    public synchronized void wpToSet(String username, int chosenWp) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        if(this.listOfSentWpID.get(username).contains(chosenWp)) {
            WindowPatternCard wpToSet = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenWp);
            commonBoard.getSpecificPlayer(username).setWindowPatternCard(wpToSet);
            this.incrementSetWpAndEventuallyStartMatch(username);
        } else {
            try {
                super.getControllerMaster().getConnectedPlayers().get(username).getClient().showNotice(
                        "Hai selezionato una vetrata non valida, reinserisci un ID tra quelli mostrati.");
                super.getControllerMaster().getConnectedPlayers().get(username).getClient().showCommand(
                        Collections.singletonList(Commands.CHOOSE_WP));
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Impossible to send a notice to the client", e);
                //todo handle disconnection.
            }
        }
    }

    /**
     * Checks if all the players have chosen a window pattern card. If so, starts the match. If not, informs the player
     * of how many people still have to choose.
     * @param username name of the player that has just chosen a correct wp.
     */
    private void incrementSetWpAndEventuallyStartMatch(String username) {
        this.wpSetCount++;
        if(this.wpSetCount == super.getControllerMaster().getCommonBoard().getPlayers().size()) {
            this.setCommonBoard();
            ((GamePlayManager) super.getControllerMaster().getGamePlayManager()).startMatch();
        } else {
            try {
                super.getControllerMaster().getConnectedPlayers().get(username).getClient().showNotice(
                        "Alcuni giocatori (n: " + (super.getControllerMaster().getCommonBoard().getPlayers().size() - 1) +
                                ") devono ancora scegliere la vetrata, attendi...");
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Impossible to send a notice to the client", e);
                //todo handle disconnection.
            }
        }
    }

    /**
     * This method shows to the player the 4 {@link WindowPatternCard}s drawn for him and lets him choose one among
     * those.
     * It also saves the ids of the cards sent in {@link #listOfSentWpID}, to be able to check if the player has chosen
     * a right id afterwards.
     */
    private void setUpWindowPattern() {
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        players.forEach(player -> {
            IFromServerToClient iFromServerToClient = super.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
            List<SimplifiedWindowPatternCard> listOfSentWp = chooseWindowPatternCard();

            //This list is useful to check the ids of the window pattern cards sent.
            List<Integer> sentWpIDs = new ArrayList<>();
            listOfSentWp.forEach(wp -> sentWpIDs.add(wp.getIdMap()));

            //Sends to the player the window pattern cards drawn for him and saves their ids in the map of this class.
            try {
                iFromServerToClient.showMapsToChoose(listOfSentWp);
                this.listOfSentWpID.put(player.getPlayerName(), sentWpIDs);
            } catch (BrokenConnectionException br) {
                //handle broken connection.
            }
        });

        //Allows the players to choose a Window Pattern Card among the ones drawn.
        players.forEach(player -> {
            IFromServerToClient iFromServerToClient = super.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
            try {
                iFromServerToClient.showCommand(Collections.singletonList(Commands.CHOOSE_WP));
            } catch (BrokenConnectionException br) {
                //broken connection
            }
        });
      //  super.getControllerMaster().initializeGame();
    }

    /**
     * This method is used to set the rest of the {@link CommonBoard} with a populated
     * {@link it.polimi.ingsw.model.die.diecontainers.DiceDraftPool}, other players' {@link WindowPatternCard}s,
     * {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard}s and
     * {@link it.polimi.ingsw.model.cards.tool.ToolCard}s drawn.
     */
    private void setCommonBoard() {
        List<SetUpInformationUnit> draftPool = draftPoolConverter();
        Map<String, SimplifiedWindowPatternCard> mapOfWp = mapsOfPlayersConverter();
        int [] idPubObj = pubObjConverter();
        int [] idTool = toolConverter();


        super.getControllerMaster().getConnectedPlayers().forEach((playerName, connection) -> {
            try {
                connection.getClient().setCommonBoard(mapOfWp, idPubObj, idTool);
                connection.getClient().setDraft(draftPool);
                connection.getClient().setFavorToken(numberFavTokenConverter(playerName));
            } catch (BrokenConnectionException e) {
                //todo handle disconnecion
            }
        });
    }

//----------------------------------------------------------
//           MODEL TO VIEW OBJECTS CONVERTERS
//----------------------------------------------------------

    /**
     * Converts the {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard} of a player into its id.
     * @param userName name of the player owning the
     * {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard}.
     * @return id of the {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard} owned by
     * the player.
     */
    private int privateObjCardConverter(String userName){
        List <Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        for (Player p : players)
            if (p.getPlayerName().equals(userName)) {
                return p.getPrivateObjectiveCard().getId();
            }
        return 0;
    }

    /**
     * This method converts the window pattern card into objects to send to the client.
     * @return a list of a couple of matched window pattern card.
     */
    private List<SimplifiedWindowPatternCard> windowPatternCardConverter() {
        WindowPatternCardDeck mapDeck = super.getControllerMaster().getCommonBoard().getWindowPatternCardDeck();
        List<SimplifiedWindowPatternCard> wpToSend = new ArrayList<>();

        try {
            List<WindowPatternCard> coupleOfWP = mapDeck.drawCard();
            for(WindowPatternCard wp : coupleOfWP) {
                Cell[][] gw = wp.getGlassWindow();
                List<SetUpInformationUnit> informationUnitList = new ArrayList<>();
                for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
                    for(int j=0; j<WindowPatternCard.getMaxCol(); j++) {
                        SetUpInformationUnit setUpInfo = new SetUpInformationUnit(i*WindowPatternCard.getMaxCol()+j,
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
     * @return the map in which each player is mapped with his {@link SimplifiedWindowPatternCard}.
     */
    private Map<String, SimplifiedWindowPatternCard> mapsOfPlayersConverter() {
        Map<String, SimplifiedWindowPatternCard> mapOfWp = new HashMap<>();
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        for(Player player : players) {
            mapOfWp.put(player.getPlayerName(), convertOneWp(player.getWindowPatternCard().getIdMap()));
        }
        return mapOfWp;
    }

    /**
     * Converts a complex {@link WindowPatternCard} into a {@link SimplifiedWindowPatternCard}.
     * @param chosenMap id of the chosen {@link WindowPatternCard}.
     * @return a {@link SimplifiedWindowPatternCard} obtained by a {@link WindowPatternCard}.
     */
    private SimplifiedWindowPatternCard convertOneWp(int chosenMap) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        WindowPatternCard wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenMap);

        Cell[][] gw = wp.getGlassWindow();

        List<SetUpInformationUnit> informationUnitList = new ArrayList<>();

        for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
            for(int j=0; j<WindowPatternCard.getMaxCol(); j++)
                informationUnitList.add(new SetUpInformationUnit(i*WindowPatternCard.getMaxCol()+j,
                        gw[i][j].getDefaultColorRestriction().getColor(), gw[i][j].getDefaultValueRestriction().getValue()));

        SimplifiedWindowPatternCard simpleWp = new SimplifiedWindowPatternCard(informationUnitList);
        simpleWp.setDifficulty(wp.getDifficulty());
        simpleWp.setIdMap(chosenMap);
        return simpleWp;
    }

    /**
     * Converts the {@link it.polimi.ingsw.model.die.diecontainers.DiceDraftPool} of the model to a simpler
     * List of {@link SetUpInformationUnit}, to represent it in the view.
     * @return a list of {@link SetUpInformationUnit} generated from the match
     * {@link it.polimi.ingsw.model.die.diecontainers.DiceDraftPool}.
     */
    private List<SetUpInformationUnit> draftPoolConverter() {
        List<Die> draftPoolDice = super.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice();
        List<SetUpInformationUnit> draftPoolInfo = new ArrayList<>();
        for(Die die : draftPoolDice)
            draftPoolInfo.add(new SetUpInformationUnit(draftPoolDice.indexOf(die), die.getDieColor(), die.getActualDieValue()));
        return draftPoolInfo;
    }

    /**
     * Converts the {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard}s drawn into an array of
     * integers, which are their id.
     * @return an array containing the drawn {@link it.polimi.ingsw.model.cards.objective.publics.APublicObjectiveCard}s'
     * ids.
     */
    private int[] pubObjConverter(){
        int index = 0;
        int [] pubObj = new int[3];
        List <PublicObjectiveCardSlot> slotList = super.getControllerMaster().getCommonBoard().getPublicObjectiveCardSlots();

        for (PublicObjectiveCardSlot slot : slotList){
            pubObj[index] = slot.getPublicObjectiveCard().getId();
            index++;
        }

        return pubObj;
    }

    /**
     * Converts the {@link it.polimi.ingsw.model.cards.tool.ToolCard}s drawn into an array of integers, which are their
     * id.
     * @return an array containing the drawn {@link it.polimi.ingsw.model.cards.tool.ToolCard}s' ids.
     */
    private int[] toolConverter(){
        int index = 0;
        int [] tool = new int[3];
        List <ToolCardSlot> slotList = super.getControllerMaster().getCommonBoard().getToolCardSlots();

        for (ToolCardSlot slot : slotList){
            tool[index] = slot.getToolCard().getId();
            index++;
        }

        return tool;
    }

    /**
     * Gets the favor tokens owned by the player.
     * @param userName name of the player owning the favor tokens.
     * @return the number of favor tokens owned by the player.
     */
    private int numberFavTokenConverter(String userName){
        List <Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        for (Player p : players)
            if (p.getPlayerName().equals(userName))
                return p.getFavorTokens();
        return 0;
    }
}




/*
    public boolean isAlreadyConnected(String namePlayer, List<Player> playersRoom) {
       boolean[] res = new boolean[1];
        playersRoom.forEach(player -> {
            if(namePlayer.equals(player.getPlayerName())) {
                res[0] = false;
            }
        });
        return res[0];
    }
    */
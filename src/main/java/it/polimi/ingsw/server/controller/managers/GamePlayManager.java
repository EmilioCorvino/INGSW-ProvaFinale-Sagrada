package it.polimi.ingsw.server.controller.managers;

import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.network.IFromServerToClient;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.server.controller.ControllerMaster;
import it.polimi.ingsw.server.model.CommonBoard;
import it.polimi.ingsw.server.model.cards.ToolCardSlot;
import it.polimi.ingsw.server.model.cards.tool.ToolCard;
import it.polimi.ingsw.server.model.cards.tool.effects.draft.DraftValueEffect;
import it.polimi.ingsw.server.model.cards.tool.effects.movement.ColorBoundMoveWithRestrictionsEffect;
import it.polimi.ingsw.server.model.cards.tool.effects.movement.MoveWithRestrictionsEffect;
import it.polimi.ingsw.server.model.cards.tool.effects.movement.ignore.IgnoreAdjacentCellsRestrictionEffect;
import it.polimi.ingsw.server.model.cards.tool.effects.movement.ignore.IgnoreColorRestrictionEffect;
import it.polimi.ingsw.server.model.cards.tool.effects.movement.ignore.IgnoreValueRestrictionEffect;
import it.polimi.ingsw.server.model.cards.tool.effects.swap.SwapFromDraftPoolToRoundTrack;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.server.model.move.AMove;
import it.polimi.ingsw.server.model.move.DefaultDiePlacementMove;
import it.polimi.ingsw.server.model.move.RestrictedDiePlacementMove;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.turn.GameState;
import it.polimi.ingsw.server.model.turn.Turn;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;

/**
 * This class handles everything that concerns the match from the start of the first round to the end of the last one.
 */
public class GamePlayManager extends AGameManager {

    /**
     * List of commands that the player on duty can preform.
     */
    private final List<Commands> currentPlayerCommands;

    /**
     * List of commands that the waiting players can perform.
     */
    private final List<Commands> waitingPlayersCommands;

    /**
     * Map that contains the updated current player commands of each player within the turn.
     */
    private Map<String, List<Commands>> dynamicCommands;

    /**
     * Maximum number of effects a {@link ToolCard} can have.
     */
    public static final int MAX_TOOL_EFFECTS_NUMBER = 2;

    /**
     * Number of effects the user wants to use from a {@link ToolCard}.
     */
    private int numberOfChosenEffects = 0;

    /**
     * Counter that represents how many effects of a tool card have been used.
     */
    private int effectCounter = 0;

    /**
     * Says if the move done is legal or not. It's set by executeMove methods and at the start of each turn.
     */
    private boolean moveLegal = true;

    /**
     * This object is used as a back up for some effects.
     */
    private SetUpInformationUnit backUpUnit;

    private static final String EVERYTHING_DONE = "\nHai effettuato tutte le operazioni possibili in un turno, " +
            "passaggio al giocatore successivo...";

    private static final String IMPOSSIBLE_TO_UPDATE = "Impossible to update ";

    private static final int FIRST_TURN = 0;

    private static final int SECOND_TURN = 1;

    /**
     * This constructor, other than setting the controller master, also initializes the lists of commands to be shown
     * both to the player on duty ({@link #currentPlayerCommands}) and to the others ({@link #waitingPlayersCommands}).
     * It also loads the timer from file.
     * @param controllerMaster main controller class.
     */
    public GamePlayManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        this.currentPlayerCommands = new ArrayList<>();

        //Gets the tool cards commands from the model.
        for (ToolCardSlot slot : controllerMaster.getCommonBoard().getToolCardSlots()) {
            this.currentPlayerCommands.add(slot.getToolCard().getCommandName());
        }

        this.currentPlayerCommands.addAll(Arrays.asList(Commands.PLACEMENT, Commands.VISUALIZATION,Commands.END_TURN, Commands.LOGOUT));
        this.waitingPlayersCommands = new ArrayList<>(Arrays.asList(Commands.VISUALIZATION, Commands.LOGOUT));

        //Initializes the dynamic map of commands.
        this.dynamicCommands = new HashMap<>();

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

    public List<Commands> getCurrentPlayerCommands() {
        return currentPlayerCommands;
    }

    public List<Commands> getWaitingPlayersCommands() {
        return waitingPlayersCommands;
    }

    public Map<String, List<Commands>> getDynamicCommands() {
        return dynamicCommands;
    }

    public void incrementEffectCounter() {
        this.effectCounter++;
    }

    public int getEffectCounter() {
        return effectCounter;
    }

//----------------------------------------------------------
//                    GAME-PLAY FLOW METHODS
//----------------------------------------------------------

    /**
     * Starts a round, preparing the turn order.
     */
    void startRound() {
        GameState gameState = super.getControllerMaster().getGameState();

        //Terminates the match if there are no players left.
        if (super.getControllerMaster().getSuspendedPlayers().size() == super.getControllerMaster().getConnectedPlayers().size()) {
            super.getControllerMaster().getStartGameManager().setMatchRunning(false);
            gameState.setMatchOver(true);
            super.getControllerMaster().getEndGameManager().quitGame();
            return;
        }

        if (!gameState.isMatchOver()) {
            //Reorders the list of players each round a part from the first.
            List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
            if (gameState.getActualRound() > 1) {
                gameState.reorderPlayerList(players);
            }
            gameState.initializePlayerList(players);

            //Populates the dice draft pool and updates the view.
            super.getControllerMaster().getCommonBoard().getDraftPool().populateDiceDraftPool(players.size());
            this.broadcastNotification("\n\nÈ INIZIATO IL ROUND N° " + gameState.getActualRound() + "\n" +
                    "La riserva è stata aggiornata:");
            for (Player player : players) {
                IFromServerToClient client = super.getPlayerClient(player.getPlayerName());
                try {
                    client.setDraft(super.draftPoolConverter());
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Connection broken while updating draft pool at the start" +
                            "of a round");
                    super.getControllerMaster().suspendPlayer(player.getPlayerName(), true);
                }
            }

            this.startTurn(gameState.getCurrentTurn());
        } else {
            super.getControllerMaster().getStartGameManager().setMatchRunning(false);
            super.getControllerMaster().getEndGameManager().computeRank();
        }
    }

    /**
     * This method starts the turn of a player, sending the correct {@link Commands} to him and the waiting players.
     * It also brings all the counters and booleans of the class to their initial state, checks if the match is over
     * (useful for player who are reconnecting), if the turn has to be skipped and if the player is suspended, then
     * it acts accordingly.
     * @param currentPlayerTurn the turn of the current player.
     */
    private void startTurn(Turn currentPlayerTurn) {
        this.setMoveLegal(true);
        this.effectCounter = 0;
        this.numberOfChosenEffects = 0;
        this.backUpUnit = null;
        GameState gameState = super.getControllerMaster().getGameState();

        if (gameState.isCurrentTurnOver()) {
            this.endTurn("\nDevi saltare questo turno!");
            return;
        }

        if (super.getControllerMaster().getSuspendedPlayers().contains(currentPlayerTurn.getPlayer().getPlayerName())) {
            super.broadcastNotification("\n" + currentPlayerTurn.getPlayer().getPlayerName() + " è sospeso: il suo turno" +
                    " verrà saltato.");
            this.endTurn("\nSei in stato di sospensione, il tuo turno verrà saltato.");
            return;
        }

        //Shows the available commands to the players waiting.
        List<Player> waitingPlayers = super.getControllerMaster().getCommonBoard().getPlayers();
        for (Player player : waitingPlayers) {
            if (!player.isSamePlayerAs(currentPlayerTurn.getPlayer()) &&
                    !super.getControllerMaster().getSuspendedPlayers().contains(player.getPlayerName())) {
                IFromServerToClient waitingPlayerClient = super.getPlayerClient(player.getPlayerName());
                try {
                    waitingPlayerClient.showNotice("\nÈ il turno di " + gameState.getCurrentPlayer().getPlayerName());
                    waitingPlayerClient.showCommand(this.waitingPlayersCommands);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Impossible to show commands " +
                            "to the waiting players");
                    super.getControllerMaster().suspendPlayer(player.getPlayerName(), true);
                }
            }
        }

        //Check whether this is the first or the second turn of this player in this round.
        String turnNumber =
                gameState.getCurrentPlayerTurnIndex() < (gameState.getTurnOrder().size() / 2) ? "PRIMO" : "SECONDO";

        super.sendNotificationToCurrentPlayer("\nÈ IL TUO " + turnNumber + " TURNO DEL ROUND!");
        this.dynamicCommands.replace(currentPlayerTurn.getPlayer().getPlayerName(), new ArrayList<>(this.currentPlayerCommands));
        super.sendCommandsToCurrentPlayer(this.dynamicCommands.get(currentPlayerTurn.getPlayer().getPlayerName()));
        this.startTimer(currentPlayerTurn.getPlayer().getPlayerName());
    }

    /**
     * Starts the timer of the turn. If it can't be loaded from file, a back up value is used.
     * This method cancels the previous {@link TimerTask} if it's not completed yet, and then starts a new task.
     * From the playerName, the last turn of the player is retrieved from the turn order: if this turn isn't completed
     * by when the timer expires, the player gets suspended.
     * @param playerName player to suspend in case his turn isn't over when the timer expires.
     */
    @Override
    void startTimer(String playerName) {
        if (task != null) {
            task.cancel();
            task = null;
            SagradaLogger.log(Level.INFO, "Previous timer has been canceled.");
        }

        GameState gameState = super.getControllerMaster().getGameState();
        SagradaLogger.log(Level.INFO, playerName + " turn timer is started");
        task = new TimerTask() {
            @Override
            public void run() {
                SagradaLogger.log(Level.WARNING, playerName + " turn timer is expired");

                //This should always be overwritten.
                Turn playerTurn = new Turn(new Player("fakePlayer", getControllerMaster().getCommonBoard()));

                for (Turn turn: gameState.getTurnOrder()) {
                    if (turn.getPlayer().getPlayerName().equals(playerName) &&
                            gameState.getTurnOrder().indexOf(turn) <= gameState.getCurrentPlayerTurnIndex()) {
                        playerTurn = turn;
                    }
                }
                if (!playerTurn.isTurnCompleted()) {
                    getControllerMaster().suspendPlayer(playerTurn.getPlayer().getPlayerName(), false);
                }
            }
        };
        timer.schedule(task, super.timeOut);
    }

    /**
     * Ends the turn, incrementing the index of the current player in the turn order list at {@link GameState}.
     * Then starts a new turn. If the player is the only one left, declare him as winner and ends the match.
     * @param message communication to send to the player, informing him why is his turn over.
     */
    public void endTurn(String message) {
        GameState gameState = super.getControllerMaster().getGameState();

        //Sends the message only if the player is not disconnected.
        if (super.getControllerMaster().getDisconnectedPlayers().contains(gameState.getCurrentPlayer().getPlayerName())) {
            IFromServerToClient currentPlayerClient = super.getPlayerClient(gameState.getCurrentPlayer().getPlayerName());
            try {
                currentPlayerClient.showNotice(message);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection broken while ending the turn");
                super.getControllerMaster().suspendPlayer(gameState.getCurrentPlayer().getPlayerName(), true);
            }
        }

        //Checks if there is only one (or less) player left playing. In that case, ends the match.
        if (super.getControllerMaster().getSuspendedPlayers().size() >= (super.getControllerMaster().getConnectedPlayers().size() - 1)) {
            super.getControllerMaster().getStartGameManager().setMatchRunning(false);
            gameState.setMatchOver(true);
            for(String playerName: super.getControllerMaster().getConnectedPlayers().keySet()) {
                if(!super.getControllerMaster().getSuspendedPlayers().contains(playerName)) {
                    IFromServerToClient client = super.getPlayerClient(playerName);
                    EndGameManager endGameManager = super.getControllerMaster().getEndGameManager();
                    try {
                        client.showNotice("\nSei l'ultimo giocatore rimasto, HAI VINTO PER ABBANDONO!");
                        client.showCommand(endGameManager.endGameCommands);
                        endGameManager.startTimer(playerName);
                        return;
                    } catch (BrokenConnectionException e) {
                        SagradaLogger.log(Level.SEVERE, playerName + " disconnected. Preparing a new room...");
                        endGameManager.quitGame();
                    }
                }
            }
            return;
        }

        //If the round is not over, proceed to the next turn.
        if (gameState.getCurrentPlayerTurnIndex() < gameState.getTurnOrder().size() - 1) {
            gameState.incrementCurrentPlayerTurnIndex();
            this.startTurn(gameState.getCurrentTurn());
        }

        //If the round is over, start a new one, unless it was the last one. In that case, report that the match is over.
        else {
            this.endRound(gameState);
        }
    }

    /**
     * Starts a new round, by resetting the turn index and incrementing the round count. If the match is over, it
     * signals it.
     * @param gameState object containing the overall state of the match.
     */
    private void endRound(GameState gameState) {
        gameState.resetCurrentPlayerTurnIndex();
        this.moveRemainingDiceFromDraftPoolToRoundTrack(gameState, super.getControllerMaster().getCommonBoard());
        if (gameState.getActualRound() < GameState.LAST_ROUND) {
            gameState.incrementActualRound();
        } else {
            gameState.setMatchOver(true);
        }
        startRound();
    }

    /**
     * This method is used at the start of each round to move all remaining dice in the
     * {@link it.polimi.ingsw.server.model.die.containers.DiceDraftPool} to the space on the
     * {@link it.polimi.ingsw.server.model.die.containers.RoundTrack} corresponding to the current round.
     */
    private void moveRemainingDiceFromDraftPoolToRoundTrack(GameState gameState, CommonBoard board) {
        if (!board.getDraftPool().getAvailableDice().isEmpty()) {
            board.getRoundTrack().setRoundToBeUpdated(gameState.getActualRound() - 1);
            board.getRoundTrack().createCopy();
            int actualDraftSize = board.getDraftPool().getAvailableDice().size();
            List<SetUpInformationUnit> diceToSend = new ArrayList<>();

            for (int i = 0; i < actualDraftSize; i++) {
                Die dieToMove = board.getDraftPool().getAvailableDice().remove(0);
                board.getRoundTrack().addDieToCopy(dieToMove);
                diceToSend.add(new SetUpInformationUnit(gameState.getActualRound() - 1, dieToMove.getDieColor(), dieToMove.getActualDieValue()));
            }
            board.getRoundTrack().overwriteOriginal();

            //Updates the view of the players.
            super.getControllerMaster().getConnectedPlayers().forEach((playerName, connection) -> {
                if (!super.getControllerMaster().getSuspendedPlayers().contains(playerName)) {
                    for (SetUpInformationUnit infoUnit: diceToSend) {
                        try {
                            connection.getClient().addOnRoundTrack(infoUnit);
                        } catch (BrokenConnectionException e) {
                            SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + playerName + " Round Track");
                            super.getControllerMaster().suspendPlayer(playerName, true);
                        }
                    }
                }
            });
        }
    }

//----------------------------------------------------------
//                    CLIENT REQUESTS METHODS
//----------------------------------------------------------

    /**
     * This method is used to perform a standard {@link DefaultDiePlacementMove}. It can handle weird situations in which a
     * player tries to perform moves not in his turn.
     * It also checks if a placement has already been done and in that case it shows to the player new suitable
     * {@link Commands}.
     * @param info       object containing the information needed to update the model.
     * @param playerName name of the player trying to perform the move.
     * @see DefaultDiePlacementMove
     */
    public void performDefaultMove(SetUpInformationUnit info, String playerName) {
        GameState gameState = super.getControllerMaster().getGameState();

        //Checks if the request comes from the current player. If not, shows the player the commands of a waiting player.
        //This is just a security measure, it should not be possible to be in this situation.
        if (this.handleIllegalStateRequests(playerName)) {
            return;
        }

        if (!gameState.getCurrentTurn().isTurnCompleted()) {

            //If the player has already placed a die, tells him and shows him a new appropriate list of commands.
            if (gameState.getCurrentTurn().isDiePlaced()) {
                List<Commands> filteredCommands = this.filterPlacement(this.dynamicCommands.get(playerName), playerName);
                super.sendNotificationToCurrentPlayer("\nHai già effettuato un piazzamento in questo turno! Puoi ancora " +
                        "usare una Carta Strumento che non lo implichi, visualizzare informazioni della plancia" +
                        " oppure passare.");
                super.sendCommandsToCurrentPlayer(filteredCommands);
            } else {
                AMove move = new DefaultDiePlacementMove();
                move.executeMove(this, info);
                this.checkAndResolveDefaultMoveLegality(gameState, playerName);
            }
        } else {
            this.endTurn(EVERYTHING_DONE);
        }
    }

    /**
     * This method is called to use a {@link ToolCard}. It can handle weird situations in which a player tries to perform
     * moves not in his turn.
     * It also checks if a Tool Card has already been used and in that case it shows to the player new suitable
     * {@link Commands}.
     * @param infoUnits  list of objects containing the information needed to update the model.
     * @param playerName name of the player trying to perform the move.
     */
    public void performToolCardMove(int slotID, List<SetUpInformationUnit> infoUnits, String playerName) {
        GameState gameState = super.getControllerMaster().getGameState();

        //Checks if the request comes from the current player. If not, shows the player the commands of a waiting player.
        //This is just a security measure, it should not be possible to be in this situation.
        if (this.handleIllegalStateRequests(playerName)) {
            return;
        }

        if (!gameState.isCurrentTurnOver()) {

            //If the player has already used a Tool Card, tells him and shows him a new appropriate list of commands.
            if (gameState.getCurrentTurn().isToolCardUsed()) {
                List<Commands> filteredCommands = this.filterTools(this.dynamicCommands.get(playerName), gameState, playerName);
                super.sendNotificationToCurrentPlayer("\nHai già usato una Carta Strumento in questo turno! Puoi ancora " +
                        "piazzare un dado (se la carta usata non implicava un piazzamento), visualizzare informazioni " +
                        "della plancia, oppure passare.");
                super.sendCommandsToCurrentPlayer(filteredCommands);
            } else {
                ToolCardSlot slot = super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID);
                this.checkToolCardAvailability(gameState, slotID, playerName);
                this.numberOfChosenEffects = infoUnits.size();
                this.effectCounter = 0;
                for (int i = 0; i < this.numberOfChosenEffects; i++) {
                    if (this.isMoveLegal()) {
                        slot.getToolCard().getCardEffects().get(i).executeMove(this, infoUnits.get(i));
                    }
                }

                //Checks if the executeMove has been successful or not, and modifies the state of the turn accordingly.
                this.checkAndResolveToolMoveLegality(gameState, slotID, slot.getToolCard(), playerName);
            }
        } else {
            this.endTurn("\nHai già effettuato tutte le operazioni possibili in un turno, " +
                    "passaggio al giocatore successivo...");
        }
    }

    /**
     * This method is called to perform a placement where the player has to put a specific die into his
     * {@link it.polimi.ingsw.server.model.die.containers.WindowPatternCard}. It is reached through the usage of some
     * {@link ToolCard}.
     * @param infoUnit object containing the information needed to update the model.
     * @param playerName name of the player trying to perform the move.
     * @see DraftValueEffect
     * @see it.polimi.ingsw.server.model.cards.tool.effects.swap.SwapFromDraftPoolToDiceBag
     */
    public void performRestrictedPlacement(SetUpInformationUnit infoUnit, String playerName) {
        GameState gameState = super.getControllerMaster().getGameState();

        //Checks if the request comes from the current player. If not, shows the player the commands of a waiting player.
        //This is just a security measure, it should not be possible to be in this situation.
        if(this.handleIllegalStateRequests(playerName)) {
            return;
        }

        AMove restrictedPlacement = new RestrictedDiePlacementMove();
        restrictedPlacement.executeMove(this, infoUnit);

        if(!this.isMoveLegal()) {
            super.sendNotificationToCurrentPlayer("\n" + gameState.getCurrentPlayer().getWindowPatternCard().getErrorMessage() +
                    " Prova a piazzare il dado in un'altra posizione.");

            //Only the extra command for tool 6 is sent because even in the case of tool 11, at this point, the value
            //of the die has already been chosen.
            super.sendCommandsToCurrentPlayer(Collections.singletonList(Commands.EXTRA_TOOL6));
        } else {
            if(gameState.isCurrentTurnOver()) {
                this.endTurn(EVERYTHING_DONE);
            } else {
                super.sendNotificationToCurrentPlayer("\nNon è possibile piazzare il dado in alcuna posizione. Verrà aggiunto alla" +
                        " riserva. Puoi ancora effettuare un piazzamento standard.");
                List<Commands> updatedCommands = this.filterTools(this.dynamicCommands.get(playerName), gameState, playerName);
                updatedCommands.add(0, Commands.PLACEMENT);
                super.sendCommandsToCurrentPlayer(updatedCommands);
            }
        }
    }

    /**
     * Lets the player on duty pass his turn, then starts the next one.
     * @param playerName player wanting to end his turn.
     */
    public void moveToNextTurn(String playerName) {
        this.handleIllegalStateRequests(playerName);

        GameState gameState = super.getControllerMaster().getGameState();
        gameState.getCurrentTurn().setPassed(true);
        this.endTurn("\nHai scelto di passare il turno.");
    }

//----------------------------------------------------------
//                    SERVER RESPONSE METHODS
//----------------------------------------------------------

    /**
     * Shows the result of the default placement move to the player on duty and the waiting players.
     * Note that this method can be also called to communicate the result of some of the {@link ToolCard}s.
     * @param currentPlayer player on duty.
     * @param setUpInfoUnit information used by the view to update
     *                      the {@link it.polimi.ingsw.client.view.cli.die.WindowPatternCardView} and the
     *                      {@link it.polimi.ingsw.client.view.cli.die.DieDraftPoolView}.
     * @see DefaultDiePlacementMove
     * @see it.polimi.ingsw.server.model.cards.tool.effects.value.ChooseValueEffect
     * @see it.polimi.ingsw.server.model.cards.tool.effects.value.OppositeValueEffect
     * @see IgnoreAdjacentCellsRestrictionEffect
     */
    public void showPlacementResult(Player currentPlayer, SetUpInformationUnit setUpInfoUnit) {
        if (!this.isMoveLegal()) {
            return;
        }

        //Copies back the updated wp and draft.
        currentPlayer.getWindowPatternCard().overwriteOriginal();
        super.getControllerMaster().getCommonBoard().getDraftPool().overwriteOriginal();

        //Updates the board of the waiting players.
        List<Player> waitingPlayers = super.getControllerMaster().getCommonBoard().getPlayers();
        for (Player p : waitingPlayers) {
            if (!p.isSamePlayerAs(currentPlayer)) {
                IFromServerToClient waitingPlayerClient = super.getPlayerClient(p.getPlayerName());
                try {
                    waitingPlayerClient.showNotice("\n" + currentPlayer.getPlayerName() + " ha effettuato un piazzamento.");
                    waitingPlayerClient.addOnOtherPlayerWp(currentPlayer.getPlayerName(), setUpInfoUnit);
                    waitingPlayerClient.removeOnDraft(setUpInfoUnit);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + p.getPlayerName() + "'s WP and Draft Pool");
                    super.getControllerMaster().suspendPlayer(p.getPlayerName(), true);
                }
            }
        }

        //Updates the board of the player on duty.
        IFromServerToClient currentPlayerClient = super.getPlayerClient(currentPlayer.getPlayerName());
        super.sendNotificationToCurrentPlayer("\nPiazzamento effettuato correttamente.");
        try {
            currentPlayerClient.addOnOwnWp(setUpInfoUnit);
            currentPlayerClient.removeOnDraft(setUpInfoUnit);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + currentPlayer.getPlayerName() + "'s WP and Draft Pool");
            super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName(), true);
        }
    }

    /**
     * Shows the result of the movement of a die within a {@link it.polimi.ingsw.server.model.die.containers.WindowPatternCard}.
     * This method is used by various {@link ToolCard}s.
     * Note that the overwriting of the original Window Pattern Card is done only if all the effects of the card have been
     * used. The Window Pattern Card of the player on duty, however, is updated to the view even between the iterations.
     * If in the end the effect is illegal, it is brought back to the original one.
     * @param currentPlayer player on duty.
     * @param setUpInfoUnit information used by the view to update
     *                      the {@link it.polimi.ingsw.client.view.cli.die.WindowPatternCardView}.
     * @see MoveWithRestrictionsEffect
     * @see IgnoreValueRestrictionEffect
     * @see IgnoreColorRestrictionEffect
     * @see ColorBoundMoveWithRestrictionsEffect
     */
    public void showRearrangementResult(Player currentPlayer, SetUpInformationUnit setUpInfoUnit) {
        if (!this.isMoveLegal() && this.effectCounter < MAX_TOOL_EFFECTS_NUMBER) {
            return;
        } else if (!this.isMoveLegal() && this.effectCounter == MAX_TOOL_EFFECTS_NUMBER) {
            super.sendNotificationToCurrentPlayer("\nL'ultimo spostamento non è andato a buon fine, ripristino della" +
                    " situazione iniziale...");
            SetUpInformationUnit restoreUnit = this.backUpUnit.invertSourceAndDestination();
            this.updateOwnWpViewMovement(currentPlayer, restoreUnit);
            return;
        }

        //Updates the board of the player on duty.
        super.sendNotificationToCurrentPlayer("\nSpostamento n° " + this.effectCounter + " effettuato correttamente.");
        this.updateOwnWpViewMovement(currentPlayer, setUpInfoUnit);

        //Update other player wp and overwrite the original one of the player on duty on the model only if all
        //the iterations of the effect are completed.
        if (this.effectCounter == this.numberOfChosenEffects) {
            currentPlayer.getWindowPatternCard().overwriteOriginal();

            //Updates the board of the waiting players.
            List<Player> waitingPlayers = super.getControllerMaster().getCommonBoard().getPlayers();
            for (Player p : waitingPlayers) {
                if (!p.isSamePlayerAs(currentPlayer)) {
                    this.updateOthersWpViewMovement(currentPlayer, p, setUpInfoUnit);
                }
            }
        }

        this.backUpUnit = setUpInfoUnit;
    }

    /**
     * Shows the result of the swapping of a die between {@link DiceDraftPool} and
     * {@link it.polimi.ingsw.server.model.die.containers.RoundTrack}.
     * @param infoUnitDraft contains the information to update the {@link it.polimi.ingsw.client.view.cli.die.DieDraftPoolView}.
     * @param infoUnitRoundTrack contains the information to update the {@link it.polimi.ingsw.client.view.cli.die.RoundTrackView}.
     * @see SwapFromDraftPoolToRoundTrack
     */
    public void showDraftPoolRoundTrackSwap(SetUpInformationUnit infoUnitDraft, SetUpInformationUnit infoUnitRoundTrack) {
        if (!this.isMoveLegal()) {
            return;
        }

        //Copies back the updated draft pool and round track.
        super.getControllerMaster().getCommonBoard().getDraftPool().overwriteOriginal();
        super.getControllerMaster().getCommonBoard().getRoundTrack().overwriteOriginal();

        //Updates the draft pool and the round track for each player.
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
        for (Player p: players) {
            IFromServerToClient playerClient =
                    super.getControllerMaster().getConnectedPlayers().get(p.getPlayerName()).getClient();
            try {
                playerClient.showNotice("\nÈ stato scambiato un dado tra il Tracciato dei Round e la Riserva.");
                playerClient.removeOnDraft(infoUnitDraft);
                playerClient.addOnDraft(infoUnitDraft);
                playerClient.removeOnRoundTrack(infoUnitRoundTrack);
                playerClient.addOnRoundTrack(infoUnitRoundTrack);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + p.getPlayerName() + "'s Draft Pool " +
                        "and round track");
                super.getControllerMaster().suspendPlayer(p.getPlayerName(), true);
            }
        }
    }

    /**
     * Shows the result of the rolling of all dices of the {@link DiceDraftPool} to the
     * {@link it.polimi.ingsw.client.view.cli.die.DieDraftPoolView}.
     * @param rolledDice list of information needed to update the view with the newly rolled dice.
     * @see DraftValueEffect
     * @see RestrictedDiePlacementMove
     */
    public void showUpdatedDraft(List<SetUpInformationUnit> rolledDice) {
        if (!this.isMoveLegal()) {
            return;
        }

        //Copies back the updated draft pool.
        super.getControllerMaster().getCommonBoard().getDraftPool().overwriteOriginal();

        //Updates the draft pool for each player.
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
        for (Player p: players) {
            IFromServerToClient playerClient = super.getPlayerClient(p.getPlayerName());
            try {
                playerClient.showNotice("\nLa Riserva è stata aggiornata.");
                playerClient.setDraft(rolledDice);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + p.getPlayerName() + " draft pool");
                super.getControllerMaster().suspendPlayer(p.getPlayerName(), true);
            }
        }
    }

    /**
     * Shows the new die drafted after the player has chosen a die from the {@link DiceDraftPool}. It also sends the
     * new command available.
     * @param currentPlayer player on duty.
     * @param infoUnit contains the information needed to show the new {@link it.polimi.ingsw.server.model.die.Die}
     *                 (in the form of a {@link it.polimi.ingsw.client.view.cli.die.DieView}).
     * @see DraftValueEffect
     * @see it.polimi.ingsw.server.model.cards.tool.effects.swap.SwapFromDraftPoolToDiceBag
     */
    public void showDraftedDie(Player currentPlayer, SetUpInformationUnit infoUnit) {
        if (!isMoveLegal()) {
           return;
        }

        //Shows the newly drafted die to the player on duty.
        GameState gameState = super.getControllerMaster().getGameState();
        ToolCard card = super.getControllerMaster().getCommonBoard().getToolCardSlots()
                .get(gameState.getCurrentTurn().getToolSlotUsed()).getToolCard();
        IFromServerToClient currentPlayerClient = super.getPlayerClient(currentPlayer.getPlayerName());
        try {
            currentPlayerClient.showDie(infoUnit);
            Commands commandToSend = card.getCommandName().equals(Commands.TOOL6) ? Commands.EXTRA_TOOL6 : Commands.EXTRA_TOOL11;
            currentPlayerClient.showCommand(Collections.singletonList(commandToSend));
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show the newly drafted die to " + currentPlayer.getPlayerName());
            super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName(), true);
        }
    }

//----------------------------------------------------------
//                    UTILITY METHODS
//----------------------------------------------------------

    private boolean isMoveLegal() {
        return moveLegal;
    }

    public void setMoveLegal(boolean moveLegal) {
        this.moveLegal = moveLegal;
    }

    /**
     * This method checks if the current player is the same one as the one making the request.
     * @param username the color of the current player.
     * @return true if the given color matches the color of the current player.
     */
    private boolean isRequestFromCurrentPlayer(String username) {
        Player player = super.getControllerMaster().getGameState().getCurrentPlayer();
        return username.equals(player.getPlayerName());
    }

    /**
     * This method filters the list of {@link Commands} in input, removing all the ones related to the placement,
     * including {@link ToolCard}s.
     *
     * @param listToFilter list needing to be filtered.
     * @param playerName name of the player whose commands need to be filtered.
     */
    private List<Commands> filterPlacement(List<Commands> listToFilter, String playerName) {
        List<Commands> modifiedCurrentPlayerList = new ArrayList<>(listToFilter);

        //Remove the placement command.
        modifiedCurrentPlayerList.remove(Commands.PLACEMENT);

        //Remove the commands of each tool card drafted that implies a placement.
        for (ToolCardSlot slot : super.getControllerMaster().getCommonBoard().getToolCardSlots()) {
            if (slot.doesCardImplyPlacement()) {
                modifiedCurrentPlayerList.remove(slot.getToolCard().getCommandName());
            }
        }

        //Updates the dynamic commands.
        this.dynamicCommands.replace(playerName, modifiedCurrentPlayerList);

        return modifiedCurrentPlayerList;
    }

    /**
     * This method filters the list of {@link Commands} in input, removing all the ones related to {@link ToolCard}s.
     * @param listToFilter list needing to be filtered.
     * @param gameState object representing the state of the game.
     * @param playerName name of the player whose commands need to be filtered.
     */
    private List<Commands> filterTools(List<Commands> listToFilter, GameState gameState, String playerName) {
        List<Commands> modifiedCurrentPlayerList = new ArrayList<>(listToFilter);
        int slotID = gameState.getCurrentTurn().getToolSlotUsed();
        ToolCardSlot slotUsed = super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID);

        //Remove tool cards commands
        for (ToolCardSlot slot : super.getControllerMaster().getCommonBoard().getToolCardSlots()) {
            modifiedCurrentPlayerList.remove(slot.getToolCard().getCommandName());
        }

        //Removes the placement command if the card already used implied it.
        if (gameState.getCurrentTurn().isToolCardUsed() && slotUsed.doesCardImplyPlacement() &&
                gameState.getCurrentTurn().getDieCount() > 0) {
            modifiedCurrentPlayerList.remove(Commands.PLACEMENT);
        }

        //Updates the dynamic commands.
        this.dynamicCommands.replace(playerName, modifiedCurrentPlayerList);

        return modifiedCurrentPlayerList;
    }

    /**
     * This method is a security measure: if a request which doesn't come from the player on duty arrives, it handles it
     * by showing the player sending it the commands he is allowed to use.
     * If the request arrive regularly, this method does nothing.
     * @param playerName name of the player sending the request.
     * @return {@code true} if the request is illegal, {@code false} otherwise.
     */
    private boolean handleIllegalStateRequests(String playerName) {
        IFromServerToClient client = super.getPlayerClient(playerName);
        if (!isRequestFromCurrentPlayer(playerName)) {
            if (super.getControllerMaster().getSuspendedPlayers().contains(playerName)) {
                try {
                    client.showNotice("\nNon puoi effettuare mosse durante la sospensione!");
                    client.showCommand(Arrays.asList(Commands.RECONNECT, Commands.LOGOUT));
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Connection with " + playerName + " crashed while suspended " +
                            "and trying to perform a move");
                    super.getControllerMaster().suspendPlayer(playerName, true);
                }
                return true;
            } else {
                try {
                    client.showNotice("\nNon puoi effettuare questa mossa durante il turno degli altri!");
                    client.showCommand(this.waitingPlayersCommands);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Connection with " + playerName + " crashed while in an illegal" +
                            " state");
                    super.getControllerMaster().suspendPlayer(playerName, true);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * This methods is used to check if the placement should be made effective or not and, if so, updates all the values
     * related to the move.
     * @param gameState object representing the state of the game.
     * @param playerName name of the player whose move needs to be checked
     */
    private void checkAndResolveDefaultMoveLegality(GameState gameState, String playerName) {
        //Checks if the executeMove has been successful or not, and modifies the state of the turn accordingly.
        if (this.isMoveLegal()) {
            gameState.getCurrentTurn().setDiePlaced(true);
            super.sendNotificationToCurrentPlayer("\nPiazzamento effettuato!");

            if (gameState.getCurrentTurn().getDieCount() >= 2) {

                //Considers the second turn of the same player in the round.
                Turn turn = gameState.getTurnOrder().get(gameState.getTurnOrder().size() - gameState.getCurrentPlayerTurnIndex() - 1);
                turn.setPassed(true);
                super.sendNotificationToCurrentPlayer("\nRicorda che salterai il tuo prossimo turno in questo round!");
            }

            //Checks if the player has done everything he could. If he did, ends his turn; if not, shows him the commands.
            //The commands shown are relative to what the player can still do.
            if (!gameState.isCurrentTurnOver()) {
                List<Commands> filteredCommands = this.filterPlacement(this.dynamicCommands.get(playerName), playerName);
                super.sendNotificationToCurrentPlayer("\nPuoi ancora usare una Carta Strumento che" +
                        " non implichi un piazzamento, visualizzare informazioni della plancia oppure passare.");
                super.sendCommandsToCurrentPlayer(filteredCommands);
            } else {
                this.endTurn(EVERYTHING_DONE);
            }
        } else {
            gameState.getCurrentTurn().setDiePlaced(false);
        }
    }

    /**
     * This methods is used to check if the tool card should be made effective or not and, if so, updates all the values
     * related to the move.
     * @param gameState object representing the state of the game.
     * @param slotID    ID of the slot where the {@link ToolCard} is in.
     * @param toolCard  {@link ToolCard} that has been used.
     * @param playerName name of the player whose Tool Card move needs to be checked.
     */
    private void checkAndResolveToolMoveLegality(GameState gameState, int slotID, ToolCard toolCard, String playerName) {
        if (this.isMoveLegal() && this.effectCounter == this.numberOfChosenEffects) {
            gameState.getCurrentTurn().setToolCardUsed(true);
            this.updateFavorTokensAndToolCost(gameState, slotID, toolCard);

            if (gameState.getCurrentTurn().getDieCount() >= 2) {

                //Considers the second turn of the same player in the round.
                Turn turn = gameState.getTurnOrder().get(gameState.getTurnOrder().size() - gameState.getCurrentPlayerTurnIndex() - 1);
                turn.setPassed(true);
                super.sendNotificationToCurrentPlayer("\nRicorda che salterai il tuo prossimo turno in questo round!");
            }

            if (toolCard.impliesPlacement() && gameState.getCurrentTurn().getDieCount() > 0) {
                gameState.getCurrentTurn().setDiePlaced(true);
                super.sendNotificationToCurrentPlayer("\nLa Carta Strumento che hai usato implicava un piazzamento");
            }

            //Checks if the player has done everything he could. If he did, ends his turn; if not, shows him the
            //commands still available to him.
            if (!gameState.isCurrentTurnOver() && toolCard.getEffectBuilder().requiresOnlyOneInteraction()) {
                List<Commands> filteredCommands = this.filterTools(this.dynamicCommands.get(playerName), gameState, playerName);
                super.sendNotificationToCurrentPlayer("\nPuoi ancora effettuare un piazzamento, visualizzare informazioni della " +
                        "plancia oppure passare.");
                super.sendCommandsToCurrentPlayer(filteredCommands);
            } else if (gameState.isCurrentTurnOver() && toolCard.getEffectBuilder().requiresOnlyOneInteraction()) {
                this.endTurn(EVERYTHING_DONE);
            }
        } else {
            gameState.getCurrentTurn().setToolCardUsed(false);
        }
    }

    /**
     * This method checks if the {@link ToolCard} the player selected can be used in that turn and if he has enough
     * Favor Tokens to spend.
     * In case the card cannot be used, a new suitable set of {@link Commands} is sent to the player.
     */
    private void checkToolCardAvailability(GameState gameState, int slotID, String playerName) {
        ToolCardSlot slot = super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID);
        int playerFavorTokens = gameState.getCurrentPlayer().getFavorTokens();
        int turnNumber = gameState.getCurrentPlayerTurnIndex() < (gameState.getTurnOrder().size() / 2) ? FIRST_TURN : SECOND_TURN;

        if (!slot.getToolCard().canBeUsed(turnNumber)) {
            super.sendNotificationToCurrentPlayer("\nNon puoi usare questa Carta Strumento in questo turno!");
            this.setMoveLegal(false);
            List<Commands> updatedCommands = this.dynamicCommands.get(playerName);
            updatedCommands.remove(slot.getToolCard().getCommandName());
            super.sendCommandsToCurrentPlayer(updatedCommands);
        } else if (!slot.canCardBePaid(playerFavorTokens)) {
            super.sendNotificationToCurrentPlayer("\nNon hai abbastanza Segnalini Favore per utilizzare la Carta Strumento selezionata");
            this.setMoveLegal(false);
            List<Commands> updatedCommands = this.dynamicCommands.get(playerName);
            updatedCommands.remove(slot.getToolCard().getCommandName());
            super.sendCommandsToCurrentPlayer(updatedCommands);
        } else {
            gameState.getCurrentTurn().setToolSlotUsed(slotID);
            this.setMoveLegal(true);
        }
    }

    /**
     * This method is used to update both model and view tool cards' cost and the number of favor token of the player
     * performing the move.
     * @param gameState object representing the state of the game.
     * @param slotID ID of the slot where the {@link ToolCard} is in.
     * @param toolCard {@link ToolCard} that has been used.
     */
    private void updateFavorTokensAndToolCost(GameState gameState, int slotID, ToolCard toolCard) {
        int toolCost = super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID).getCost();
        int oldPlayerFavorTokens = gameState.getCurrentPlayer().getFavorTokens();
        gameState.getCurrentPlayer().setFavorTokens(oldPlayerFavorTokens - toolCost);
        IFromServerToClient currentPlayerClient = super.getPlayerClient(gameState.getCurrentPlayer().getPlayerName());
        try {
            currentPlayerClient.updateFavTokenPlayer(oldPlayerFavorTokens - toolCost);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + gameState.getCurrentPlayer().getPlayerName() +
                    "'s Favor Tokens.");
            super.getControllerMaster().suspendPlayer(gameState.getCurrentPlayer().getPlayerName(), true);
        }

        if (super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID).getCost() == 1) {
            super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID).setCost(2);
            super.broadcastNotification("\nIl costo della Carta Strumento " + toolCard.getName() + " è aumentato a "
                    + super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID).getCost() +
                    " Segnalini Favore.");
            for(String playerName: super.getControllerMaster().getConnectedPlayers().keySet()) {
                if(!super.getControllerMaster().getSuspendedPlayers().contains(playerName)) {
                    IFromServerToClient playerClient = super.getPlayerClient(playerName);
                    try {
                        playerClient.updateToolCost(slotID, 2);
                    } catch (BrokenConnectionException e) {
                        SagradaLogger.log(Level.SEVERE,  IMPOSSIBLE_TO_UPDATE + "the Tool Card cost to " + playerName);
                        super.getControllerMaster().suspendPlayer(playerName, true);
                    }
                }
            }
        }
    }

    /**
     * This method updates the {@link it.polimi.ingsw.server.model.die.containers.WindowPatternCard} of the current player.
     * @param currentPlayer player whose {@link it.polimi.ingsw.client.view.cli.die.WindowPatternCardView} is updated.
     * @param infoUnit object containing all the information needed to perform the update.
     */
    private void updateOwnWpViewMovement(Player currentPlayer, SetUpInformationUnit infoUnit) {
        IFromServerToClient currentPlayerClient = super.getPlayerClient(currentPlayer.getPlayerName());
        try {
            currentPlayerClient.removeOnOwnWp(infoUnit);
            currentPlayerClient.addOnOwnWp(infoUnit);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + currentPlayer.getPlayerName() + "'s WP");
            super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName(), true);
        }
    }

    /**
     * This method updates the {@link it.polimi.ingsw.server.model.die.containers.WindowPatternCard} of a waiting player.
     * @param currentPlayer player whose {@link it.polimi.ingsw.client.view.cli.die.WindowPatternCardView} is updated.
     * @param waitingPlayer player whose version of the current player wp has to be updated.
     * @param infoUnit object containing all the information needed to perform the update.
     */
    private void updateOthersWpViewMovement(Player currentPlayer, Player waitingPlayer, SetUpInformationUnit infoUnit) {
        IFromServerToClient waitingPlayerClient = super.getPlayerClient(waitingPlayer.getPlayerName());
        try {
            waitingPlayerClient.showNotice("\n" + currentPlayer.getPlayerName() + " ha spostato un dado nella" +
                    " sua vetrata.");

            //Do the rollback to others only if this is at least the second time the player uses this effect
            //in a single card.
            if (this.effectCounter > 1) {
                waitingPlayerClient.removeOnOtherPlayerWp(currentPlayer.getPlayerName(), this.backUpUnit);
                waitingPlayerClient.addOnOtherPlayerWp(currentPlayer.getPlayerName(), this.backUpUnit);
            }

            waitingPlayerClient.removeOnOtherPlayerWp(currentPlayer.getPlayerName(), infoUnit);
            waitingPlayerClient.addOnOtherPlayerWp(currentPlayer.getPlayerName(), infoUnit);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + waitingPlayer.getPlayerName() + "'s WP");
            super.getControllerMaster().suspendPlayer(waitingPlayer.getPlayerName(), true);
        }
    }
}

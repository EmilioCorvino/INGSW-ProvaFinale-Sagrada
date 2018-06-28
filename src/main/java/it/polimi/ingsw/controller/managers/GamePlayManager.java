package it.polimi.ingsw.controller.managers;


import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.cards.ToolCardSlot;
import it.polimi.ingsw.model.cards.tool.ToolCard;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.DiceDraftPool;
import it.polimi.ingsw.model.move.DiePlacementMove;
import it.polimi.ingsw.model.move.IMove;
import it.polimi.ingsw.model.move.RestrictedDiePlacementMove;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.turn.GameState;
import it.polimi.ingsw.model.turn.Turn;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

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
     * Says if the move done is legal or not. It's set by executeMove methods and at the start of each turn.
     */
    private boolean moveLegal = true;

    private static final String EVERYTHING_DONE = "\nHai effettuato tutte le operazioni possibili in un turno, " +
            "passaggio al giocatore successivo...\n";

    private static final String IMPOSSIBLE_TO_UPDATE = "Impossible to update ";

    private static final int FIRST_TURN = 0;

    private static final int SECOND_TURN = 1;

    /**
     * This constructor, other than setting the controller master, also initializes the lists of commands to be shown
     * both to the player on duty ({@link #currentPlayerCommands}) and to the others ({@link #waitingPlayersCommands}).
     *
     * @param controllerMaster main controller class.
     */
    public GamePlayManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        this.currentPlayerCommands = new ArrayList<>();

        //Gets the tool cards commands from the model.
        for (ToolCardSlot slot : controllerMaster.getCommonBoard().getToolCardSlots()) {
            this.currentPlayerCommands.add(slot.getToolCard().getCommandName());
        }

        this.currentPlayerCommands.addAll(Arrays.asList(Commands.PLACEMENT, Commands.OTHER_PLAYERS_MAPS,
                Commands.PUBLIC_OBJ_CARDS, Commands.PRIVATE_OBJ_CARD, Commands.AVAILABLE_TOOL_CARDS,
                Commands.ROUND_TRACK, Commands.END_TURN, Commands.LOGOUT));

        this.waitingPlayersCommands = new ArrayList<>(Arrays.asList(Commands.OTHER_PLAYERS_MAPS,
                Commands.PUBLIC_OBJ_CARDS, Commands.PRIVATE_OBJ_CARD, Commands.AVAILABLE_TOOL_CARDS,
                Commands.ROUND_TRACK, Commands.LOGOUT));
    }

    public List<Commands> getWaitingPlayersCommands() {
        return waitingPlayersCommands;
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
            super.getControllerMaster().getEndGameManager().quitGame();
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
            this.broadcastNotification("\nÈ INIZIATO IL ROUND N° " + gameState.getActualRound() + "\n" +
                    "La riserva è stata aggiornata:");
            for (Player player : players) {
                IFromServerToClient client = super.getPlayerClient(player.getPlayerName());
                try {
                    client.setDraft(super.draftPoolConverter());
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Connection broken while updating draft pool at the start" +
                            "of a round", e);
                    super.getControllerMaster().suspendPlayer(player.getPlayerName());
                }
            }

            this.startTurn(gameState.getCurrentTurn());
        } else {
            super.getControllerMaster().getEndGameManager().computeRank();
        }
    }

    /**
     * This method starts the turn of a player, sending the correct {@link Commands} to him and the waiting players.
     * @param currentPlayerTurn the turn of the current player.
     */
    private void startTurn(Turn currentPlayerTurn) {
        this.setMoveLegal(true);
        GameState gameState = super.getControllerMaster().getGameState();

        if (gameState.isCurrentTurnOver()) {
            this.endTurn("\nDevi saltare questo turno!\n");
            return;
        }

        if (super.getControllerMaster().getSuspendedPlayers().contains(currentPlayerTurn.getPlayer().getPlayerName())) {
            super.broadcastNotification("\n" + currentPlayerTurn.getPlayer().getPlayerName() + " è sospeso: il suo turno" +
                    " verrà saltato.\n");
            this.endTurn("\nSei in stato di sospensione, il tuo turno verrà saltato.\n");
        }

        //Shows the available commands to the players waiting.
        List<Player> waitingPlayers = super.getControllerMaster().getCommonBoard().getPlayers();
        for (Player player : waitingPlayers) {
            if (!player.isSamePlayerAs(currentPlayerTurn.getPlayer())) {
                IFromServerToClient waitingPlayerClient = super.getPlayerClient(player.getPlayerName());
                try {
                    waitingPlayerClient.showNotice("\nÈ il turno di " + gameState.getCurrentPlayer().getPlayerName() + "\n");
                    waitingPlayerClient.showCommand(this.waitingPlayersCommands);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Impossible to show commands " +
                            "to the waiting players", e);
                    super.getControllerMaster().suspendPlayer(player.getPlayerName());
                }
            }
        }

        //Check whether this is the first or the second turn of this player in this round.
        String turnNumber =
                gameState.getCurrentPlayerTurnIndex() < (gameState.getTurnOrder().size() / 2) ? "PRIMO" : "SECONDO";

        super.sendNotificationToCurrentPlayer("\nÈ IL TUO " + turnNumber + " TURNO DEL ROUND!\n");
        super.sendCommandsToCurrentPlayer(this.currentPlayerCommands);
        this.startTimer(currentPlayerTurn);
        this.checkIfPlayerIsSuspended(currentPlayerTurn.getPlayer().getPlayerName());
    }

    /**
     * Starts the timer of the turn. If it can't be loaded from file, a back up value is used.
     * @param turn turn of the player to suspend in case his turn isn't over when the timer expires. It has a reference
     *             to said {@link Player}.
     */
    private void startTimer(Turn turn) {
        Timer timer = new Timer();

        //Back up value.
        long timeOut = BACK_UP_TIMER;

        //Value read from file. If the loading is successful, it overwrites the back up.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(TIMER_FILE)))) {
            timeOut = Long.parseLong(reader.readLine());
            SagradaLogger.log(Level.CONFIG, "Timer successfully loaded from file. Its value is: " + timeOut / 1000 + "s");
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to load the turn timer from file.", e);
        }
        SagradaLogger.log(Level.INFO, turn.getPlayer().getPlayerName() + " turn timer is started");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SagradaLogger.log(Level.WARNING, turn.getPlayer().getPlayerName() + " turn timer is expired");
                if (!turn.isTurnCompleted()) {
                    getControllerMaster().suspendPlayer(turn.getPlayer().getPlayerName());
                    endTurn("\nIl tempo a tua disposizione è terminato. Sei stato sospeso per inattività.\n");
                }
            }
        }, timeOut);
    }

    /**
     * Ends the turn, incrementing the index of the current player in the turn order list at {@link GameState}.
     * Then starts a new turn. If the player is the only one left, declare him as winner and ends the match.
     * @param message communication to send to the player, informing him why is his turn over.
     */
    private void endTurn(String message) {
        GameState gameState = super.getControllerMaster().getGameState();
        IFromServerToClient currentPlayerClient = super.getPlayerClient(gameState.getCurrentPlayer().getPlayerName());
        try {
            currentPlayerClient.showNotice(message);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection broken while ending the turn", e);
            super.getControllerMaster().suspendPlayer(gameState.getCurrentPlayer().getPlayerName());
        }

        //Checks if there is only one (or less) player left playing. In that case, ends the match.
        if (super.getControllerMaster().getSuspendedPlayers().size() >= (super.getControllerMaster().getConnectedPlayers().size() - 1)) {
            for(String playerName: super.getControllerMaster().getConnectedPlayers().keySet()) {
                if(!super.getControllerMaster().getSuspendedPlayers().contains(playerName)) {
                    IFromServerToClient client = super.getPlayerClient(playerName);
                    EndGameManager endGameManager = super.getControllerMaster().getEndGameManager();
                    try {
                        client.showNotice("\nSei l'ultimo giocatore rimasto, HAI VINTO PER ABBANDONO!\n");
                        client.showCommand(endGameManager.endGameCommands);
                        return;
                    } catch (BrokenConnectionException e) {
                        SagradaLogger.log(Level.SEVERE, playerName + " disconnected. Preparing a new room...");
                        endGameManager.quitGame();
                    }
                }
            }
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
     * {@link it.polimi.ingsw.model.die.containers.DiceDraftPool} to the space on the
     * {@link it.polimi.ingsw.model.die.containers.RoundTrack} corresponding to the current round.
     */
    private void moveRemainingDiceFromDraftPoolToRoundTrack(GameState gameState, CommonBoard board) {
        if (!board.getDraftPool().getAvailableDice().isEmpty()) {
            board.getRoundTrack().setRoundToBeUpdated(gameState.getActualRound() - 1);
            board.getRoundTrack().createCopy();
            int actualDraftSize = board.getDraftPool().getAvailableDice().size();
            List<SetUpInformationUnit> diceToSend = new ArrayList<>();

            for (int i = 0; i < actualDraftSize; i++) {
                Die dieToMove = board.getDraftPool().getAvailableDice().remove(0);
                board.getRoundTrack().addDie(dieToMove);
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
                            SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + playerName + " Round Track", e);
                            super.getControllerMaster().suspendPlayer(playerName);
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
     * This method is used to perform a standard {@link DiePlacementMove}. It can handle weird situations in which a
     * player tries to perform moves not in his turn.
     * It also checks if a placement has already been done and in that case it shows to the player new suitable
     * {@link Commands}.
     * @param info       object containing the information needed to update the model.
     * @param playerName name of the player trying to perform the move.
     * @see DiePlacementMove
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
                List<Commands> filteredCommands = this.filterPlacement(this.currentPlayerCommands);
                super.sendNotificationToCurrentPlayer("\nHai già effettuato un piazzamento in questo turno! Puoi ancora " +
                        "usare una Carta Strumento che non lo implichi, visualizzare informazioni della plancia" +
                        " oppure passare.\n");
                super.sendCommandsToCurrentPlayer(filteredCommands);
                this.checkIfPlayerIsSuspended(playerName);
            } else {
                IMove move = new DiePlacementMove();
                move.executeMove(this, info);
                this.checkAndResolveDefaultMoveLegality(gameState);
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
        if(this.handleIllegalStateRequests(playerName)) {
            return;
        }

        if (!gameState.isCurrentTurnOver()) {

            //If the player has already used a Tool Card, tells him and shows him a new appropriate list of commands.
            if (gameState.getCurrentTurn().isToolCardUsed()) {
                List<Commands> filteredCommands = this.filterTools(this.currentPlayerCommands, gameState);
                super.sendNotificationToCurrentPlayer("\nHai già usato una Carta Strumento in questo turno! Puoi ancora " +
                        "piazzare un dado (se la carta usata non implicava un piazzamento), visualizzare informazioni " +
                        "della plancia, oppure passare.\n");
                super.sendCommandsToCurrentPlayer(filteredCommands);
                this.checkIfPlayerIsSuspended(playerName);
            } else {
                ToolCardSlot slot = super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID);
                this.checkToolCardAvailability(gameState, slotID);
                for (int i = 0; i < infoUnits.size(); i++) {
                    if (this.isMoveLegal()) {
                        slot.getToolCard().getCardEffects().get(i).executeMove(this, infoUnits.get(i));
                    }
                }

                //Checks if the executeMove has been successful or not, and modifies the state of the turn accordingly.
                this.checkAndResolveToolMoveLegality(gameState, slotID, slot.getToolCard());
            }
        } else {
            this.endTurn("\nHai già effettuato tutte le operazioni possibili in un turno, " +
                    "passaggio al giocatore successivo...\n");
        }
    }

    /**
     * This method is called to perform a placement where the player has to put a specific die into his
     * {@link it.polimi.ingsw.model.die.containers.WindowPatternCard}. It is reached through the usage of some
     * {@link ToolCard}.
     * @param infoUnit object containing the information needed to update the model.
     * @param playerName name of the player trying to perform the move.
     * @see it.polimi.ingsw.model.cards.tool.ValueEffects.DraftValueEffect
     * @see it.polimi.ingsw.model.cards.tool.SwapEffect.SwapFromDraftPoolToDicebag
     */
    public void performRestrictedPlacement(SetUpInformationUnit infoUnit, String playerName) {
        GameState gameState = super.getControllerMaster().getGameState();

        //Checks if the request comes from the current player. If not, shows the player the commands of a waiting player.
        //This is just a security measure, it should not be possible to be in this situation.
        if(this.handleIllegalStateRequests(playerName)) {
            return;
        }

        IMove restrictedPlacement = new RestrictedDiePlacementMove();
        restrictedPlacement.executeMove(this, infoUnit);

        if(!this.isMoveLegal()) {
            super.sendNotificationToCurrentPlayer(gameState.getCurrentPlayer().getWindowPatternCard().getErrorMessage() +
                    " Prova a piazzare il dado in un'altra posizione.\n");

            //Only the extra command for tool 6 is sent because even in the case of tool 11, at this point, the value
            //of the die has already been chosen.
            super.sendCommandsToCurrentPlayer(Collections.singletonList(Commands.EXTRA_TOOL6));
            this.checkIfPlayerIsSuspended(playerName);
        } else {
            if(gameState.isCurrentTurnOver()) {
                this.endTurn(EVERYTHING_DONE);
            } else {
                super.sendNotificationToCurrentPlayer("\nNon è possibile piazzare il dado in alcuna posizione. Verrà aggiunto alla" +
                        " riserva. Puoi ancora effettuare un piazzamento standard.\n");
                List<Commands> updatedCommands = this.filterTools(this.currentPlayerCommands, gameState);
                updatedCommands.add(0, Commands.PLACEMENT);
                super.sendCommandsToCurrentPlayer(updatedCommands);
                this.checkIfPlayerIsSuspended(playerName);
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
        this.endTurn("\nHai scelto di passare il turno.\n");
    }

//----------------------------------------------------------
//                    SERVER RESPONSE METHODS
//----------------------------------------------------------

    /**
     * Shows the result of the default placement move to the player on duty and the waiting players.
     * Note that this method can be also called to communicate the result of some of the {@link ToolCard}s.
     * @param currentPlayer player on duty.
     * @param setUpInfoUnit information used by the view to update
     *                      the {@link it.polimi.ingsw.view.cli.die.WindowPatternCardView} and the
     *                      {@link it.polimi.ingsw.view.cli.die.DieDraftPoolView}.
     * @see DiePlacementMove
     * @see it.polimi.ingsw.model.cards.tool.ValueEffects.ChooseValueEffect
     * @see it.polimi.ingsw.model.cards.tool.ValueEffects.OppositeValueEffect
     * @see it.polimi.ingsw.model.cards.tool.PlacementEffect.AdjacentCellsRestrictionEffect
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
                    waitingPlayerClient.showNotice("\n" + currentPlayer.getPlayerName() + " ha effettuato un piazzamento.\n");
                    waitingPlayerClient.addOnOtherPlayerWp(currentPlayer.getPlayerName(), setUpInfoUnit);
                    waitingPlayerClient.removeOnDraft(setUpInfoUnit);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + p.getPlayerName() + "'s WP and Draft Pool", e);
                    super.getControllerMaster().suspendPlayer(p.getPlayerName());
                }
            }
        }

        //Updates the board of the player on duty.
        IFromServerToClient currentPlayerClient = super.getPlayerClient(currentPlayer.getPlayerName());
        super.sendNotificationToCurrentPlayer("\nPiazzamento effettuato correttamente.\n");
        try {
            currentPlayerClient.addOnOwnWp(setUpInfoUnit);
            currentPlayerClient.removeOnDraft(setUpInfoUnit);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + currentPlayer.getPlayerName() + "'s WP and Draft Pool", e);
            super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName());
        }
    }

    /**
     * Shows the result of the movement of a die within a {@link it.polimi.ingsw.model.die.containers.WindowPatternCard}.
     * This method is used by various {@link ToolCard}s.
     * @param currentPlayer player on duty.
     * @param setUpInfoUnit information used by the view to update
     *                      the {@link it.polimi.ingsw.view.cli.die.WindowPatternCardView}.
     * @see it.polimi.ingsw.model.cards.tool.PlacementEffect.PlacementRestrictionEffect
     * @see it.polimi.ingsw.model.cards.tool.PlacementEffect.ColorRestrictionEffect
     * @see it.polimi.ingsw.model.cards.tool.PlacementEffect.ValueRestrictionEffect
     * @see it.polimi.ingsw.model.cards.tool.PlacementEffect.ColorPlacementRestrictionEffect
     */
    public void showRearrangementResult(Player currentPlayer, SetUpInformationUnit setUpInfoUnit) {
        if(!this.isMoveLegal()) {
            return;
        }

        //Copies back the updated wp.
        currentPlayer.getWindowPatternCard().overwriteOriginal();

        //Updates the board of the player on duty.
        IFromServerToClient currentPlayerClient = super.getPlayerClient(currentPlayer.getPlayerName());
        super.sendNotificationToCurrentPlayer("\nSpostamento effettuato correttamente.\n");
        try {
            currentPlayerClient.removeOnOwnWp(setUpInfoUnit);
            currentPlayerClient.addOnOwnWp(setUpInfoUnit);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + currentPlayer.getPlayerName() + "'s WP", e);
            super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName());
        }

        //Updates the board of the waiting players.
        List<Player> waitingPlayers = super.getControllerMaster().getCommonBoard().getPlayers();
        for (Player p : waitingPlayers) {
            if (!p.isSamePlayerAs(currentPlayer)) {
                IFromServerToClient waitingPlayerClient = super.getPlayerClient(p.getPlayerName());
                try {
                    waitingPlayerClient.showNotice("\n" + currentPlayer.getPlayerName() + " ha spostato un dado nella" +
                            " sua vetrata.\n");
                    waitingPlayerClient.removeOnOtherPlayerWp(currentPlayer.getPlayerName(), setUpInfoUnit);
                    waitingPlayerClient.addOnOtherPlayerWp(currentPlayer.getPlayerName(), setUpInfoUnit);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + p.getPlayerName() + "'s WP", e);
                    super.getControllerMaster().suspendPlayer(p.getPlayerName());
                }
            }
        }
    }

    /**
     * Shows the result of the swapping of a die between {@link DiceDraftPool} and
     * {@link it.polimi.ingsw.model.die.containers.RoundTrack}.
     * @param infoUnitDraft contains the information to update the {@link it.polimi.ingsw.view.cli.die.DieDraftPoolView}.
     * @param infoUnitRoundTrack contains the information to update the {@link it.polimi.ingsw.view.cli.die.RoundTrackView}.
     * @see it.polimi.ingsw.model.cards.tool.SwapEffect.SwapFromDraftpoolToRoundTrack
     */
    public void showDraftPoolRoundTrackSwap(SetUpInformationUnit infoUnitDraft, SetUpInformationUnit infoUnitRoundTrack) {
        if(!this.isMoveLegal()) {
            return;
        }

        //Copies bck the updated draft pool and round track.
        super.getControllerMaster().getCommonBoard().getDraftPool().overwriteOriginal();
        super.getControllerMaster().getCommonBoard().getRoundTrack().overwriteOriginal();

        //Updates the draft pool and the round track for each player.
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
        for(Player p: players) {
            IFromServerToClient playerClient =
                    super.getControllerMaster().getConnectedPlayers().get(p.getPlayerName()).getClient();
            try {
                playerClient.showNotice("\nÈ stato scambiato un dado tra il Tracciato dei Round e la Riserva.\n");
                playerClient.removeOnDraft(infoUnitDraft);
                playerClient.addOnDraft(infoUnitDraft);
                playerClient.removeOnRoundTrack(infoUnitRoundTrack);
                playerClient.addOnRoundTrack(infoUnitRoundTrack);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + p.getPlayerName() + "'s Draft Pool " +
                        "and round track", e);
                super.getControllerMaster().suspendPlayer(p.getPlayerName());
            }
        }
    }

    /**
     * Shows the result of the rolling of all dices of the {@link DiceDraftPool} to the
     * {@link it.polimi.ingsw.view.cli.die.DieDraftPoolView}.
     * @param rolledDice list of information needed to update the view with the newly rolled dice.
     * @see it.polimi.ingsw.model.cards.tool.ValueEffects.DraftValueEffect
     * @see RestrictedDiePlacementMove
     */
    public void showUpdatedDraft(List<SetUpInformationUnit> rolledDice) {
        if(!this.isMoveLegal()) {
            return;
        }

        //Copies back the updated draft pool.
        super.getControllerMaster().getCommonBoard().getDraftPool().overwriteOriginal();

        //Updates the draft pool for each player.
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
        for(Player p: players) {
            IFromServerToClient playerClient = super.getPlayerClient(p.getPlayerName());
            try {
                playerClient.showNotice("\nLa Riserva è stata aggiornata.\n");
                playerClient.setDraft(rolledDice);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, IMPOSSIBLE_TO_UPDATE + p.getPlayerName() + " draft pool", e);
                super.getControllerMaster().suspendPlayer(p.getPlayerName());
            }
        }
    }

    /**
     * Shows the new die drafted after the player has chosen a die from the {@link DiceDraftPool}. It also sends the
     * new command available.
     * @param currentPlayer player on duty.
     * @param infoUnit contains the information needed to show the new {@link it.polimi.ingsw.model.die.Die}
     *                 (in the form of a {@link it.polimi.ingsw.view.cli.die.DieView}).
     * @see it.polimi.ingsw.model.cards.tool.ValueEffects.DraftValueEffect
     * @see it.polimi.ingsw.model.cards.tool.SwapEffect.SwapFromDraftPoolToDicebag
     */
    public void showDraftedDie(Player currentPlayer, SetUpInformationUnit infoUnit) {
        if(!isMoveLegal()) {
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
            super.getControllerMaster().suspendPlayer(currentPlayer.getPlayerName());
            this.checkIfPlayerIsSuspended(currentPlayer.getPlayerName());
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
     */
    private List<Commands> filterPlacement(List<Commands> listToFilter) {
        List<Commands> modifiedCurrentPlayerList = new ArrayList<>(listToFilter);

        //Remove the placement command.
        modifiedCurrentPlayerList.remove(Commands.PLACEMENT);

        //Remove the commands of each tool card drafted that implies a placement.
        for (ToolCardSlot slot : super.getControllerMaster().getCommonBoard().getToolCardSlots()) {
            if (slot.doesCardImplyPlacement()) {
                modifiedCurrentPlayerList.remove(slot.getToolCard().getCommandName());
            }
        }

        return modifiedCurrentPlayerList;
    }

    /**
     * This method filters the list of {@link Commands} in input, removing all the ones related to {@link ToolCard}s.
     * @param listToFilter list needing to be filtered.
     * @param gameState object representing the state of the game.
     */
    private List<Commands> filterTools(List<Commands> listToFilter, GameState gameState) {
        List<Commands> modifiedCurrentPlayerList = new ArrayList<>(listToFilter);
        int slotID = gameState.getCurrentTurn().getToolSlotUsed();
        ToolCardSlot slotUsed = super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID);

        //Remove tool cards commands
        for (ToolCardSlot slot : super.getControllerMaster().getCommonBoard().getToolCardSlots()) {
            modifiedCurrentPlayerList.remove(slot.getToolCard().getCommandName());
        }

        //Removes the placement command if the card already used implied it.
        if (gameState.getCurrentTurn().isToolCardUsed() && slotUsed.doesCardImplyPlacement()) {
            modifiedCurrentPlayerList.remove(Commands.PLACEMENT);
        }

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
            try {
                client.showNotice("Non puoi effettuare questa mossa durante il turno degli altri!");
                client.showCommand(this.waitingPlayersCommands);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection with the client crashed while performing a " +
                        "default move", e);
                super.getControllerMaster().suspendPlayer(playerName);
            }
            return true;
        }
        return false;
    }

    /**
     * This methods is used to check if the placement should be made effective or not and, if so, updates all the values
     * related to the move.
     * @param gameState object representing the state of the game.
     */
    private void checkAndResolveDefaultMoveLegality(GameState gameState) {
        //Checks if the executeMove has been successful or not, and modifies the state of the turn accordingly.
        if (this.isMoveLegal()) {
            gameState.getCurrentTurn().setDiePlaced(true);
            gameState.getCurrentTurn().incrementDieCount();
            super.sendNotificationToCurrentPlayer("\nPiazzamento effettuato!\n");

            //Checks if the player has done everything he could. If he did, ends his turn; if not, shows him the commands.
            //The commands shown are relative to what the player can still do.
            if (!gameState.isCurrentTurnOver()) {
                List<Commands> filteredCommands = this.filterPlacement(this.currentPlayerCommands);
                super.sendNotificationToCurrentPlayer("\nPuoi ancora usare una Carta Strumento che" +
                        " non implichi un piazzamento, visualizzare informazioni della plancia oppure passare.\n");
                super.sendCommandsToCurrentPlayer(filteredCommands);
                this.checkIfPlayerIsSuspended(gameState.getCurrentPlayer().getPlayerName());
            } else {
                this.endTurn(EVERYTHING_DONE);
            }
        } else {
            gameState.getCurrentTurn().setDiePlaced(false);
            this.checkIfPlayerIsSuspended(gameState.getCurrentPlayer().getPlayerName());
        }
    }

    /**
     * This methods is used to check if the tool card should be made effective or not and, if so, updates all the values
     * related to the move.
     * @param gameState object representing the state of the game.
     * @param slotID    ID of the slot where the {@link ToolCard} is in.
     * @param toolCard  {@link ToolCard} that has been used.
     */
    private void checkAndResolveToolMoveLegality(GameState gameState, int slotID, ToolCard toolCard) {
        if (this.isMoveLegal()) {
            gameState.getCurrentTurn().setToolCardUsed(true);
            this.updateFavorTokensAndToolCost(gameState, slotID, toolCard);

            if (gameState.getCurrentTurn().getDieCount() >= 2) {

                //Considers the second turn of the same player in the round.
                Turn turn = gameState.getTurnOrder().get(gameState.getTurnOrder().size() - gameState.getCurrentPlayerTurnIndex() - 1);
                turn.setPassed(true);
                super.sendNotificationToCurrentPlayer("\nRicorda che salterai il tuo prossimo turno in questo round!\n");
            }

            if (toolCard.impliesPlacement()) {
                gameState.getCurrentTurn().setDiePlaced(true);
                gameState.getCurrentTurn().incrementDieCount();
                super.sendNotificationToCurrentPlayer("\nLa Carta Strumento che hai usato implicava un piazzamento");
            }

            //Checks if the player has done everything he could. If he did, ends his turn; if not, shows him the
            //commands still available to him.
            if (!gameState.isCurrentTurnOver() && !toolCard.getEffectBuilder().requiresMultipleInteractions()) {
                List<Commands> filteredCommands = this.filterTools(this.currentPlayerCommands, gameState);
                super.sendNotificationToCurrentPlayer("\nPuoi ancora effettuare un piazzamento, visualizzare informazioni della " +
                        "plancia oppure passare.\n");
                super.sendCommandsToCurrentPlayer(filteredCommands);
                this.checkIfPlayerIsSuspended(gameState.getCurrentPlayer().getPlayerName());
            } else if (gameState.isCurrentTurnOver() && !toolCard.getEffectBuilder().requiresMultipleInteractions()) {
                this.endTurn(EVERYTHING_DONE);
            }
        } else {
            gameState.getCurrentTurn().setToolCardUsed(false);
            this.checkIfPlayerIsSuspended(gameState.getCurrentPlayer().getPlayerName());
        }
    }

    /**
     * This method checks if the {@link ToolCard} the player selected can be used in that turn and if he has enough
     * Favor Tokens to spend.
     * In case the card cannot be used, a new suitable set of {@link Commands} is sent to the player.
     */
    private void checkToolCardAvailability(GameState gameState, int slotID) {
        ToolCardSlot slot = super.getControllerMaster().getCommonBoard().getToolCardSlots().get(slotID);
        int playerFavorTokens = gameState.getCurrentPlayer().getFavorTokens();
        int turnNumber = gameState.getCurrentPlayerTurnIndex() < (gameState.getTurnOrder().size() / 2) ? FIRST_TURN : SECOND_TURN;

        if (!slot.getToolCard().canBeUsed(turnNumber)) {
            super.sendNotificationToCurrentPlayer("\nNon puoi usare questa Carta Strumento in questo turno!\n");
            this.setMoveLegal(false);
            List<Commands> updatedCommands = new ArrayList<>(this.currentPlayerCommands);
            updatedCommands.remove(slot.getToolCard().getCommandName());
            super.sendCommandsToCurrentPlayer(updatedCommands);
        } else if (!slot.canCardBePaid(playerFavorTokens)) {
            super.sendNotificationToCurrentPlayer("\nNon hai abbastanza Segnalini Favore per utilizzare la Carta Strumento selezionata\n");
            this.setMoveLegal(false);
            List<Commands> updatedCommands = new ArrayList<>(this.currentPlayerCommands);
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
                    "'s Favor Tokens.", e);
            super.getControllerMaster().suspendPlayer(gameState.getCurrentPlayer().getPlayerName());
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
                        SagradaLogger.log(Level.SEVERE,  IMPOSSIBLE_TO_UPDATE + "the Tool Card cost to " + playerName, e);
                        super.getControllerMaster().suspendPlayer(playerName);
                    }
                }
            }
        }
    }

    /**
     * Checks if the player is suspended. If so, ends the current turn.
     * @param playerName player to check.
     */
    private void checkIfPlayerIsSuspended(String playerName) {
        if(super.getControllerMaster().getSuspendedPlayers().contains(playerName)) {
            this.endTurn("\nSei stato sospeso, il tuo turno terminerà.\n");
        }
    }
}

package it.polimi.ingsw.server.controller.managers;

import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.network.IFromServerToClient;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.server.controller.ControllerMaster;
import it.polimi.ingsw.server.model.die.Die;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public abstract class AGameManager {

    /**
     * Timer to use in case the loading from file fails. Value is in milliseconds.
     */
    public static final long BACK_UP_TIMER = 90000;

    /**
     * Path of the file containing the maximum amount of time available for players to make a choice.
     */
    static final String TIMER_FILE = "./src/main/resources/config/turnTimer";

    /**
     * This is the timer that starts at the beginning of each player turn. If it expires, the player is suspended.
     */
    final Timer timer = new Timer();

    /**
     * Task associated with the timer. It's the one that effectively suspends players.
     * @see GamePlayManager
     */
    TimerTask task;

    /**
     * Maximum amount of time each player has to perform a turn
     */
    long timeOut;

    /**
     * Controller of the application.
     */
    private ControllerMaster controllerMaster;

    public ControllerMaster getControllerMaster() {
        return controllerMaster;
    }

    void setControllerMaster(ControllerMaster controllerMaster) {
        this.controllerMaster = controllerMaster;
    }

    /**
     * Starts the timer related to a player. What happens when the timer expires is up to the state of the game in which
     * players are.
     * @param playerName player to whom the timer is related to.
     * @see StartGameManager#startTimer(String)
     * @see GamePlayManager#startTimer(String)
     * @see EndGameManager#startTimer(String)
     */
    abstract void startTimer(String playerName);

    /**
     * This method notifies the current client any type of issue may occur in each request.
     * It's used in the {@link GamePlayManager}.
     * @param message the message to tell.
     */
    public void sendNotificationToCurrentPlayer(String message) {
        String playerName = this.getControllerMaster().getGameState().getCurrentPlayer().getPlayerName();
        IFromServerToClient iFromServerToClient =
                this.getControllerMaster().getConnectedPlayers().get(playerName).getClient();
        try {
            iFromServerToClient.showNotice(message);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to send notification to " + playerName);
            this.getControllerMaster().suspendPlayer(playerName, true);
        }
    }

    /**
     * This method sends to all the player the message parameter.
     * @param message message to send to all clients.
     */
    public void broadcastNotification(String message) {
        for(Player player: this.getControllerMaster().getCommonBoard().getPlayers()) {
            if(!this.controllerMaster.getSuspendedPlayers().contains(player.getPlayerName())) {
                IFromServerToClient client = this.getPlayerClient(player.getPlayerName());
                try {
                    client.showNotice(message);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Impossible to send notification to " + player.getPlayerName());
                    this.getControllerMaster().suspendPlayer(player.getPlayerName(), true);
                }
            }
        }
    }

    /**
     * This method sends to the client the commands available.
     * @param commands {@link Commands} to send.
     */
    void sendCommandsToCurrentPlayer(List<Commands> commands) {
        String playerName = this.getControllerMaster().getGameState().getCurrentPlayer().getPlayerName();
        IFromServerToClient iFromServerToClient =
                this.getControllerMaster().getConnectedPlayers().get(playerName).getClient();
        try {
            iFromServerToClient.showCommand(commands);
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to send commands to " + playerName);
            this.getControllerMaster().suspendPlayer(playerName, true);
        }
    }

    /**
     * This method is used to retrieve the client of the player in input.
     * @param playerName name of the player.
     * @return the client of the player.
     */
    IFromServerToClient getPlayerClient(String playerName) {
        return this.getControllerMaster().getConnectedPlayers().get(playerName).getClient();
    }

    /**
     * Converts the {@link it.polimi.ingsw.server.model.die.containers.DiceDraftPool} of the model to a simpler
     * List of {@link SetUpInformationUnit}, to represent it in the view.
     * @return a list of {@link SetUpInformationUnit} generated from the match
     * {@link it.polimi.ingsw.server.model.die.containers.DiceDraftPool}.
     */
    List<SetUpInformationUnit> draftPoolConverter() {
        List<Die> draftPoolDice = this.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice();
        List<SetUpInformationUnit> draftPoolInfo = new ArrayList<>();
        for(Die die : draftPoolDice)
            draftPoolInfo.add(new SetUpInformationUnit(draftPoolDice.indexOf(die), die.getDieColor(), die.getActualDieValue()));
        return draftPoolInfo;
    }
}

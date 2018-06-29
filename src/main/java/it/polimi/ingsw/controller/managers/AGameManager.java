package it.polimi.ingsw.controller.managers;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.util.ArrayList;
import java.util.List;
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

    private ControllerMaster controllerMaster;

    public ControllerMaster getControllerMaster() {
        return controllerMaster;
    }

    void setControllerMaster(ControllerMaster controllerMaster) {
        this.controllerMaster = controllerMaster;
    }

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
            SagradaLogger.log(Level.SEVERE, "Impossible to send notification to " + playerName, e);
            this.getControllerMaster().suspendPlayer(playerName);
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
                    SagradaLogger.log(Level.SEVERE, "Impossible to send notification to " + player.getPlayerName(), e);
                    this.getControllerMaster().suspendPlayer(player.getPlayerName());
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
            SagradaLogger.log(Level.SEVERE, "Impossible to send commands to " + playerName, e);
            this.getControllerMaster().suspendPlayer(playerName);
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
     * Converts the {@link it.polimi.ingsw.model.die.containers.DiceDraftPool} of the model to a simpler
     * List of {@link SetUpInformationUnit}, to represent it in the view.
     * @return a list of {@link SetUpInformationUnit} generated from the match
     * {@link it.polimi.ingsw.model.die.containers.DiceDraftPool}.
     */
    List<SetUpInformationUnit> draftPoolConverter() {
        List<Die> draftPoolDice = this.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice();
        List<SetUpInformationUnit> draftPoolInfo = new ArrayList<>();
        for(Die die : draftPoolDice)
            draftPoolInfo.add(new SetUpInformationUnit(draftPoolDice.indexOf(die), die.getDieColor(), die.getActualDieValue()));
        return draftPoolInfo;
    }
}

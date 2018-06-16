package it.polimi.ingsw.controller.managers;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.util.ArrayList;
import java.util.List;

public abstract class AGameManager {

    private ControllerMaster controllerMaster;

    public ControllerMaster getControllerMaster() {
        return controllerMaster;
    }

    void setControllerMaster(ControllerMaster controllerMaster) {
        this.controllerMaster = controllerMaster;
    }

    /**
     * This method notifies the client any type of issue may occur in each request.
     * @param message the message to tell.
     */
    public void showNotification(String message) {
        Player player = this.getControllerMaster().getGameState().getCurrentPlayer();
        IFromServerToClient iFromServerToClient = this.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
        try {
            iFromServerToClient.showNotice(message);
        } catch (BrokenConnectionException br) {
            //todo super.getControllerMaster().suspendPlayer(player.getPlayerName());
        }
    }

    /**
     * This method sends to all the player the message parameter.
     * @param message message to send to all clients.
     */
    void broadcastNotification(String message) {
        for(Player player: this.getControllerMaster().getCommonBoard().getPlayers()) {
            IFromServerToClient client =
                    this.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
            try {
                client.showNotice(message);
            } catch (BrokenConnectionException e) {
                //todo super.getControllerMaster().suspendPlayer(player.getPlayerName);
            }
        }
    }

    /**
     * Converts the {@link it.polimi.ingsw.model.die.diecontainers.DiceDraftPool} of the model to a simpler
     * List of {@link SetUpInformationUnit}, to represent it in the view.
     * @return a list of {@link SetUpInformationUnit} generated from the match
     * {@link it.polimi.ingsw.model.die.diecontainers.DiceDraftPool}.
     */
    List<SetUpInformationUnit> draftPoolConverter() {
        List<Die> draftPoolDice = this.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice();
        List<SetUpInformationUnit> draftPoolInfo = new ArrayList<>();
        for(Die die : draftPoolDice)
            draftPoolInfo.add(new SetUpInformationUnit(draftPoolDice.indexOf(die), die.getDieColor(), die.getActualDieValue()));
        return draftPoolInfo;
    }
}

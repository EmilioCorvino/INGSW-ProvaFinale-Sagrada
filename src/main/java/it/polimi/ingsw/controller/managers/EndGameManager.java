package it.polimi.ingsw.controller.managers;

import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 *
 */
public class EndGameManager extends AGameManager {

    public EndGameManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
    }

    void computeRank() {
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();
        players.forEach((player -> player.getScore().computeTotalScore()));

        super.broadcastNotification("\nLA PARTITA È FINITA!\nQuesta è la classifica:\n");
    }
}

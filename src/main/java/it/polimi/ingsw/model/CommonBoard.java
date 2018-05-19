package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * This class represents everything which is visible by all players.
 */
//todo This will probably be the observed class that will notify changes.
public class CommonBoard {

    /**
     * Players connected to the match.
     */
    private List<Player> players;

}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class representing the first state of the game.
 */
public class StartGameState extends AGameState {

    /**
     * This list represents the players that connect to the game at this first state.
     */
    private List<Player> playersRoom;


    public StartGameState() {
        playersRoom = new ArrayList<>();
    }

    /**
     * This method manages the instantiation of the list of player for the game.
     * @param namePlayer the name of the player to add to the list.
     */
    public void login(String namePlayer) {
        Player player = new Player(namePlayer, super.getControllerMaster().getCommonBoard());
        playersRoom.add(player);
        super.getControllerMaster().getFromServerToClient().showPlayerName(player.getPlayerName());
    }

    public List<Player> getPlayersRoom() {
        return playersRoom;
    }

    public void setPlayersRoom(List<Player> playersRoom) {
        this.playersRoom = playersRoom;
    }

    @Override
    public void changeGameState(ControllerMaster controllerMaster) {

    }
}
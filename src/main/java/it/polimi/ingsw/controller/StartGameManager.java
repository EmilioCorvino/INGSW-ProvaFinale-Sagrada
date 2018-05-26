package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.network.IFromServerToClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides methods to support all the operations needed before the match starts.
 */
public class StartGameManager extends AGameManager {

    public StartGameManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
    }

    /**
     * This method checks if the username of a player already exists in the current match.
     * @param namePlayer the name of the player to check.
     */
    public boolean checkLogin(String namePlayer) {
        //Player player = new Player(namePlayer, super.getControllerMaster().getCommonBoard());

        if (super.getControllerMaster().getConnectedPlayers().isEmpty()) {
            return true;
        } else {
            for (Map.Entry<String, IFromServerToClient> entry : getControllerMaster().getConnectedPlayers().entrySet())
                if (namePlayer.equals(entry.getKey()))
                    return false;
        }
        return true;
    }

    /**
     * This method checks if the maximum number of player for a match is reached.
     * @return true if the maximum number is reached.
     */
    public boolean isFull() {
        return super.getControllerMaster().getConnectedPlayers().size() == ServerImplementation.MAX_PLAYERS;
    }

    //todo handle gameMode
    /**
     * This method manages the notifyWaitingPlayers of a player and initializes the match according to his/her choice.
     * @param gameMode the type of match to initialize.
     */
    public void notifyWaitingPlayers(String gameMode) {

        List<String> listName = new ArrayList<>();

        for(Map.Entry<String, IFromServerToClient> entry: getControllerMaster().getConnectedPlayers().entrySet()) {
            listName.add(entry.getKey());
        }

        for(Map.Entry<String, IFromServerToClient> entry: getControllerMaster().getConnectedPlayers().entrySet()) {
            try {
                entry.getValue().showRoom(listName);
            } catch (BrokenConnectionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param namePlayer
     */
    public void disconnectPlayer(String namePlayer) {


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

}
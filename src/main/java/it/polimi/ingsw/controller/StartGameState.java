package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the class representing the first state of the game.
 */
public class StartGameState extends AGameState {

    /**
     * This list represents the players that connect to the game at this first state.
     */
    private List<Player> playersRoom;




    public StartGameState(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        playersRoom = new ArrayList<>();
    }

    /**
     * This method manages the instantiation of the list of player for the game.
     * @param namePlayer the name of the player to add to the list.
     */
    public void login(String namePlayer, String gameMode) {
        Player player = new Player(namePlayer, super.getControllerMaster().getCommonBoard());
        playersRoom.add(player);

         List<String> listName = new ArrayList<>();


        for (Player p :  playersRoom) {
            listName.add(p.getPlayerName());
        }

        for(Map.Entry<String, IFromServerToClient> entry: getControllerMaster().getConnectedPlayers().entrySet()) {
            try {
                entry.getValue().showRoom(listName);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
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
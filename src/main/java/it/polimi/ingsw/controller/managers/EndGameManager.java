package it.polimi.ingsw.controller.managers;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.util.*;
import java.util.logging.Level;

/**
 *
 */
public class EndGameManager extends AGameManager {

    /**
     * List containing the {@link Commands} the player can require during the end game phase.
     */
    private final List<Commands> endGameCommands;

    public EndGameManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        this.endGameCommands = new ArrayList<>(Arrays.asList(Commands.START_ANOTHER_GAME, Commands.LOGOUT));
    }

    /**
     * This method computes the scores and sends to all the players the final rank.
     * It also shows them the last {@link Commands} available.
     */
    void computeRank() {
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        //Computes the score of all players and puts the playerName and the score in a temporary map.
        Map<String, Integer> disorderedRank = new HashMap<>();
        for(Player player: players) {
            player.getScore().computeTotalScore();
            disorderedRank.put(player.getPlayerName(), player.getScore().getTotalScore());
        }

        //Reorders tha map and then sends the rank, opportunely converted, to the view.
        Map<String, Integer> rank = reorganizeRank(disorderedRank);
        super.broadcastNotification("\nLA PARTITA È FINITA!\nQuesta è la classifica:\n");
        for(String playerName: super.getControllerMaster().getConnectedPlayers().keySet()) {
            IFromServerToClient client = super.getControllerMaster().getConnectedPlayers().get(playerName).getClient();
            try {
                client.showRank(this.extractNamesToSend(rank), this.extractScoresToSend(rank));
                client.showCommand(endGameCommands);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, "Connection lost with " + playerName + " while showing the rank");
            }
        }
    }

    /**
     * This method puts in order of value the map in input.
     * @param rankToOrganize map containing the rank to put in order.
     */
    private Map<String, Integer> reorganizeRank(Map<String, Integer> rankToOrganize) {
        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(rankToOrganize.entrySet());
        listOfEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Map<String, Integer> organizedMap = new LinkedHashMap<>();
        for(Map.Entry<String, Integer> entry: listOfEntries) {
            organizedMap.put(entry.getKey(), entry.getValue());
        }
        return organizedMap;
    }

    /**
     * Populates an array (ordered by score in descending order) containing the names of the players in the match.
     * @param rankToSend rank to display to players.
     * @return array containing the name of the players in the game.
     */
    private String[] extractNamesToSend(Map<String, Integer> rankToSend) {
        String[] playerNames = new String[rankToSend.entrySet().size()];
        List<String> tempNames = new ArrayList<>(rankToSend.keySet());
        for(int i = 0; i < rankToSend.entrySet().size(); i++) {
            playerNames[i] = tempNames.get(i);
        }
        return playerNames;
    }

    /**
     * Populates an array (ordered by score in descending order) containing the scores of the players in the match.
     * @param rankToSend rank to display to players.
     * @return array containing the score of the players in the game.
     */
    private int[] extractScoresToSend(Map<String, Integer> rankToSend) {
        int[] playerScores = new int[rankToSend.entrySet().size()];
        List<Integer> tempScores = new ArrayList<>(rankToSend.values());
        for(int i = 0; i < rankToSend.entrySet().size(); i++) {
            playerScores[i] = tempScores.get(i);
        }
        return playerScores;
    }

    public void exitGame() {
        
    }
}

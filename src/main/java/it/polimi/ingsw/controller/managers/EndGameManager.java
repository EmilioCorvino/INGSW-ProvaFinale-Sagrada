package it.polimi.ingsw.controller.managers;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.ControllerMaster;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.turn.Turn;
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
     * This method computes the scores and sends to all the players the winner and the final rank, eventually
     * performing a tie-break.
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

        //Reorders tha map, counts how many player have the same winning score and then, if they are more than one,
        //proceed to the tie-break.
        Map<String, Integer> rank = reorganizeRank(disorderedRank); //This rank is the complete one shown to the players.
        Map<String, Integer> rankToFilter = new LinkedHashMap<>(rank); //This rank is used for the tie-break.
        List<String> playersWithMaxScore = this.identifyPlayersWithMaxScore(rank, rankToFilter);
        super.broadcastNotification("\nLA PARTITA È FINITA!");
        if(playersWithMaxScore.size() == 1) {
            this.declareWinner(playersWithMaxScore.get(0));
        } else {    //Tie-break Private Objective.
            List<String> playersWithMaxPrivateObjectivePoints = this.performPrivateObjectiveTieBreak(rankToFilter);
            if(playersWithMaxPrivateObjectivePoints.size() == 1) {
                this.declareWinner(playersWithMaxPrivateObjectivePoints.get(0));
                super.broadcastNotification("\nÈ stato effettuato uno spareggio basato sul maggior numero di punti" +
                        " acquisiti dall'Obiettivo Privato.");
            } else {    //Tie-break Favor Tokens.
                List<String> playersWithMaxFavorTokensPoints = this.performFavorTokensTieBreak(rankToFilter);
                if(playersWithMaxFavorTokensPoints.size() == 1) {
                    this.declareWinner(playersWithMaxFavorTokensPoints.get(0));
                    super.broadcastNotification("\nÈ stato effettuato uno spareggio basato sul maggior numero di punti" +
                            " acquisiti dall'obiettivo privato, e in seguito dai Segnalini Favore.");
                } else {    //Tie-break Turn Order.
                    this.declareWinner(this.performTurnOrderTieBreak(rankToFilter));
                    super.broadcastNotification("\nÈ stato effettuato uno spareggio basato sul maggior numero di punti " +
                            "acquisiti dall'Obiettivo Privato,\nquindi dai Segnalini Favore ed infine sull'ordine di turno" +
                            " (ha vinto l'ultimo giocatore in ordine di turno partecipante allo spareggio).");
                }
            }
        }

        super.broadcastNotification("\nQuesta è la classifica completa:\n");
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
     * This methods communicates the winner to all players in the match.
     * @param winnerName name of the winner.
     *
     */
    private void declareWinner(String winnerName) {

        //Tells the winning player that he won.
        IFromServerToClient winnerClient =
                super.getControllerMaster().getConnectedPlayers().get(winnerName).getClient();
        try {
            winnerClient.showNotice("\nCongratulazioni, hai vinto!");
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, "Connection lost with " + winnerName + " while showing the winner");
        }

        //Tells the other players who is the winner.
        for(Player player: super.getControllerMaster().getCommonBoard().getPlayers()) {
            if(!player.getPlayerName().equals(winnerName)) {
                IFromServerToClient loserClient =
                        super.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
                try {
                    loserClient.showNotice("\nIl vincitore è: " + winnerName);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, "Connection lost with " + player.getPlayerName() +
                            " while showing the winner");
                }
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

    /**
     * This method computes the maximum score inside a set.
     * @param scores scores achieved in the match.
     * @return maximum score achieved.
     */
    private int maxScore(Set<Integer> scores) {
        int max = 0;
        for(Integer score: scores) {
            if(score > max) {
                max = score;
            }
        }
        return max;
    }

    /**
     * This method gathers in a {@link List} all the player having the same winning score, allowing for a tie-breaker.
     * @param completeRank rank of the players in the match.
     * @param filteredRank rank that is filtered for the tie-break.
     * @return list with player names who have the same score.
     */
    private List<String> identifyPlayersWithMaxScore(Map<String, Integer> completeRank, Map<String, Integer> filteredRank) {
        List<String> playersWithMaxScore = new ArrayList<>();
        int maxScore = this.maxScore((Set<Integer>) completeRank.values());
        for(Map.Entry<String, Integer> entry: completeRank.entrySet()) {
            if(entry.getValue().equals(maxScore)) {
                playersWithMaxScore.add(entry.getKey());
            } else {
                filteredRank.remove(entry.getKey());
            }
        }
        return playersWithMaxScore;
    }

    /**
     * This methods performs a tie-break between players with same points, based on
     * {@link it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard} points.
     * It first creates a {@link Set} with all private objective card scores, used to compute the maximum, and transforms
     * the map in input from a playerName-totalScore to a playerName-privateObjectiveScore scheme.
     * Then fills a list with all the names of players having a private objective score equal to the maximum and finally
     * removes from the map in input all the players not reaching that value.
     * @param filteredRank map of players in the tie-break. The values representing the total scores are replaced with
     *                     private objective scores.
     * @return list of the names of players that won the tie-break.
     */
    private List<String> performPrivateObjectiveTieBreak(Map<String, Integer> filteredRank) {

        //Gather all the points gained from Private Objective Cards and puts them in a set.
        Set<Integer> privateObjectiveScores = new HashSet<>();
        for(String playerName: super.getControllerMaster().getConnectedPlayers().keySet()) {
            if(filteredRank.containsKey(playerName)) {
                int privateObjectiveScore = super.getControllerMaster().getCommonBoard().getSpecificPlayer(playerName)
                        .getScore().getPrivateObjectivePoints();
                privateObjectiveScores.add(privateObjectiveScore);
                filteredRank.replace(playerName, privateObjectiveScore);
            }
        }

        //Computes the list of players having the max private objective score.
        List<String> playersWithMaxPrivateObjectiveScore = new ArrayList<>();
        Map<String, Integer> playersToRemove = new LinkedHashMap<>();
        int maxPrivateObjectiveScore = maxScore(privateObjectiveScores);
        for(Map.Entry<String, Integer> entry: filteredRank.entrySet()) {
            if(entry.getValue().equals(maxPrivateObjectiveScore)) {
                playersWithMaxPrivateObjectiveScore.add(entry.getKey());
            } else {
                playersToRemove.put(entry.getKey(), entry.getValue());
            }
        }

        //Updates the filtered rank, removing the players with less private objective points.
        for(String playerName: playersToRemove.keySet()) {
            filteredRank.remove(playerName);
        }

        return playersWithMaxPrivateObjectiveScore;
    }

    /**
     * This methods performs a tie-break between players with same points, based on Favor Token points.
     * It first creates a {@link Set} with all favor tokens scores, used to compute the maximum, and transforms
     * the map in input from a playerName-privateObjectiveScore to a playerName-favorTokensScore scheme.
     * Then fills a list with all the names of players having a favor tokens score equal to the maximum and finally
     * removes from the map in input all the players not reaching that value.
     * @param filteredRank map of players in the tie-break. The values representing the private objective scores are
     *                     replaced with favor tokens score.
     * @return list of the names of players that won the tie-break.
     */
    private List<String> performFavorTokensTieBreak(Map<String, Integer> filteredRank) {

        //Gather all the points gained from Favor Tokens and puts them in a set.
        Set<Integer> favorTokensScores = new HashSet<>();
        for(String playerName: super.getControllerMaster().getConnectedPlayers().keySet()) {
            if(filteredRank.containsKey(playerName)) {
                int favorTokensScore = super.getControllerMaster().getCommonBoard().getSpecificPlayer(playerName)
                        .getScore().getFavorTokensPoints();
                favorTokensScores.add(favorTokensScore);
                filteredRank.replace(playerName, favorTokensScore);
            }
        }

        //Computes the list of players having the max favor tokens score.
        List<String> playersWithMaxFavorTokensScore = new ArrayList<>();
        Map<String, Integer> playersToRemove = new LinkedHashMap<>();
        int maxFavorTokensScore = maxScore(favorTokensScores);
        for(Map.Entry<String, Integer> entry: filteredRank.entrySet()) {
            if(entry.getValue().equals(maxFavorTokensScore)) {
                playersWithMaxFavorTokensScore.add(entry.getKey());
            } else {
                playersToRemove.put(entry.getKey(), entry.getValue());
            }
        }

        //Updates the filtered rank, removing the players with less favor tokens points.
        for(String playerName: playersToRemove.keySet()) {
            filteredRank.remove(playerName);
        }

        return playersWithMaxFavorTokensScore;
    }

    /**
     * This methods performs a tie-break between players with same points, based on Turn Order.
     * It first creates a {@link Set} with all turn order indexes, used to compute the maximum, and transforms
     * the map in input from a playerName-favorTokensScore to a playerName-turnIndex scheme.
     * Then fills a string with the name of the last player in the turn order and finally removes from the map in input
     * all the players different from that one. At this point, the map should only have one entry.
     * @param filteredRank map of players in the tie-break. The values representing the favor tokens scores are
     *                     replaced with turn order indexes.
     * @return the name of the player that won the tie-break.
     */
    private String performTurnOrderTieBreak(Map<String, Integer> filteredRank) {
        List<Turn> turnOrder = super.getControllerMaster().getGameState().getTurnOrder();
        List<Turn> turnOrderSubList = turnOrder.subList(0, (turnOrder.size()/2) - 1);
        List<String> orderedPlayerNames = new ArrayList<>();
        for(int i = 0; i < turnOrderSubList.size(); i++) {
            orderedPlayerNames.add(turnOrderSubList.get(0).getPlayer().getPlayerName());
        }

        //Gather all the turn indexes and puts them in a set.
        Set<Integer> turnIndexes = new HashSet<>();
        for(String playerName: orderedPlayerNames) {
            if(filteredRank.containsKey(playerName)) {
                int turnIndex = orderedPlayerNames.indexOf(playerName);
                turnIndexes.add(turnIndex);
                filteredRank.replace(playerName, turnIndex);
            }
        }

        //Computes the last player in the turn order.
        String lastPlayer = "";
        Map<String, Integer> playersToRemove = new LinkedHashMap<>();
        int lastInTurnOrder = maxScore(turnIndexes);
        for(Map.Entry<String, Integer> entry: filteredRank.entrySet()) {
            if(entry.getValue().equals(lastInTurnOrder)) {
                lastPlayer = entry.getKey();
            } else {
                playersToRemove.put(entry.getKey(), entry.getValue());
            }
        }

        //Updates the filtered rank, removing the players that didn't play the last turn. Only one entry should be in
        //the map at this point.
        for(String playerName: playersToRemove.keySet()) {
            filteredRank.remove(playerName);
        }

        return lastPlayer;
    }

    public void exitGame() {

    }
}

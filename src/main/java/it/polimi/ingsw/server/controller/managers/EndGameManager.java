package it.polimi.ingsw.server.controller.managers;

import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.network.Connection;
import it.polimi.ingsw.common.network.IFromServerToClient;
import it.polimi.ingsw.common.utils.PropertyLoader;
import it.polimi.ingsw.common.utils.SagradaLogger;
import it.polimi.ingsw.common.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.server.controller.ControllerMaster;
import it.polimi.ingsw.server.controller.WaitingRoom;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.turn.Turn;

import java.util.*;
import java.util.logging.Level;

/**
 *
 */
public class EndGameManager extends AGameManager {

    /**
     * List containing the {@link Commands} the player can require during the end game phase.
     */
    final List<Commands> endGameCommands;

    /**
     * Name of players that made a choice between starting a new game or logging out.
     */
    private List<String> playersThatAnswered;

    private static final String CONNECTION_LOST_WITH = "Connection lost with ";

    /**
     * A part from initializing the class attributes, this constructor also loads the timer from file.
     * @param controllerMaster main controller class.
     */
    public EndGameManager(ControllerMaster controllerMaster) {
        super.setControllerMaster(controllerMaster);
        this.endGameCommands = new ArrayList<>(Arrays.asList(Commands.START_ANOTHER_GAME, Commands.LOGOUT));
        this.playersThatAnswered = new ArrayList<>();

        //Back up value.
        super.timeOut = BACK_UP_TIMER;

        //Value read from file. If the loading is successful, it overwrites the back up.
        if (PropertyLoader.getPropertyLoader().getTurnTimer() != 0) {
            super.timeOut = PropertyLoader.getPropertyLoader().getTurnTimer();
            SagradaLogger.log(Level.CONFIG, "Timer successfully loaded from file. Its value is: " + timeOut / 1000 + "s");
        }
    }

//----------------------------------------------------------
//                    END GAME FLOW METHODS
//----------------------------------------------------------

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
            this.declareWinner(playersWithMaxScore.get(0), rank);
        }

        //Tie-break Private Objective.
        else {
            List<String> playersWithMaxPrivateObjectivePoints = this.performPrivateObjectiveTieBreak(rankToFilter);
            if(playersWithMaxPrivateObjectivePoints.size() == 1) {
                this.declareWinner(playersWithMaxPrivateObjectivePoints.get(0), rank);
                super.broadcastNotification("\nÈ stato effettuato uno spareggio basato sul maggior numero di punti" +
                        " acquisiti dall'Obiettivo Privato.");
            }

            //Tie-break Favor Tokens.
            else {
                List<String> playersWithMaxFavorTokensPoints = this.performFavorTokensTieBreak(rankToFilter);
                if(playersWithMaxFavorTokensPoints.size() == 1) {
                    this.declareWinner(playersWithMaxFavorTokensPoints.get(0), rank);
                    super.broadcastNotification("\nÈ stato effettuato uno spareggio basato sul maggior numero di punti" +
                            " acquisiti dall'Obiettivo Privato, e in seguito sui Segnalini Favore.");
                }

                //Tie-break Turn Order.
                else {
                    this.declareWinner(this.performTurnOrderTieBreak(rankToFilter), rank);
                    super.broadcastNotification("\nÈ stato effettuato uno spareggio basato sul maggior numero di punti " +
                            "acquisiti dall'Obiettivo Privato,\nquindi dai Segnalini Favore ed infine sull'ordine di turno" +
                            " (ha vinto l'ultimo giocatore in ordine di turno partecipante allo spareggio).");
                }
            }
        }

        super.broadcastNotification("\nQuesta è la classifica completa:");
        for(String playerName: super.getControllerMaster().getConnectedPlayers().keySet()) {
            IFromServerToClient client = super.getControllerMaster().getConnectedPlayers().get(playerName).getClient();
            this.startTimer(playerName);
            try {
                client.showRank(this.extractNamesToSend(rank), this.extractScoresToSend(rank));
                client.showNotice("\nGrazie per aver giocato!");
                client.showCommand(this.endGameCommands);
            } catch (BrokenConnectionException e) {
                SagradaLogger.log(Level.SEVERE, CONNECTION_LOST_WITH + playerName + " while showing the rank");
                this.exitGame(playerName);
            }
        }
    }

    /**
     * This methods is triggered by players who want to log out. It increments the number of players that have made a
     * choice and then, if each of them did, quits the game or initialized a new
     * {@link it.polimi.ingsw.server.controller.WaitingRoom} whether there is at least one player willing to play again.
     * @param playerName name of the player sending the request.
     */
    public void exitGame(String playerName) {
        if (!playersThatAnswered.contains(playerName) && !super.getControllerMaster().getSuspendedPlayers().contains(playerName)) {
            this.playersThatAnswered.add(playerName);
        }
        Map<String, Connection> connectedPlayers = super.getControllerMaster().getConnectedPlayers();
        IFromServerToClient client = connectedPlayers.get(playerName).getClient();
        try {
            client.forceLogOut();
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.WARNING, CONNECTION_LOST_WITH + playerName + " while forcing him to log" +
                    " out");
        }
        connectedPlayers.remove(playerName);

        SagradaLogger.log(Level.WARNING, playerName + " logged out.");

        if (playersThatAnswered.size() == super.getControllerMaster().getCommonBoard().getPlayers().size() -
                super.getControllerMaster().getSuspendedPlayers().size()) {

            //Remove the suspended players from the connected ones
            this.removeSuspendedPlayers();

            if (connectedPlayers.isEmpty()) {
                this.quitGame();
            } else {
                this.initializeNewWaitingRoom(connectedPlayers);
            }
        }
    }

    /**
     * This methods is triggered by players who want to play again. It increments the number of players that have made a
     * choice and then, if each of them did, quits the game or initialized a new
     * {@link it.polimi.ingsw.server.controller.WaitingRoom} whether there is at least one player willing to play again.
     * This method is robust towards multiple disconnections: it will call {@link #quitGame()} if all players'
     * connections drop.
     * @param playerName player sending the request.
     */
    public void newGame(String playerName) {
        if (!playersThatAnswered.contains(playerName)) {
            this.playersThatAnswered.add(playerName);
        }
        IFromServerToClient client =
                super.getControllerMaster().getConnectedPlayers().get(playerName).getClient();
        try {
            client.showNotice("Attendi la scelta degli altri giocatori...");
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.WARNING, CONNECTION_LOST_WITH + playerName + "while inserting him in a " +
                    "new room. He will be removed.");
            super.getControllerMaster().getConnectedPlayers().remove(playerName);
        }

        SagradaLogger.log(Level.WARNING, playerName + " wishes to play again.");

        if(playersThatAnswered.size() == super.getControllerMaster().getCommonBoard().getPlayers().size() -
                super.getControllerMaster().getSuspendedPlayers().size()) {
            Map<String, Connection> connectedPlayers = super.getControllerMaster().getConnectedPlayers();

            //Remove the suspended players from the connected ones
            this.removeSuspendedPlayers();

            for(Map.Entry<String, Connection> remainingPlayer: connectedPlayers.entrySet()) {
                try {
                    remainingPlayer.getValue().getClient().showNotice("\nVerrai inserito in una nuova stanza d'attesa.");
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.WARNING, CONNECTION_LOST_WITH + remainingPlayer.getKey() +
                        " while inserting him in a new room. He will be removed.");
                    connectedPlayers.remove(remainingPlayer.getKey());
                }
            }

            //Checks this in case all players disconnected.
            if(!connectedPlayers.isEmpty()) {
                this.initializeNewWaitingRoom(connectedPlayers);
            } else {
                this.quitGame();
            }
        }
    }

    /**
     * Asks the server owner whether he wants to close the server or not. If he doesn't, it prepares the
     * {@link it.polimi.ingsw.server.controller.WaitingRoom} for a new game.
     */
    void quitGame() {
        synchronized(super.getControllerMaster().getWaitingRoom()) {
            WaitingRoom waitingRoom = super.getControllerMaster().getWaitingRoom();
            waitingRoom.getPlayersRoom().clear();
            waitingRoom.setMatchAlreadyStarted(false);
        }
        System.out.println("Server ready for a new match.");
    }

    /**
     * Replaces the players that were in the map {@link WaitingRoom#playersRoom} with the one in input, then notifies
     * them.
     * @param playersWillingToGoAgain player that want to start another game.
     */
    private void initializeNewWaitingRoom(Map<String, Connection> playersWillingToGoAgain) {
        synchronized(super.getControllerMaster().getWaitingRoom()) {
            WaitingRoom waitingRoom = super.getControllerMaster().getWaitingRoom();
            waitingRoom.getPlayersRoom().clear();
            waitingRoom.setMatchAlreadyStarted(false);
            waitingRoom.getPlayersRoom().putAll(playersWillingToGoAgain);
            waitingRoom.notifyWaitingPlayers();
        }
    }

//----------------------------------------------------------
//                    UTILITY METHODS
//----------------------------------------------------------

    /**
     * This methods communicates the winner to all players in the match.
     * @param winnerName name of the winner.
     * @param reorderedRank rank that will be sent to the players.
     */
    private void declareWinner(String winnerName, Map<String, Integer> reorderedRank) {

        //These lines are used to move the winning player to the top of the rank.
        Map<String, Integer> supportMap = new LinkedHashMap<>();
        supportMap.put(winnerName, reorderedRank.remove(winnerName));
        supportMap.putAll(reorderedRank);
        reorderedRank.clear();
        reorderedRank.putAll(supportMap);

        //Tells the winning player that he won.
        IFromServerToClient winnerClient = super.getPlayerClient(winnerName);
        try {
            winnerClient.showNotice("\nCongratulazioni, hai vinto!");
        } catch (BrokenConnectionException e) {
            SagradaLogger.log(Level.SEVERE, CONNECTION_LOST_WITH + winnerName + " while showing the winner");
            this.exitGame(winnerName);
        }

        //Tells the other players who is the winner.
        for(Player player: super.getControllerMaster().getCommonBoard().getPlayers()) {
            if(!player.getPlayerName().equals(winnerName)) {
                IFromServerToClient loserClient = super.getPlayerClient(player.getPlayerName());
                try {
                    loserClient.showNotice("\nIl vincitore è: " + winnerName);
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.SEVERE, CONNECTION_LOST_WITH + player.getPlayerName() +
                            " while showing the winner");
                    this.exitGame(player.getPlayerName());
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
     * This method gathers in a {@link List} all the player having the same winning score, allowing for a tie-breaker.
     * @param completeRank rank of the players in the match.
     * @param filteredRank rank that is filtered for the tie-break.
     * @return list with player names who have the same score.
     */
    private List<String> identifyPlayersWithMaxScore(Map<String, Integer> completeRank, Map<String, Integer> filteredRank) {
        List<String> playersWithMaxScore = new ArrayList<>();
        Set<Integer> scores = new HashSet<>();
        completeRank.forEach((playerName, score) -> scores.add(score));
        int maxScore = this.maxScore(scores);
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
     * Starts the timer of the turn. If it can't be loaded from file, a back up value is used.
     * @param playerName turn of the player to suspend in case his turn isn't over when the timer expires. It has a reference
     *             to said {@link Player}.
     */
    @Override
    void startTimer(String playerName) {
        SagradaLogger.log(Level.INFO, playerName + " end choice timer is started");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SagradaLogger.log(Level.WARNING, playerName + " end choice turn timer is expired");
                if(!playersThatAnswered.contains(playerName) && getControllerMaster().getConnectedPlayers().containsKey(playerName)) {
                        IFromServerToClient client = getPlayerClient(playerName);
                        try {
                            client.showNotice("Hai impiegato troppo tempo a rispondere, verrai disconnesso");
                            exitGame(playerName);
                        } catch (BrokenConnectionException e) {
                            SagradaLogger.log(Level.SEVERE, CONNECTION_LOST_WITH + playerName + " while forcing" +
                                    " him to log out.");
                            exitGame(playerName);
                        }
                }
            }
        }, super.timeOut);
    }

//----------------------------------------------------------
//                    VIEW CONVERTERS
//----------------------------------------------------------

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

//----------------------------------------------------------
//                    TIE-BREAKERS
//----------------------------------------------------------

    /**
     * This methods performs a tie-break between players with same points, based on
     * {@link it.polimi.ingsw.server.model.cards.objective.privates.PrivateObjectiveCard} points.
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
        List<Turn> turnOrderSubList = turnOrder.subList(0, (turnOrder.size()/2));
        List<String> orderedPlayerNames = new ArrayList<>();
        for (Turn turn: turnOrderSubList) {
            orderedPlayerNames.add(turn.getPlayer().getPlayerName());
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

    /**
     * Removes the suspended players from the connected ones.
     */
    private void removeSuspendedPlayers() {
        super.getControllerMaster().getSuspendedPlayers().forEach(suspendedPlayer -> {
            if (getControllerMaster().getConnectedPlayers().containsKey(suspendedPlayer)) {
                try {
                    getControllerMaster().getConnectedPlayers().get(suspendedPlayer).getClient().forceLogOut();
                } catch (BrokenConnectionException e) {
                    SagradaLogger.log(Level.WARNING, "Player disconnected while forcing him to log out");
                } finally {
                    getControllerMaster().getConnectedPlayers().remove(suspendedPlayer);
                }
            }
        });
    }
}

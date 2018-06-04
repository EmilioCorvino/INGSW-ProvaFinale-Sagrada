package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCardDeck;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.EmptyException;

import java.util.ArrayList;
import java.util.HashMap;
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
     * This method shows to the client four window pattern cards among which to choose.
     */
    public List<SimplifiedWindowPatternCard> chooseWindowPatternCard() {
        List<SimplifiedWindowPatternCard> listToSend = new ArrayList<>();
        super.getControllerMaster().getConnectedPlayers().entrySet().forEach(entry -> {
            List<SimplifiedWindowPatternCard> list1 = windowPatternCardConverter();
            listToSend.addAll(list1);
        });
        return listToSend;
    }

    /**
     * This method converts the window pattern card into objects to send to the client.
     * @return a list of a couple of matched window pattern card.
     */
    public List<SimplifiedWindowPatternCard> windowPatternCardConverter() {
        WindowPatternCardDeck mapDeck = super.getControllerMaster().getCommonBoard().getWindowPatternCardDeck();
        List<SimplifiedWindowPatternCard> wpToSend = new ArrayList<>();

        try {
            //System.out.println(super.getControllerMaster().getCommonBoard().getWindowPatternCardDeck().getAvailableWP().size());
            List<WindowPatternCard> coupleOfWP = mapDeck.drawCard();
            //System.out.println(super.getControllerMaster().getCommonBoard().getWindowPatternCardDeck().getAvailableWP().size());
            for(WindowPatternCard wp : coupleOfWP) {
                Cell[][] gw = wp.getGlassWindow();
                List<SetUpInformationUnit> informationUnitList = new ArrayList<>();
                for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
                    for(int j=0; j<WindowPatternCard.getMaxCol(); j++) {
                        SetUpInformationUnit setUpInfo = new SetUpInformationUnit(i*WindowPatternCard.getMaxCol()+j,
                                gw[i][j].getDefaultColorRestriction().getColor(),
                                gw[i][j].getDefaultValueRestriction().getValue());
                        informationUnitList.add(setUpInfo);
                    }
                SimplifiedWindowPatternCard simpleWp = new SimplifiedWindowPatternCard(informationUnitList);
                simpleWp.setIdMap(wp.getIdMap());
                simpleWp.setDifficulty(wp.getDifficulty());
                wpToSend.add(simpleWp);
            }
        } catch (EmptyException empty) {
            empty.printStackTrace();
        }
        return wpToSend;
    }

    /**
     *
     * @param chosenMap
     * @return
     */
    public SimplifiedWindowPatternCard convertOneWp(int chosenMap) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        WindowPatternCard wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenMap);

        Cell[][] gw = wp.getGlassWindow();

        List<SetUpInformationUnit> informationUnitList = new ArrayList<>();

        for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
            for(int j=0; j<WindowPatternCard.getMaxCol(); j++)
                   informationUnitList.add(new SetUpInformationUnit(i*WindowPatternCard.getMaxCol()+j, gw[i][j].getDefaultColorRestriction().getColor(), gw[i][j].getDefaultValueRestriction().getValue()));

        SimplifiedWindowPatternCard simpleWp = new SimplifiedWindowPatternCard(informationUnitList);
        simpleWp.setDifficulty(wp.getDifficulty());
        simpleWp.setIdMap(chosenMap);
        return simpleWp;
    }

    /**
     *
     * @param username
     * @param chosenWp
     */
    public synchronized void wpToSet(String username, int chosenWp) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        WindowPatternCard wpToSet = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenWp);
        commonBoard.getSpecificPlayer(username).setWindowPatternCard(wpToSet);
    }

    /**
     *
     * @return
     */
    private List<SetUpInformationUnit> draftPoolConverter() {
        List<Die> draftPoolDice = super.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice();
        List<SetUpInformationUnit> draftPoolInfo = new ArrayList<>();
        for(Die die : draftPoolDice)
            draftPoolInfo.add(new SetUpInformationUnit(draftPoolDice.indexOf(die), die.getDieColor(), die.getActualDieValue()));
        return draftPoolInfo;
    }

    /**
     * This m
     */
    public void setUpWindowPattern() {
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        players.forEach(player -> {
            IFromServerToClient iFromServerToClient = super.getControllerMaster().getConnectedPlayers().get(player.getPlayerName()).getClient();
            try{
                iFromServerToClient.showMapsToChoose(chooseWindowPatternCard());
            } catch (BrokenConnectionException br) {
                //handle broken connection.
            }
        });

        players.forEach(entry -> {
            IFromServerToClient iFromServerToClient = super.getControllerMaster().getConnectedPlayers().get(entry.getPlayerName()).getClient();
            try {
                iFromServerToClient.choseWpId();
            } catch (BrokenConnectionException br) {
                //broken connection
            }
        });
        //method that shows the common board
        setCommonBoard();

        super.getControllerMaster().initializeGame();
    }

    /**
     *
     */
    public void setCommonBoard() {
        List<SetUpInformationUnit> draftPool = draftPoolConverter();
        Map<String, SimplifiedWindowPatternCard> mapOfWp = mapsOfPlayersConverter();

        super.getControllerMaster().getConnectedPlayers().entrySet().forEach(entry -> {
                try {
                    entry.getValue().getClient().showCommonBoard(draftPool, mapOfWp.get(entry.getKey()));
                } catch (BrokenConnectionException e) {
                    e.printStackTrace();
                }
            });
    }

    /**
     *
     * @return
     */
    private Map<String, SimplifiedWindowPatternCard> mapsOfPlayersConverter() {
        Map<String, SimplifiedWindowPatternCard> mapOfWp = new HashMap<>();
        List<Player> players = super.getControllerMaster().getCommonBoard().getPlayers();

        for(Player player : players) {
           // System.out.println(player.getWindowPatternCard().getIdMap());
            mapOfWp.put(player.getPlayerName(), convertOneWp(player.getWindowPatternCard().getIdMap()));
        }
        return mapOfWp;
    }
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
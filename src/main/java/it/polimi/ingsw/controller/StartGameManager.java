package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCardDeck;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.network.PlayerColor;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.EmptyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public void chooseWindowPatternCard() {

        super.getControllerMaster().getConnectedPlayers().entrySet().forEach(entry -> {

            List<SimplifiedWindowPatternCard> listToSend = new ArrayList<>();

            List<SimplifiedWindowPatternCard> list1 = windowPatternCardConverter();
            listToSend.add(list1.get(0));
            listToSend.add(list1.get(1));

            List<SimplifiedWindowPatternCard> list2 = windowPatternCardConverter();
            listToSend.add(list2.get(0));
            listToSend.add(list2.get(1));
            try{
                entry.getValue().showMapsToChoose(listToSend);
            } catch (BrokenConnectionException br) {
                //TODO handle broken connection: suspend player...
            }

        });
    }

    /**
     * This method converts the window pattern card into objects to send to the client.
     * @return a list of a couple of matched window pattern card.
     */
    public List<SimplifiedWindowPatternCard> windowPatternCardConverter() {

        WindowPatternCardDeck windowPatternCardDeck = new WindowPatternCardDeck();
        windowPatternCardDeck.parseDeck();
        List<SetUpInformationUnit> informationUnitList = new ArrayList<>();
        List<SimplifiedWindowPatternCard> wpToSend = new ArrayList<>();

        try {
            List<WindowPatternCard> coupleOfWP = windowPatternCardDeck.drawCard();

            for(WindowPatternCard wp : coupleOfWP) {
                Cell[][] gw = wp.getGlassWindow();

                for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
                    for(int j=0; j<WindowPatternCard.getMaxCol(); i++)

                        if(!gw[i][j].getRuleSetCell().isEmpty())

                            informationUnitList.add(new SetUpInformationUnit(i*WindowPatternCard.getMaxCol()+j, gw[i][j].getDefaultColorRestriction().getColor(), gw[i][j].getDefaultValueRestriction().getValue()));

                wpToSend.add(new SimplifiedWindowPatternCard(informationUnitList));
            }
        } catch (EmptyException empty) {
            empty.printStackTrace();
        }
        return wpToSend;
    }

    public SimplifiedWindowPatternCard convertOneWp(int chosenMap) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        WindowPatternCard wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenMap);

        Cell[][] gw = wp.getGlassWindow();

        List<SetUpInformationUnit> informationUnitList = new ArrayList<>();

        for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
            for(int j=0; j<WindowPatternCard.getMaxCol(); i++)
                if(!gw[i][j].getRuleSetCell().isEmpty())
                   informationUnitList.add(new SetUpInformationUnit(i*WindowPatternCard.getMaxCol()+j, gw[i][j].getDefaultColorRestriction().getColor(), gw[i][j].getDefaultValueRestriction().getValue()));

        return new SimplifiedWindowPatternCard(informationUnitList);

    }

    /**
     * This method associates the chosen window pattern card to the specific player.
     * @param player
     * @param chosenWp
     */
    public void wpToSet(PlayerColor player, int chosenWp) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        WindowPatternCard wpToSet = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenWp);
        commonBoard.getPlayerMap().get(player).setWindowPatternCard(wpToSet);
        SimplifiedWindowPatternCard wpToSend = convertOneWp(chosenWp);

        try{
            super.getControllerMaster().getConnectedPlayers().get(player).showCommonBoard(commonBoard.getDraftPool(), wpToSend);

        } catch (BrokenConnectionException br) {
            //TODO handle broken connection.
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

}
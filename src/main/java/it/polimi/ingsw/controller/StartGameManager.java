package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCardDeck;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.exceptions.EmptyException;

import java.util.ArrayList;
import java.util.List;

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

        List<SimplifiedWindowPatternCard> listToSend = new ArrayList<>();
        System.out.println("created list to send");

        super.getControllerMaster().getConnectedPlayers().entrySet().forEach(entry -> {

            List<SimplifiedWindowPatternCard> list1 = windowPatternCardConverter();
            listToSend.add(list1.get(0));
            listToSend.add(list1.get(1));

            List<SimplifiedWindowPatternCard> list2 = windowPatternCardConverter();
            listToSend.add(list2.get(0));
            listToSend.add(list2.get(1));

        System.out.println("choooooooseeeeeeeeeeeee");

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

        WindowPatternCardDeck mapDeck = super.getControllerMaster().getCommonBoard().getWindowPatternCardDeck();
        //WindowPatternCardDeck windowPatternCardDeck = new WindowPatternCardDeck();
        //windowPatternCardDeck.parseDeck();
        List<SimplifiedWindowPatternCard> wpToSend = new ArrayList<>();

        try {
            List<WindowPatternCard> coupleOfWP = mapDeck.drawCard();
            for(WindowPatternCard wp : coupleOfWP) {
                Cell[][] gw = wp.getGlassWindow();
                List<SetUpInformationUnit> informationUnitList = new ArrayList<>();
                for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
                    for(int j=0; j<WindowPatternCard.getMaxCol(); j++) {

                        //System.out.println(i + " " + j);
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

    public SimplifiedWindowPatternCard convertOneWp(int chosenMap) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        WindowPatternCard wp = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenMap);

        Cell[][] gw = wp.getGlassWindow();

        List<SetUpInformationUnit> informationUnitList = new ArrayList<>();

        for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
            for(int j=0; j<WindowPatternCard.getMaxCol(); j++)
                   informationUnitList.add(new SetUpInformationUnit(i*WindowPatternCard.getMaxCol()+j, gw[i][j].getDefaultColorRestriction().getColor(), gw[i][j].getDefaultValueRestriction().getValue()));

        return new SimplifiedWindowPatternCard(informationUnitList);

    }

    /**
     *
     * @param username
     * @param chosenWp
     */
    public void wpToSet(String username, int chosenWp) {
        CommonBoard commonBoard = super.getControllerMaster().getCommonBoard();
        WindowPatternCard wpToSet = commonBoard.getWindowPatternCardDeck().getAvailableWP().get(chosenWp - 1);
        commonBoard.getSpecificPlayer(username).setWindowPatternCard(wpToSet);
        SimplifiedWindowPatternCard wpToSend = convertOneWp(chosenWp);

        try{
            //substitute commonBoard.getDraftPool with list of SetUpInformationUnit
            super.getControllerMaster().getConnectedPlayers().get(username).showCommonBoard(draftPoolConverter(), wpToSend);
        } catch (BrokenConnectionException br) {
            //TODO handle broken connection.
        }
    }

    /**
     *
     * @return
     */
    public List<SetUpInformationUnit> draftPoolConverter() {
        List<Die> draftPoolDice = super.getControllerMaster().getCommonBoard().getDraftPool().getAvailableDice();
        List<SetUpInformationUnit> draftPoolInfo = new ArrayList<>();
        for(Die die : draftPoolDice)
            draftPoolInfo.add(new SetUpInformationUnit(draftPoolDice.indexOf(die), die.getDieColor(), die.getActualDieValue()));
        return draftPoolInfo;
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
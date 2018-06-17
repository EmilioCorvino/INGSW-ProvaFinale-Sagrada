package it.polimi.ingsw.model.cards.tool.PlacementEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.model.restrictions.ValueRestriction;

import java.util.ArrayList;
import java.util.List;



/**
 * This class manages the tool effect that allows the user to place a die ignoring the color restriction but
 * respecting only the value restriction of the personal window pattern card.
 */
public class ValueRestrictionEffect extends PlacementRestrictionEffect {

    /**
     *
     * @param manager
     * @param setUpInfoUnit
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        Player currPlayer = manager.getControllerMaster().getGameState().getCurrentPlayer();
        WindowPatternCard wp = currPlayer.getWindowPatternCard();
        wp.copyGlassWindow(wp.getGlassWindowCopy(), wp.getGlassWindow());
        Die chosenDie = wp.getGlassWindowCopy()[setUpInfoUnit.getSourceIndex()/WindowPatternCard.MAX_COL][setUpInfoUnit.getSourceIndex() % WindowPatternCard.MAX_COL].getContainedDie();
        Cell[][] gwCopy = wp.getGlassWindowCopy();
        deleteColorRestriction(gwCopy);

        wp.setDesiredCell(new Cell(setUpInfoUnit.getDestinationIndex()/WindowPatternCard.MAX_COL , setUpInfoUnit.getDestinationIndex() % WindowPatternCard.MAX_COL));

        if(!wp.canBePlaced(chosenDie, wp.getDesiredCell(), gwCopy)) {
            manager.showNotification(wp.getErrorMessage());
            manager.setMoveLegal(false);
            return;
        }

        //restoreGlassWindow(wp, setUpInfoUnit.getDestinationIndex());
        wp.copyGlassWindow(gwCopy, wp.getGlassWindow());
       super.updateContainer(wp, setUpInfoUnit);
    }

    /**
     * This method deletes all the color restriction in the copy of the original glass window.
     * @param gwCopy the glass window to modify.
     */
    public void deleteColorRestriction(Cell[][] gwCopy) {
        List<ARestriction> list;
        for(int i=0; i<WindowPatternCard.MAX_ROW; i++)
            for(int j=0; j<WindowPatternCard.MAX_COL; j++) {
                if(!gwCopy[i][j].isEmpty()) {
                    list = new ArrayList<>();
                    ARestriction value = new ValueRestriction(gwCopy[i][j].getContainedDie().getActualDieValue());
                    list.add(value);
                    gwCopy[i][j].updateRuleSet(list);
                } else {
                    list = new ArrayList<>();
                    if(gwCopy[i][j].getDefaultValueRestriction() != null) {
                        int val = gwCopy[i][j].getDefaultValueRestriction().getValue();
                        ARestriction value = new ValueRestriction(val);
                        list.add(value);
                        gwCopy[i][j].updateRuleSet(list);
                    }
                }
            }
    }

    /*
    public void restoreGlassWindow(WindowPatternCard wp, int index) {

        Cell[][] gwCopy = wp.getGlassWindowCopy();
        Cell[][] gw = wp.getGlassWindow();

        for(int i=0; i<WindowPatternCard.MAX_COL; i++)
            for(int j=0; j<WindowPatternCard.MAX_COL; j++)
                if(i == index/WindowPatternCard.MAX_COL && j == index % WindowPatternCard.MAX_COL)




    }
    */
}
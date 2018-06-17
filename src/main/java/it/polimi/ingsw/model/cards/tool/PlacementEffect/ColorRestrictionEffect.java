package it.polimi.ingsw.model.cards.tool.PlacementEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.containers.WindowPatternCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.restrictions.ARestriction;
import it.polimi.ingsw.model.restrictions.ColorRestriction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the effect that allows the player to place a die ignoring the value restriction but
 * respecting only the color restriction of the personal window pattern card.
 */
public class ColorRestrictionEffect extends PlacementRestrictionEffect {

    /**
     * This method makes a copy of the original window pattern card, modifies it and then executes the die
     * placement with the modified one.
     * @param manager the controller.
     * @param setUpInfoUnit the info unit to use.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {
        Player currPlayer = manager.getControllerMaster().getGameState().getCurrentPlayer();
        WindowPatternCard wp = currPlayer.getWindowPatternCard();
        wp.copyGlassWindow(wp.getGlassWindowCopy(), wp.getGlassWindow());
        Die chosenDie = wp.getGlassWindowCopy()[setUpInfoUnit.getSourceIndex()/WindowPatternCard.MAX_COL][setUpInfoUnit.getSourceIndex() % WindowPatternCard.MAX_COL].getContainedDie();
        Cell[][] gwCopy = wp.getGlassWindowCopy();

        deleteValueRestriction(gwCopy);

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
    public void deleteValueRestriction(Cell[][] gwCopy) {
        List<ARestriction> list;
        for(int i=0; i<WindowPatternCard.MAX_ROW; i++)
            for(int j=0; j<WindowPatternCard.MAX_COL; j++) {
                if(!gwCopy[i][j].isEmpty()) {
                    list = new ArrayList<>();
                    ARestriction value = new ColorRestriction(gwCopy[i][j].getContainedDie().getDieColor());
                    list.add(value);
                    gwCopy[i][j].updateRuleSet(list);
                } else {
                    list = new ArrayList<>();
                    if(gwCopy[i][j].getDefaultValueRestriction() != null) {
                        Color val = gwCopy[i][j].getDefaultColorRestriction().getColor();
                        ARestriction value = new ColorRestriction(val);
                        list.add(value);
                        gwCopy[i][j].updateRuleSet(list);
                    }
                }
            }
    }
}
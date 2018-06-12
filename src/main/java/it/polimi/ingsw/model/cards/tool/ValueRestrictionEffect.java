package it.polimi.ingsw.model.cards.tool;

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
        Die chosenDie = new Die(setUpInfoUnit.getValue(), setUpInfoUnit.getColor());
        WindowPatternCard wp = currPlayer.getWindowPatternCard();

        if(!super.checkContainedDie(wp, chosenDie, manager))
            return;

        wp.copyGlassWindow();
        Cell[][] playerGlassWindow = wp.getGlassWindow();

        Cell[][] glassWindow = new Cell[WindowPatternCard.getMaxRow()][WindowPatternCard.getMaxCol()];
        List<ARestriction> ruleSet;

        //creates a copy of the original glass window
        for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
            for(int j=0; j<WindowPatternCard.getMaxCol(); j++) {
                ruleSet = new ArrayList<>();
                if(!playerGlassWindow[i][j].isEmpty()) {
                    Die die = playerGlassWindow[i][j].getContainedDie();
                    ruleSet.add(new ValueRestriction(die.getActualDieValue()));
                } else {
                    ruleSet.add(new ValueRestriction(playerGlassWindow[i][j].getDefaultValueRestriction().getValue()));
                }
                glassWindow[i][j].setRuleSetCell(ruleSet);
            }

        //ocio alla rimozione del dado.
        wp.removeDie(chosenDie);
        wp.setGlassWindow(glassWindow);
        wp.setGlassWindowModified(true);
        super.executeMove(manager, setUpInfoUnit);
    }
}

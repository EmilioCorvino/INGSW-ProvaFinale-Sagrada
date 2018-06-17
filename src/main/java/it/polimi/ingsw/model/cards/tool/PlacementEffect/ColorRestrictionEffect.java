package it.polimi.ingsw.model.cards.tool.PlacementEffect;

import it.polimi.ingsw.controller.managers.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
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


        Cell[][] playerGlassWindow = wp.getGlassWindow();

        Cell[][] glassWindow = new Cell[WindowPatternCard.getMaxRow()][WindowPatternCard.getMaxCol()];
        List<ARestriction> ruleSet;

        //creates a copy of the original glass window
        for(int i=0; i<WindowPatternCard.getMaxRow(); i++)
            for(int j=0; j<WindowPatternCard.getMaxCol(); j++) {
                ruleSet = new ArrayList<>();
                if(!playerGlassWindow[i][j].isEmpty()) {
                    Die die = playerGlassWindow[i][j].getContainedDie();
                    ruleSet.add(new ColorRestriction(die.getDieColor()));
                } else {
                    ruleSet.add(new ColorRestriction(playerGlassWindow[i][j].getDefaultColorRestriction().getColor()));
                }
                glassWindow[i][j].setRuleSetCell(ruleSet);
            }
        wp.setGlassWindow(glassWindow);
        wp.setGlassWindowModified(true);
        super.executeMove(manager, setUpInfoUnit);
    }
}
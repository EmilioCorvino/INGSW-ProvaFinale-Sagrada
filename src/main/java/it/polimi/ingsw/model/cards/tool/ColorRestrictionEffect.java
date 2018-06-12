package it.polimi.ingsw.model.cards.tool;

/**
 * This class manages the effect that allows the player to place a die ignoring the value restriction but
 * respecting only the color restriction of the personal window pattern card.
 */
public class ColorRestrictionEffect extends PlacementRestrictionEffect {

    /**
     *
     * @param manager
     * @param setUpInfoUnit
     */
    /*@Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {
        Player currPlayer = manager.getPlayerList().get(manager.getCurrentPlayer());
        WindowPatternCard wp = currPlayer.getWindowPatternCard();
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
                    ruleSet.add(new ColorRestriction(die.getDieColor()));
                } else {
                    ruleSet.add(new ColorRestriction(playerGlassWindow[i][j].getDefaultColorRestriction().getColor()));
                }
                glassWindow[i][j].setRuleSetCell(ruleSet);
            }

        wp.setGlassWindow(glassWindow);
        wp.setGlassWindowModified(true);
        super.executeMove(manager, setUpInfoUnit);
    }*/
}

package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.WindowPatternCard;

/**
 * This type of {@link APublicObjectiveCard} relies on Die {@link it.polimi.ingsw.model.Color} to compute the score,
 * applying the selected positional {@link IScoreComputationStrategy}.
 * Each card is filled with {@link com.google.gson.Gson} parser.
 */
public class ColorPublicObjectiveCard extends APublicObjectiveCard {

    /**
     * Applies the card strategy (based on {@link it.polimi.ingsw.model.Color} to compute the score.
     * @param windowPatternCard pattern card analyzed to get the score.
     * @return the score from the card.
     */
    @Override
    public int analyzeWindowPatternCard(WindowPatternCard windowPatternCard) {
        return this.getStrategy().applyColorStrategy(this, windowPatternCard);
    }

    /**
     * Allows the {@link IObjectiveCardVisitor} to visit this class and launch the window pattern card analysis.
     * @param objectiveCardVisitor visitor to accept.
     * @param windowPatternCard window pattern card to analyze.
     */
    @Override
    public void accept(IObjectiveCardVisitor objectiveCardVisitor, WindowPatternCard windowPatternCard) {
        objectiveCardVisitor.visit(this, windowPatternCard);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }



    /*public static void main(String args[]) {
        ColorPublicObjectiveCard card = new ColorPublicObjectiveCard();
        card.id = 200;
        card.name = "Colori diversi - Riga";
        card.description = "Righe senza colori ripetuti";
        card.pointsForIteration = 6;
        //card.strategy = new RowStrategy();
        ((RowStrategy) card.strategy).setStrategyName("row");

        Gson gson = new Gson();
        System.out.println(gson.toJson(card));
    }*/
}

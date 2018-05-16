package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

/**
 * This visitor interface is used to visit the right concrete instance of {@link AObjectiveCard}.
 * Classes implementing this interface:
 * @see ObjectiveCardAnalyzerVisitor
 */
public interface IObjectiveCardVisitor {

    public void visit(PrivateObjectiveCard privateObjectiveCard, WindowPatternCard windowPatternCard);

    public void visit(ColorPublicObjectiveCard colorPublicObjectiveCard, WindowPatternCard windowPatternCard);

    public void visit(ValuePublicObjectiveCard valuePublicObjectiveCard, WindowPatternCard windowPatternCard);
}

package it.polimi.ingsw.model.cards.objective;

import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.cards.objective.privates.PrivateObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.model.cards.objective.publics.ValuePublicObjectiveCard;

/**
 * This visitor interface is used to visit the right concrete instance of {@link AObjectiveCard}.
 * Classes implementing this interface:
 * @see ObjectiveCardAnalyzerVisitor
 */
public interface IObjectiveCardVisitor {

    void visit(PrivateObjectiveCard privateObjectiveCard, WindowPatternCard windowPatternCard);

    void visit(ColorPublicObjectiveCard colorPublicObjectiveCard, WindowPatternCard windowPatternCard);

    void visit(ValuePublicObjectiveCard valuePublicObjectiveCard, WindowPatternCard windowPatternCard);
}

package it.polimi.ingsw.server.model.cards.objective;

import it.polimi.ingsw.server.model.cards.objective.privates.PrivateObjectiveCard;
import it.polimi.ingsw.server.model.cards.objective.publics.ColorPublicObjectiveCard;
import it.polimi.ingsw.server.model.cards.objective.publics.ValuePublicObjectiveCard;
import it.polimi.ingsw.server.model.die.containers.WindowPatternCard;

/**
 * This visitor interface is used to visit the right concrete instance of {@link AObjectiveCard}.
 * Classes implementing this interface (see those for methods documentation):
 * @see ObjectiveCardAnalyzerVisitor
 */
public interface IObjectiveCardVisitor {

    void visit(PrivateObjectiveCard privateObjectiveCard, WindowPatternCard windowPatternCard);

    void visit(ColorPublicObjectiveCard colorPublicObjectiveCard, WindowPatternCard windowPatternCard);

    void visit(ValuePublicObjectiveCard valuePublicObjectiveCard, WindowPatternCard windowPatternCard);
}

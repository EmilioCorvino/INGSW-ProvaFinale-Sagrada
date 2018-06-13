package it.polimi.ingsw.model.move;

/**
 * This class is the main class used to decorate the default move with some tool effect. It is possible to
 * addDie new kind of cards that provide new effects in the future, by extending this class.
 */
public abstract class AEffectMoveDecorator implements IMove {

    /**
     * The object to decorate.
     */
    protected IMove decoratedMove;



    public IMove getDecoratedMove() {
        return decoratedMove;
    }

    public void setDecoratedMove(IMove decoratedMove) {
        this.decoratedMove = decoratedMove;
    }
}

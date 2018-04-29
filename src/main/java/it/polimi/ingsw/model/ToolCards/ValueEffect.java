package it.polimi.ingsw.model.ToolCards;

/**
 * This class manages the effects of the tool cards related to a change of the value of one or more dice.
 */
public class ValueEffect extends AToolCardEffect {

    /**
     * The offset that determines the interval in which the value that will be chosen must respect.
     */
    protected int offset;

    /**
     * This constructor will set e default value for the offset in case it is not specified;
     */
    public ValueEffect() {
        this.offset = 0;
    }

    /**
     * This constructor will set a specific value to use when the effect will be applied.
     * @param offset: the specific value for the offset to set.
     */
    public ValueEffect(int offset) {
        this.offset = offset;
    }

    /**
     * This method checks if the result of an operation on the value of die is a right value.
     * @param dieValue: the value of the die.
     * @return true if the value that will be set is coherent.
     */
    protected boolean checkValue(int dieValue) {
        return dieValue >=1 && dieValue <= 6;
    }
}

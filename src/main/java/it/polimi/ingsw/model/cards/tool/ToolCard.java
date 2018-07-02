package it.polimi.ingsw.model.cards.tool;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.move.AMove;

import java.util.ArrayList;
import java.util.List;

public class ToolCard implements IToolCard {

    /**
     * ID that identifies a card.
     */
    private int id;

    /**
     * Name of the command related to the Tool Card.
     */
    @SerializedName("command")
    private Commands commandName;

    /**
     * Name of the card.
     */
    private String name;

    /**
     * Description of the card.
     */
    private String description;

    /**
     * Color of the card. This is only used in single-player mode.
     */
    @SerializedName("color")
    private Color singlePlayerColor;

    /**
     * Turn in which the card is available.
     * 0 = first
     * 1 = second
     * 2 = both
     */
    @SerializedName("turn")
    private int availableTurn;

    /**
     * This boolean is used to understand whether hte card implies a die placement in the {@link it.polimi.ingsw.model.turn.Turn}
     * in which it is used or not.
     * N.B.: the tool card 8 does not imply a placement within a turn because it anticipates the placement of the
     * next turn.
     */
    @SerializedName("placement")
    private boolean impliesPlacement;

    /**
     * Object storing information needed to build the effects after the parsing.
     */
    @SerializedName("effect")
    private EffectBuilder effectBuilder;

    /**
     * Effects possessed by the card.
     */
    private transient List<AMove> cardEffects;

    /**
     * Checks if the available turn in which the tool card can be used corresponds to the current turn.
     * @param currentTurn: the turn used to compare with the turn available for the tool card to be used.
     * @return true if the current turn is equal to the turn in which the card can be used.
     */
    public boolean canBeUsed(int currentTurn) {
        return this.availableTurn == currentTurn || this.availableTurn == 2;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Color getSinglePlayerColor() {
        return singlePlayerColor;
    }

    public boolean impliesPlacement() {
        return impliesPlacement;
    }

    public EffectBuilder getEffectBuilder() {
        return effectBuilder;
    }

    public List<AMove> getCardEffects() {
        return cardEffects;
    }

    public void initializeCardEffects() {
        this.cardEffects = new ArrayList<>();
    }

    public int getAvailableTurn() {
        return availableTurn;
    }

    public void setAvailableTurn(int availableTurn) {
        this.availableTurn = availableTurn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Commands getCommandName() {
        return commandName;
    }
}
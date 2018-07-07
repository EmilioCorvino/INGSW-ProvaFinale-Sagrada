package it.polimi.ingsw.server.model.cards.tool;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.server.model.cards.tool.effects.AToolCardEffect;
import it.polimi.ingsw.server.model.cards.tool.effects.draft.DraftValueEffect;
import it.polimi.ingsw.server.model.cards.tool.effects.value.ChooseValueEffect;

/**
 * This class is used to store the parsed information used to build the {@link AToolCardEffect}.
 */
public class EffectBuilder {

    /**
     * Name of the effects to create.
     */
    @SerializedName("names")
    private String[] effects;

    /**
     * {@link it.polimi.ingsw.server.model.die.containers.ADieContainer} acting as the source of the move.
     */
    @SerializedName("source")
    private String sourceContainer;

    /**
     * {@link it.polimi.ingsw.server.model.die.containers.ADieContainer} acting as the destination of the move.
     */
    @SerializedName("destination")
    private String destinationContainer;

    /**
     * Additional parameter that some effects need.
     * @see ChooseValueEffect
     * @see DraftValueEffect
     */
    @SerializedName("parameter")
    private int effectSpecificParameter;

    /**
     * Signals if the card requires more than one interaction with the player.
     */
    private boolean multipleInteractions;


    public String[] getEffects() {
        return effects;
    }

    int getEffectSpecificParameter() {
        return effectSpecificParameter;
    }

    public boolean requiresOnlyOneInteraction() {
        return !multipleInteractions;
    }
}

package it.polimi.ingsw.model.cards.tool;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.model.cards.tool.valueeffects.ChooseValueEffect;
import it.polimi.ingsw.model.cards.tool.valueeffects.DraftValueEffect;

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
     * {@link it.polimi.ingsw.model.die.containers.ADieContainer} acting as the source of the move.
     */
    @SerializedName("source")
    private String sourceContainer;

    /**
     * {@link it.polimi.ingsw.model.die.containers.ADieContainer} acting as the destination of the move.
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

    public String getSourceContainer() {
        return sourceContainer;
    }

    public String getDestinationContainer() {
        return destinationContainer;
    }

    public int getEffectSpecificParameter() {
        return effectSpecificParameter;
    }

    public boolean requiresMultipleInteractions() {
        return multipleInteractions;
    }

    //public AToolCardEffect getNewEffectToAdd(String effectName) {

    //}
}

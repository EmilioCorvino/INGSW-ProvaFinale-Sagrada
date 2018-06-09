package it.polimi.ingsw.model.cards.tool;

import com.google.gson.annotations.SerializedName;
import it.polimi.ingsw.controller.Commands;

/**
 * This class is used to store the parsed information used to build the {@link AToolCardEffect}.
 */
public class EffectBuilder {

    /**
     * Name of the effects to create.
     */
    @SerializedName("names")
    private Commands[] effects;

    /**
     * {@link it.polimi.ingsw.model.die.diecontainers.ADieContainer} acting as the source of the move.
     */
    @SerializedName("source")
    private String sourceContainer;

    /**
     * {@link it.polimi.ingsw.model.die.diecontainers.ADieContainer} acting as the destination of the move.
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


    public Commands[] getEffects() {
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
}

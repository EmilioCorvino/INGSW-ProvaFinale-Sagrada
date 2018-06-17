package it.polimi.ingsw.model.cards.tool;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.model.cards.tool.PlacementEffect.*;
import it.polimi.ingsw.model.cards.tool.SwapEffect.SwapFromDraftPoolToDicebag;
import it.polimi.ingsw.model.cards.tool.SwapEffect.SwapFromDraftpoolToRoundTrack;
import it.polimi.ingsw.model.cards.tool.ValueEffects.ChooseValueEffect;
import it.polimi.ingsw.model.cards.tool.ValueEffects.DraftValueEffect;
import it.polimi.ingsw.model.cards.tool.ValueEffects.OppositeValueEffect;
import it.polimi.ingsw.utils.exceptions.EmptyException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;

/**
 * This class contains a collection of {@link ToolCard}.
 * It initializes them parsing information from file.
 */
public class ToolCardsDeck {

    private static final String TOOL_CARDS = "./src/main/resources/cards/tool/toolCards.json";

    /**
     * List containing all the {@link ToolCard}s of the game.
     */
    private List<ToolCard> deck;

    public List<ToolCard> getDeck() {
        return deck;
    }

    /**
     * This method uses {@link Gson} to parse all {@link ToolCard}s from a JSON file.
     */
    public void parseDeck() {
        Gson gson = new Gson();
        //The TypeToken is needed to get the full parametrized type of the collection.
        Type listCards = new TypeToken<Vector<ToolCard>>(){}.getType();
        try(Reader file = new FileReader(TOOL_CARDS)) {
            this.deck = gson.fromJson(file, listCards);
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to access tool cards file", e);
        }

        try {
            this.assignEffect();
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Draws a random card and removes it from the deck.
     * @return a random {@link ToolCard} from the deck.
     */
    public ToolCard drawCard() throws EmptyException {
        if(this.deck.isEmpty()) {
            throw new EmptyException("The tool card deck is empty");
        }
        int index = new Random().nextInt(this.deck.size());
        return this.deck.remove(index);
    }

    /**
     * Assigns the right effect to the {@link ToolCard} based on the command found on file and saved in
     * {@link EffectBuilder}
     * @throws IOException when the effect name found on file is not present in the game.
     */
    private void assignEffect() throws IOException {
        for(ToolCard card: this.getDeck()) {
            card.initializeCardEffects();
            EffectBuilder builder = card.getEffectBuilder();
            for(Commands effectName: builder.getEffects()) {
                switch (effectName) {
                    case CHOOSE_VALUE_EFFECT:             card.getCardEffects().add(new ChooseValueEffect(builder.
                                                            getEffectSpecificParameter()));
                                                          break;
                    case ADJIACENT_RESTRICTION_EFFECT:    card.getCardEffects().add(new AdjiacentCellsRestrictionEffect());
                                                          break;
                    case OPPOSITE_VALUE_EFFECT:           card.getCardEffects().add(new OppositeValueEffect());
                                                          break;
                    case SWAP_DRAFTPOOL_DICEBAG:          card.getCardEffects().add(new SwapFromDraftPoolToDicebag());
                                                          break;
                    case COLOR_PLACEMENT_RESTRICTION_EFFECT: card.getCardEffects().add(new ColorPlacementRestrictionEffect());
                                                          break;
                    case VALUE_RESTRICTION_EFFECT:        card.getCardEffects().add(new ValueRestrictionEffect());
                                                          break;
                    case COLOR_RESTRICTION_EFFECT:        card.getCardEffects().add(new ColorRestrictionEffect());
                                                          break;
                    case PLACEMENT_RESTRICTION_EFFECT:    card.getCardEffects().add(new PlacementRestrictionEffect());
                                                          break;
                    case SWAP_DRAFTPOOL_ROUNDTRACK:       card.getCardEffects().add(new SwapFromDraftpoolToRoundTrack());
                                                          break;
                    case DRAFT_VALUE_EFFECT:              card.getCardEffects().add(new DraftValueEffect(builder.
                                                            getEffectSpecificParameter()));
                                                          break;
                    default:                              throw new IOException("Illegal effect found on file.");
                }
            }
        }
    }
}

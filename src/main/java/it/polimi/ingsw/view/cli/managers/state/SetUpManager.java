package it.polimi.ingsw.view.cli.managers.state;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.utils.SagradaLogger;
import it.polimi.ingsw.view.cli.boardelements.PlayerView;
import it.polimi.ingsw.view.cli.boardelements.ToolCardView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;
import it.polimi.ingsw.view.cli.managers.general.InputOutputManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * This class manages all the interaction during the game's state of set up.
 */
public class SetUpManager {

    private InputOutputManager inputOutputManager;

    public SetUpManager(InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
    }

    /**
     * This method print for maps to the user.
     * @param listWp: The list of maps need to be choose.
     */
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp){
        List<WindowPatternCardView> cards = new ArrayList<>();

        for (SimplifiedWindowPatternCard swp : listWp)
            cards.add(new WindowPatternCardView(swp));


        inputOutputManager.print(   "\n--------------------------------" +
                                    "\n Scegli la WP desiderata tra: ");

        for (WindowPatternCardView wp : cards)
            inputOutputManager.print(wp.wpToString());
    }

    /**
     * This method ask to the user which maps want to select
     * @return the id of the map chosen.
     */
    public int getIdChosen(){
        return inputOutputManager.askInt("\nInserire l'id della mappa scelta: ");
    }

    /**
     * This method populates the Public Objective Cards of the common board, loading files from resources.
     * @param id the id of the cards drawn by the controller.
     * @param cards the list of cards of the common board, represented with their description.
     */
    public void createPubObjCards(int[] id, List<String> cards){

        for (int i : id){
            try (Reader file = new FileReader("./src/main/resources/cards/publicObjectiveText/pubObj" + i)){
                BufferedReader b = new BufferedReader(file);
                String s = b.readLine();
                if (!cards.contains(s)) {
                    cards.add(s);
                }
            } catch (IOException e) {
                SagradaLogger.log(Level.SEVERE, "Public Objective Card txt file can't be read!", e);
            }
        }
    }

    /**
     * This method populates the Tool Cards of the common board, loading files from resources.
     * @param id the id of the cards drawn by the controller.
     * @param cards the list of cards of the common board, represented using {@link ToolCardView} class.
     */
    public void createToolCards(int[] id, List<ToolCardView> cards){

        for (int i : id){
            try (Reader file = new FileReader("./src/main/resources/cards/toolCardsText/toolCard" + i)){
                BufferedReader b = new BufferedReader(file);
                String s = b.readLine();
                List<Commands> toolsPresent = new ArrayList<>();
                cards.forEach(card -> toolsPresent.add(card.getCommand()));
                if (!toolsPresent.contains(Commands.values()[i-300+2])) {
                    cards.add(new ToolCardView(s, 1, Commands.values()[i - 300 + 2]));
                }
            } catch (IOException e) {
                SagradaLogger.log(Level.SEVERE, "Tool Card txt file can't be read!", e);
            }
        }
    }

    /**
     * This method populates the Private Objective Card of the player, loading files from resources.
     * @param id the id of the card drawn by the controller.
     * @param p player to whom the card has to be set.
     */
    public void createPrivateObjCard(int id, PlayerView p){

        try (Reader file = new FileReader("./src/main/resources/cards/privateObjectiveText/privObj" + id)){
            BufferedReader b = new BufferedReader(file);
            String s = b.readLine();
            p.setPrivateObjCard(s);
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Private Objective Card txt file can't be read!", e);
        }

    }

}

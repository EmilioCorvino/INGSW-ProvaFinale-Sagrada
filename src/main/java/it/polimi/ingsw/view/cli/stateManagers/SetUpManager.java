package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.cli.generalManagers.InputOutputManager;
import it.polimi.ingsw.view.cli.boardElements.PlayerView;
import it.polimi.ingsw.view.cli.die.WindowPatternCardView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

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
     * @return: the id of the map chosen.
     */
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp){
        List<WindowPatternCardView> cards = new ArrayList<>();

        for (SimplifiedWindowPatternCard swp : listWp)
            cards.add(new WindowPatternCardView(swp));


        inputOutputManager.print(   "\n--------------------------------" +
                                    "\n Scegli la WP desiderata tra: ");

        for (WindowPatternCardView wp : cards)
            wp.printWp();
    }

    /**
     * This method ask to the user which maps want to select
     * @return: the id of the map chosen.
     */
    public int getIdChosen(){
        boolean validInput = false;
        String idChosen = null;

        while(!validInput) {
            idChosen = inputOutputManager.askInformation("\nInserire l'id della mappa scelta: ");
            validInput = Pattern.matches("\\d*", idChosen);
        }
        return Integer.parseInt(idChosen);
    }

    /**
     * This method populate the cards of the common board, loading files from resources.
     * @param id: the id of the cards drown by the controller.
     * @param cards: the list (maps) of cards of the common board.
     */
    public void createPubObjCards(int[] id, List<String> cards){

        for (int i : id){
            try (Reader file = new FileReader("./src/main/resources/cards/publicObjectiveText/pubObj" + i)){
                BufferedReader b = new BufferedReader(file);
                String s = b.readLine();
                cards.add(s);
            } catch (IOException e) {
                SagradaLogger.log(Level.WARNING, "Public Objective Card txt file can't be read!", e);
            }
        }
    }

    public void createToolCards(int[] id, Map<String, Integer> cards){

        for (int i : id){
            try (Reader file = new FileReader("./src/main/resources/cards/toolCardsText/toolCard" + i)){
                BufferedReader b = new BufferedReader(file);
                String s = b.readLine();
                cards.put(s,1);
            } catch (IOException e) {
                SagradaLogger.log(Level.WARNING, "Tool Card txt file can't be read!", e);
            }
        }
    }

    public void createPrivateObjCard(int id, PlayerView p){

        try (Reader file = new FileReader("./src/main/resources/cards/privateObjectiveText/privObj" + id)){
            BufferedReader b = new BufferedReader(file);
            String s = b.readLine();
            p.setPrivateObjCard(s);
        } catch (IOException e) {
            SagradaLogger.log(Level.WARNING, "Private Objective Card txt file can't be read!", e);
        }

    }

    /**
     * This method print all the public objective.
     * @param cards: The list public objective card.
     */
    public void printPubObj(List<String> cards){
        int index = 1;

        inputOutputManager.print("\nCarte obiettivo pubblico: ");
        for (String s : cards){
            inputOutputManager.print("\t - " + index + ": " + s);
            index ++;
        }
    }

    /**
     * This method print all the tool cards.
     * @param cards: The list of Tool Cards.
     */
    public void printTool(Map<String, Integer> cards){
        int index = 1;

        inputOutputManager.print("\nCarte strumento: ");
        for (Map.Entry<String,Integer> s : cards.entrySet()){
            inputOutputManager.print("\t - " + index + ": " + s.getKey() + " | Segnalini favore da usare: " + s.getValue());
            index ++;
        }
    }

    /**
     * This method print the private objective.
     * @param card: The private objective card.
     */
    public void printPrivateObj(String card){
        inputOutputManager.print("\nIl tuo obiettivo privato e': "+ card);
    }

    /**
     * This method print all the wp of the other player in the game.
     * @param players: all the players in the match
     */
    public void printAllWp(List<PlayerView> players){
        players.forEach(p -> p.getWp().printWp());
    }

}

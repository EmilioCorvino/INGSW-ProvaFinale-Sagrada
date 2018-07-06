package it.polimi.ingsw.client.view.gui.gamewindows;

import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundTrackGUI extends VBox {

    private static final int NUM_ROUND = 10;

    private List<String> colorRound;

    private StackPane diceRound;

    /**
     * This attribute represents the object used to construct dice.
     */
    private DieFactory dieFactory;

    /**
     * This attribute maps each id for the round with its specific list of available dice
     * updated dynamically when required.
     */
    private Map<Integer, List<SetUpInformationUnit>> allDiceRound;

    private int round;

    private int offset;

    private boolean isRoundChosen;


    public RoundTrackGUI() {

        dieFactory = new DieFactory();
        allDiceRound = new HashMap<>();

        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.getStylesheets().add("style/backgrounds.css");
        this.getStyleClass().add("map-background");
        this.setMinHeight(670);
        this.setMaxHeight(670);
        this.setMinWidth(85);

        colorRound = new ArrayList<>();
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(204, 0, 102, 0.4), rgb(128, 0, 0, 0.4))");//1
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(255, 102, 0, 0.4), rgb(204, 0, 0, 0.4))");//2
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(255, 255, 0, 0.4), rgb(255, 153, 51, 0.4))");//3
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(204, 255, 102, 0.4), rgb(255, 255, 102, 0.4))");//4
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(51, 153, 102, 0.4), rgb(51, 204, 0, 0.4))");//5
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(51, 204, 204, 0.4), rgb(0, 153, 153, 0.4))");//6
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(0, 51, 102, 0.4), rgb(51, 153, 255, 0.4))");//7
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(0, 51, 204, 0.4), rgb(0, 51, 102, 0.4))");//8
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(0, 0, 153, 0.4), rgb(0, 0, 204, 0.4))");//9
        colorRound.add("-fx-background-color: linear-gradient(to top, rgb(51, 0, 102, 0.4), rgb(0, 51, 102, 0.4))");//10

        for(int i=0; i<10; i++)
            this.allDiceRound.put(i+1, new ArrayList<>());

        for(int i=0; i<NUM_ROUND; i++) {

            StackPane mainBase = new StackPane();
            this.getChildren().add(mainBase);

            StackPane baseRound = new StackPane();
            baseRound.setMinHeight(45);
            baseRound.setMaxHeight(45);
            baseRound.setMaxWidth(45);
            baseRound.setMinWidth(45);

            Pane base = new Pane();
            base.setStyle(colorRound.get(i));

            Label round = new Label((i+1) + "");
            round.getStyleClass().add("text-label");

            baseRound.getChildren().addAll(base, round);
            mainBase.getChildren().add(baseRound);

            StackPane diceRound = new StackPane();
            diceRound.setMinHeight(45);
            diceRound.setMaxHeight(45);
            diceRound.setMaxWidth(45);
            diceRound.setMinWidth(45);
            mainBase.getChildren().add(diceRound);
        }

        for(int i=0; i<this.getChildren().size(); i++) {
            StackPane main = (StackPane)this.getChildren().get(i);
            main.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> slideInDiceHandler(main));
            main.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> slideOutDiceHandler(main));
        }
    }

    /**
     * This method adds a die on a specific round of the round track GUI of the game.
     * @param informationUnit the object to use to construct a die to put on a round.
     */
    public void addDieToRound(SetUpInformationUnit informationUnit) {
        StackPane mainRoundStack = (StackPane)this.getChildren().get(informationUnit.getDestinationIndex());
        StackPane diceRound = (StackPane)mainRoundStack.getChildren().get(1);
        DieGUI dieToAdd = this.dieFactory.getsDieGUI(informationUnit);
        diceRound.getChildren().add(dieToAdd);
        //dieToAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> roundDieClickedHandler(mainRoundStack, dieToAdd));
        //adds the information of the die to allow the construction of an updated list of dice each time.
        this.allDiceRound.get((informationUnit.getDestinationIndex() + 1)).add(informationUnit);
    }

    /**
     * This method allows to view all the available dice for a specific round.
     * @param stack the main stack container of the dice.
     */
    private void slideInDiceHandler(StackPane stack) {
        int index = this.getChildren().indexOf(stack);
        StackPane diceRound = (StackPane)stack.getChildren().get(1);
        List<SetUpInformationUnit> list = this.allDiceRound.get(index + 1 );
        HBox box = new HBox();
        if(list.size() > 0) {
            box.setSpacing(10);
            box.setPadding(new Insets(5));
            list.forEach( elem -> {
                DieGUI die = this.dieFactory.getsDieGUI(elem);
                box.getChildren().add(die);
                die.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> roundDieClickedHandler(stack, die, box));
            });
            diceRound.getChildren().add(box);
        }

        //This adds a translate transition to the hbox of dice of the selected round to show.
        TranslateTransition tt = new TranslateTransition(Duration.millis(10), box);
        tt.setByX( stack.getLayoutX() );
        tt.play();
    }

    /**
     * This method removes the box of all the dice present in a round, after the mouse exits the
     * round container area.
     * @param main the main stack container of the dice of a single round.
     */
    private void slideOutDiceHandler(StackPane main) {
        StackPane diceRound = (StackPane)main.getChildren().get(1);
        int size = diceRound.getChildren().size();
        if(size> 1)
            diceRound.getChildren().remove(size -1 );
    }

    private void roundDieClickedHandler(StackPane main, DieGUI die, HBox box) {
        int index = this.getChildren().indexOf(main);
        setRound(index);
        int offset = box.getChildren().indexOf(die);
        setOffset(offset);
        setRoundChosen(true);
    }

    public void removeOneDie(SetUpInformationUnit info) {
        StackPane mainRoundStack = (StackPane)this.getChildren().get(info.getSourceIndex());
        StackPane diceRound = (StackPane)mainRoundStack.getChildren().get(1);
        diceRound.getChildren().remove(info.getOffset());

        List<SetUpInformationUnit> list = this.allDiceRound.get(info.getSourceIndex() + 1);
        list.remove(info.getOffset());

    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isRoundChosen() {
        return isRoundChosen;
    }

    public void setRoundChosen(boolean roundChosen) {
        isRoundChosen = roundChosen;
    }
}

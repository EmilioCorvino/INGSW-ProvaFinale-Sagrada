package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
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

    private DieFactory dieFactory;

    private Map<Integer, List<SetUpInformationUnit>> allDiceRound;


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

        this.allDiceRound.get((informationUnit.getDestinationIndex() + 1)).add(informationUnit);

    }

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
            });
            diceRound.getChildren().add(box);
        }
        System.out.println("layout x: " + stack.getLayoutX());
        TranslateTransition tt = new TranslateTransition(Duration.millis(10), box);
        tt.setByX( stack.getLayoutX() );
        tt.play();
        }



    private void slideOutDiceHandler(StackPane main) {
        StackPane diceRound = (StackPane)main.getChildren().get(1);
        int size = diceRound.getChildren().size();
        if(size> 1)
            diceRound.getChildren().remove(size -1 );
    }



    public StackPane getDiceRound() {
        return diceRound;
    }

    public void setDiceRound(StackPane diceRound) {
        this.diceRound = diceRound;
    }
}

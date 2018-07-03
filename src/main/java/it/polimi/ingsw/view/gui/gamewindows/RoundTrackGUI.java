package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundTrackGUI extends VBox {

    private static final int NUM_ROUND = 10;

    private List<String> colorRound;

    private StackPane diceRound;

    private DieFactory dieFactory;

    private Map<Integer, List<DieGUI>> allDiceRound;


    public RoundTrackGUI() {

        dieFactory = new DieFactory();
        allDiceRound = new HashMap<>();

        for(int i=0; i<10; i++)
            allDiceRound.put(i+1, new ArrayList<>());

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

        for(int i=0; i<NUM_ROUND; i++) {

            StackPane mainBase = new StackPane();
            this.getChildren().add(mainBase);

            StackPane diceRound = new StackPane();
            this.getChildren().add(diceRound);


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

            diceRound = new StackPane();
            diceRound.setMinHeight(45);
            diceRound.setMaxHeight(45);
            diceRound.setMaxWidth(45);
            diceRound.setMinWidth(45);
            mainBase.getChildren().add(diceRound);
        }
    }

    public void addDieToRound(SetUpInformationUnit informationUnit) {
        StackPane mainRoundStack = (StackPane)this.getChildren().get(informationUnit.getDestinationIndex());
        StackPane diceRound = (StackPane)mainRoundStack.getChildren().get(1);
        DieGUI dieToAdd = this.dieFactory.getsDieGUI(informationUnit);
        diceRound.getChildren().add(dieToAdd);

        List<DieGUI> list = this.allDiceRound.get(informationUnit.getDestinationIndex() + 1);
        list.add(dieToAdd);
    }

    public StackPane getDiceRound() {
        return diceRound;
    }

    public void setDiceRound(StackPane diceRound) {
        this.diceRound = diceRound;
    }
}

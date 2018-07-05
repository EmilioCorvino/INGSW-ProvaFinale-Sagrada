package it.polimi.ingsw.view.gui.gamewindows.toolcardsGUImanagers;

import it.polimi.ingsw.view.gui.GUIMain;
import it.polimi.ingsw.view.gui.gamewindows.CommonBoardWindow;
import it.polimi.ingsw.view.gui.gamewindows.DieFactory;
import it.polimi.ingsw.view.gui.gamewindows.DieGUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;
import java.util.Map;

public class ToolWindowBuilder {

    private Map<Integer, Runnable> toolBuilderMap;


    private CommonBoardWindow commonBoardWindow;

    private Stage supportStage;

    public ToolWindowBuilder(CommonBoardWindow commonBoardWindow) {
        this.commonBoardWindow = commonBoardWindow;
        toolBuilderMap = new HashMap<>();
        supportStageBuilder();

        toolBuilderMap.put(1, this::showSupportWindowToolOne);
        toolBuilderMap.put(6, this::showSupportToolWindowFive);
    }

    public void invokeMethodShowWindow(int toolId) {
        this.toolBuilderMap.get(toolId).run();
    }

    private void supportStageBuilder() {
        this.supportStage = new Stage();
        this.supportStage.initStyle(StageStyle.TRANSPARENT);


        GUIMain.centerScreen();

        supportStage.initStyle(StageStyle.TRANSPARENT);
        supportStage.initOwner(GUIMain.getStage());


    }



    public void showSupportWindowToolOne() {
        VBox mainCont = new VBox();
        mainCont.getStylesheets().add("style/backgrounds.css");
        mainCont.getStyleClass().add("background");
        mainCont.getStyleClass().add("VBox");
        mainCont.setMinHeight(300);
        mainCont.setMinWidth(500);
        mainCont.setSpacing(20);
        mainCont.setAlignment(Pos.CENTER);

        Label title = new Label("Scegli un valore");
        title.getStyleClass().add("text-label-bold");
        mainCont.getChildren().add(title);

        HBox secondCont = new HBox();
        secondCont.setSpacing(10);

        VBox buttons = new VBox();
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER);
        HBox valueButtons = new HBox();
        valueButtons.setSpacing(10);

        Button minus = new Button(" - ");
        minus.getStyleClass().add("button-style");

        Label value = new Label("");
        value.setText(this.commonBoardWindow.getDraftPoolGUI().getCurrValue() + "");
        value.getStyleClass().add("text-label-bold");

        Button plus = new Button("+");
        plus.getStyleClass().add("button-style");
        valueButtons.getChildren().addAll(minus, value, plus);
        mainCont.getChildren().add(valueButtons);

        this.commonBoardWindow.getData().getSetUpInformationUnit().setExtraParam(2);
        System.out.println(this.commonBoardWindow.getDraftPoolGUI().getCurrValue());

        minus.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
           // if(!this.commonBoardWindow.getData().isSourceFilled()) {
              //  title.setText("Devi scegliere un dado");
           // } else {
                int val = Integer.parseInt(value.getText());
                int valCurr = this.commonBoardWindow.getDraftPoolGUI().getCurrValue();
                value.setText(val + "");
                val--;

                if(val < valCurr - 1)
                    val = valCurr - 1;
                else if(val < 1)
                    val = 1;

                value.setText(val + "");
                this.commonBoardWindow.getData().getSetUpInformationUnit().setExtraParam(1);
                this.commonBoardWindow.getData().getSetUpInformationUnit().setValue(val);
         //   }
        });

        plus.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {

            //if(!this.commonBoardWindow.getData().isSourceFilled()) {
               // title.setText("Devi scegliere un dado");
           // } else {

                int val = Integer.parseInt(value.getText());
                int valCurr = this.commonBoardWindow.getDraftPoolGUI().getCurrValue();
                val++;

                if(val > valCurr + 1)
                    val = valCurr + 1;
                else if(val > 6)
                    val = 6;

                value.setText(val + "");
                this.commonBoardWindow.getData().getSetUpInformationUnit().setExtraParam(0);
                this.commonBoardWindow.getData().getSetUpInformationUnit().setValue(val);
           // }
        });

        secondCont.getChildren().addAll(buttons);
        mainCont.getChildren().add(secondCont);

        Stage newWindow = new Stage();

        Button ok = new Button("Ok");
        ok.getStyleClass().add("button-style");
        ok.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> newWindow.close());
        buttons.getChildren().add(ok);

        Scene second = new Scene(mainCont);

        newWindow.setScene(second);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(GUIMain.getStage());

        newWindow.setX(GUIMain.getStage().getX() + 200);
        newWindow.setY(GUIMain.getStage().getY() + 100);

        newWindow.initStyle(StageStyle.TRANSPARENT);
        second.setFill(Color.TRANSPARENT);

        newWindow.show();
    }

    private void showSupportToolWindowFive() {

        DieFactory dieFactory =  new DieFactory();
        DieGUI die = dieFactory.getsDieGUI(this.commonBoardWindow.getData().getSetUpInformationUnit());


        VBox dieDrafted = new VBox();
        dieDrafted.setAlignment(Pos.CENTER);
        dieDrafted.getStylesheets().add("style/backgrounds.css");
        dieDrafted.getStyleClass().addAll("VBox", "background");
        dieDrafted.setSpacing(20);
        dieDrafted.setPadding(new Insets(30));

        Label title = new Label("Ecco il dado con il nuovo valore");
        title.getStyleClass().add("text-label");

        Button placeIt = new Button("Piazzalo");
        placeIt.getStyleClass().add("button-style");


        dieDrafted.getChildren().addAll(title, die, placeIt);


        Scene second = new Scene(dieDrafted);
        placeIt.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->  {
            this.supportStage.close();
            if(!this.commonBoardWindow.getManager().isCommandContained("Piazza"))
                this.commonBoardWindow.getManager().communicateMessage("errore.");
            else {
                this.commonBoardWindow.getManager().executeCommandIfPresent("Piazza");
                this.supportStage.close();
            }
        });

        GUIMain.dragWindow(dieDrafted);

        second.setFill(Color.TRANSPARENT);
        supportStage.setScene(second);
        supportStage.show();


    }


    public Map<Integer, Runnable> getToolBuilderMap() {
        return toolBuilderMap;
    }

    public void setToolBuilderMap(Map<Integer, Runnable> toolBuilderMap) {
        this.toolBuilderMap = toolBuilderMap;
    }
}

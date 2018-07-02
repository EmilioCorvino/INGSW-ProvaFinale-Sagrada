package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.view.gui.gamewindows.CommonBoardWindow;

import java.util.HashMap;
import java.util.Map;

public class ToolWindowManager {

    private GUICommunicationManager manager;

    private CommonBoardWindow commonBoardWindow;

    private Map<Integer, Runnable> toolMethods;

    private Map<Integer, Runnable> toolMoveValidator;

    private int slotId;

    private int currTool;

    private ToolWindowBuilder toolWindowBuilder;


    public ToolWindowManager(CommonBoardWindow commonBoardWindow) {
        this.commonBoardWindow = commonBoardWindow;
        toolMethods = new HashMap<>();
        toolMoveValidator = new HashMap<>();

        toolMethods.put(1, this::toolOneWindow);

        toolMoveValidator.put(1, this::validateToolOne);

        toolWindowBuilder = new ToolWindowBuilder(commonBoardWindow);

    }

    public void invokeToolCommand(int toolId) {
        this.currTool = toolId;
        this.toolMethods.get(toolId).run();
    }

    public void invokeMoveValidator(int toolId) {
        this.toolMoveValidator.get(toolId).run();
    }


    public void validateToolOne() {
        this.manager.executeCommandToolIfPresent(this.currTool);

    }

    public void buildWindow() {

        this.toolWindowBuilder.getToolBuilderMap().get(this.currTool).run();

    }

    /*
    public void showSupportWindowTool1() {
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

        minus.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            if(!this.commonBoardWindow.getData().isSourceFilled()) {
                title.setText("Devi scegliere un dado");
            } else {
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
            }
        });

        plus.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {

            if(!this.commonBoardWindow.getData().isSourceFilled()) {
                title.setText("Devi scegliere un dado");
            } else {

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
            }
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
    */

    private void toolOneWindow() {
        if(!this.commonBoardWindow.getDraftPoolGUI().isHandlerActive())
            this.commonBoardWindow.getDraftPoolGUI().cellsAsSourceWithCondition(this.commonBoardWindow.getData());

        this.commonBoardWindow.getData().getPersonalWp().cellMapAsDestinationHandler(this.commonBoardWindow.getData());
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public GUICommunicationManager getManager() {
        return manager;
    }

    public void setManager(GUICommunicationManager manager) {
        this.manager = manager;
    }
}

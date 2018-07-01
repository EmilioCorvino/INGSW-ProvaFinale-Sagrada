package it.polimi.ingsw.view.gui;


import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ToolWindowManager {

    private GUICommunicationManager manager;

    private Map<Integer, VBox> toolMap;

    private DraftPoolGUI draft;

    private PlayersData data;

    private Stage newWindow;

    public ToolWindowManager(GUICommunicationManager manager) {
        this.manager = manager;

        toolMap = new HashMap<>();

        toolMap.put(1, toolOneWindow());

    }

    public void displayToolWindow(int idTool) {

        Scene second = new Scene(toolMap.get(idTool));

        newWindow = new Stage();
        newWindow.setTitle("tool stage");
        newWindow.setScene(second);

        newWindow.initModality(Modality.WINDOW_MODAL);

        newWindow.initOwner(GUIMain.getStage());


        newWindow.setX(GUIMain.getStage().getX() + 200);


        newWindow.show();

    }

    private VBox toolOneWindow() {
        VBox mainCont = new VBox();
        /*
        draft.cellAsSource(data);

        mainCont.getStylesheets().add("style/backgrounds.css");
        mainCont.getStyleClass().add("background");
        mainCont.getStyleClass().add("VBox");
        mainCont.setMinHeight(500);
        mainCont.setMinWidth(600);
        mainCont.setSpacing(20);
        mainCont.setAlignment(Pos.CENTER);

        Label title = new Label("Scegli un valore");
        mainCont.getChildren().add(title);

        HBox secondCont = new HBox();
        secondCont.setSpacing(10);
        secondCont.getChildren().add(this.draft);

        VBox buttons = new VBox();
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        HBox valueButtons = new HBox();
        valueButtons.setSpacing(10);

        Button minus = new Button(" - ");
        Label value = new Label("0" );
        Button plus = new Button(" + ");
        valueButtons.getChildren().addAll(minus, value, plus);

        SetUpInformationUnit info = new SetUpInformationUnit();
        this.data.setSetUpInformationUnit(info);

        minus.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            if(!data.isSourceFilled()) {
                title.setText("Devi scegliere un dado");
            } else {
                value.setText(" " + this.draft.getValues().get(data.getSetUpInformationUnit().getSourceIndex()));
                int val = Integer.parseInt(value.getText());
                val--;
                value.setText(val + "");
                info.setExtraParam(1);
                info.setValue(val);
            }


        });

        plus.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            if(!data.isSourceFilled()) {
                title.setText("Devi scegliere un dado");
            } else {
                int val = Integer.parseInt(value.getText());
                val++;
                value.setText(val + "");
                info.setExtraParam(1);
                info.setValue(val);
            }
        });

        secondCont.getChildren().addAll(buttons);
        mainCont.getChildren().add(secondCont);

        Button ok = new Button("Ok");

        ok.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            if(!data.isSourceFilled() && Integer.parseInt(value.getText()) != 0) {
                title.setText("Devi scegliere un dado");
            } else {
                this.newWindow.close();
            }
        });
        buttons.getChildren().add(ok);
*/
        return mainCont;

    }

    public DraftPoolGUI getDraft() {
        return draft;
    }

    public void setDraft(DraftPoolGUI draft) {
        this.draft = draft;
    }

    public PlayersData getData() {
        return data;
    }

    public void setData(PlayersData data) {
        this.data = data;
    }
}

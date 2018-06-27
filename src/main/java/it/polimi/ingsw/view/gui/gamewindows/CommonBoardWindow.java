package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.view.gui.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Random;

public class CommonBoardWindow extends ParentWindow {

    private VBox mainContainer;

    private HBox header;

    private HBox secondContainer;

    private HBox gameData;

    private VBox publToolDraftCont;

    private VBox box = new VBox();

    private DraftPoolGUI draftPoolGUI;
    private VBox secondSecCont;

    public DraftPoolGUI getDraftPoolGUI() {
        return draftPoolGUI;
    }

    public void setDraftPoolGUI(DraftPoolGUI draftPoolGUI) {
        this.draftPoolGUI = draftPoolGUI;
    }

    private GUICommunicationManager manager;

    public PlayersData getData() {
        return data;
    }

    public void setData(PlayersData data) {
        this.data = data;
    }

    private PlayersData data;

    public CommonBoardWindow(GUICommunicationManager manager) {
        draftPoolGUI = new DraftPoolGUI();
        secondSecCont = new VBox();
        this.manager = manager;
        mainContainer = new VBox();
        header = new HBox();
        publToolDraftCont = new VBox();

        secondContainer = new HBox();
        gameData = new HBox();

        this.setMinHeight(760);
        this.setMinWidth(1400);
        this.setMaxHeight(760);
        this.setMaxWidth(1400);

        this.getStylesheets().add("style/backgrounds.css");
        this.getStyleClass().add("background");
        this.getStyleClass().add("VBox");

        GUIMain.dragWindow(this);
        initializeHeader();
        formatWindow();

        this.getChildren().add(mainContainer);
        this.mainContainer.getChildren().addAll(header, secondContainer, gameData);
    }


    @Override
    public void initializeHeader() {
        Label titleHeader = new Label("Sagrada - fase di gioco");
        titleHeader.setPadding(new Insets(3, 0, 0, 0));
        HBox buttonBox = new HBox();
        Button exit = new Button("X");
        Button help = new Button(("?"));
        Button minimize = new Button("_");
        buttonBox.getChildren().addAll(minimize, help, exit);
        buttonBox.getChildren().get(0).getStyleClass().add("button-style");
        buttonBox.getChildren().get(1).getStyleClass().add("button-style");
        buttonBox.getChildren().get(2).getStyleClass().add("button-style");
        buttonBox.setSpacing(7);
        this.header.getChildren().addAll(titleHeader, buttonBox);
        this.header.getChildren().get(0).getStyleClass().add("title");


        this.header.setSpacing(1070);

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.exit(0));
        help.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.manager.communicateMessage("Seleziona una sola delle 4 mappe presentate"));
        minimize.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> GUIMain.getStage().setIconified(true));
    }

    public void formatWindow() {
        this.mainContainer.setPadding(new Insets(20, 25, 20,25));
        this.mainContainer.setSpacing(20);
    }

    public void formatSecondContainer() {
        formatPlayersDataContainer();
        this.secondContainer.setSpacing(60);
        this.publToolDraftCont.setSpacing(35);
        this.secondContainer.getChildren().add(this.secondSecCont);
        formatDraftCommands();
        this.secondSecCont.getChildren().add(this.draftPoolGUI);
        //secondSecCont.setStyle("-fx-background-color: white");
        secondSecCont.setMinWidth(200);
        secondSecCont.setMaxWidth(200);
        this.secondContainer.getChildren().add(publToolDraftCont);
    }

    public void formatDraftCommands() {
        VBox commandsDraft = new VBox();
        //secondSecCont.setStyle("-fx-background-color: purple");

        Button draft = new Button("lancia");
        Button pass = new Button("passa");

        draft.getStyleClass().add("button-style");
        pass.getStyleClass().add("button-style");

        commandsDraft.getChildren().addAll(draft, pass);
        this.secondSecCont.getChildren().add(commandsDraft);


    }

    public void formatPlayersDataContainer() {

        StackPane base = new StackPane();
        VBox cardFavorCont = new VBox();
        GridPane map = this.data.getPersonalWp().getGlassWindow();

        Image card = new Image("/cards/privateImages/private_" + this.data.getIdPrivateCard() + ".png");
        ImageView view = new ImageView(card);
        cardFavorCont.getChildren().add(view);

        Random random = new Random();
        int val = random.nextInt(4) + 1;
        Image mapArc = new Image("/cards/boardsmap/map_" + val + ".png");
        ImageView view1 = new ImageView(mapArc);

        view1.setFitHeight(650);
        view1.setPreserveRatio(true);
        view.setFitHeight(240);
        view.setPreserveRatio(true);
        cardFavorCont.setPadding(new Insets(0, 0, 0, 25));

        base.setMinWidth(200);
        base.setMaxWidth(300);
        box.setMinWidth(280);
        box.setMaxWidth(280);
        box.setPadding(new Insets(80, 0, 0, 28));
        box.setSpacing(53);

        HBox favorTok = new HBox();
        Label titleFav = new Label("Favor tokens: ");
        favorTok.setPadding(new Insets(0, 0, 0, 45));
        titleFav.getStyleClass().add("text-label-bold");
        Label numFav = new Label("");
        numFav.getStyleClass().add("text-label-bold");

        favorTok.getChildren().addAll(titleFav, numFav);
        box.getChildren().addAll(cardFavorCont, favorTok, map);
        base.getChildren().addAll(view1, box);

        this.secondContainer.getChildren().add(base);
    }

    public void setFavorTokens() {
        ((Label)((HBox)box.getChildren().get(1)).getChildren().get(1)).setText(this.data.getNumFavTok() + "");
    }

    public void setPublicImages(int[] idPublObj) {
        HBox publObjCont = new HBox();
        publObjCont.setSpacing(25);
        this.publToolDraftCont.getChildren().add(publObjCont);
        //publObjCont.setMinHeight(240);

        for(int i=0; i<idPublObj.length; i++) {
            Image publCard = new Image("/cards/publicImages/publObj" + idPublObj[i] + ".png");
            ImageView view = new ImageView(publCard);
            view.setFitHeight(240);
            view.setPreserveRatio(true);
            publObjCont.getChildren().add(view);
        }
    }

    public void setToolImages(int[] idTools) {
        HBox toolCont = new HBox();
        toolCont.setSpacing(25);
        this.publToolDraftCont.getChildren().add(toolCont);

        for(int i=0; i<idTools.length; i++) {
            Image toolCard = new Image("/cards/toolImages/tool" + idTools[i] + ".png");
            ImageView view = new ImageView(toolCard);
            view.setFitHeight(240);
            view.setPreserveRatio(true);
            toolCont.getChildren().add(view);
        }


    }

    public VBox getPublToolDraftCont() {
        return publToolDraftCont;
    }

    public void setPublToolDraftCont(VBox publToolDraftCont) {
        this.publToolDraftCont = publToolDraftCont;
    }

    @Override
    public void addHandlers() {

    }
}

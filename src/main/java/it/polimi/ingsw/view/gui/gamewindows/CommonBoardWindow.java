package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.view.gui.GUICommunicationManager;
import it.polimi.ingsw.view.gui.GUIMain;
import it.polimi.ingsw.view.gui.ParentWindow;
import it.polimi.ingsw.view.gui.PlayersData;
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

    private GUICommunicationManager manager;

    public PlayersData getData() {
        return data;
    }

    public void setData(PlayersData data) {
        this.data = data;
    }

    private PlayersData data;

    public CommonBoardWindow(GUICommunicationManager manager) {
        this.manager = manager;
        mainContainer = new VBox();
        header = new HBox();

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

    }

    public void formatPlayersDataContainer() {

        StackPane base = new StackPane();
        VBox cardFavorCont = new VBox();
        GridPane map = this.data.getPersonalWp().getGlassWindow();
        VBox box = new VBox();

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
        cardFavorCont.setPadding(new Insets(0, 0, 0, 10));

        base.setMinWidth(200);
        base.setMaxWidth(300);
        box.setMinWidth(200);
        box.setMaxWidth(200);
        box.setPadding(new Insets(80, 0, 0, 0));

        //base.setStyle("-fx-background-color: rgba(0, 153, 204, 0.3)");
        //box.setStyle("-fx-background-color: yellow");
        box.setSpacing(120);

        box.getChildren().addAll(cardFavorCont, map);
        base.getChildren().addAll(view1, box);

        this.secondContainer.getChildren().add(base);
    }

    public void updateFavorTokens() {

    }

    @Override
    public void addHandlers() {

    }
}

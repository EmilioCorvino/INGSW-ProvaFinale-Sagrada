package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class ChooseWpGUI extends VBox {

    private double xOffset = 0;
    private double yOffset = 0;


    private VBox mainContainer;


    private HBox header;


    private VBox privateCardContainer;

    /**
     * This containes the private objective card and the map of window pattern card among which to choose
     * with their relative useful information.
     */
    private HBox secondContainer;


    /**
     * This is the specific container for the window pattern card among which to choose and their relative
     * useful information
     */
    private VBox mapsContainer;

    /**
     * This is the specific container for each map.
     */
    private GridPane gridMaps;

    private List<WpGui> maps;

    private WpGui chosenWp;

    public WpGui getChosenWp() {
        return chosenWp;
    }

    public void setChosenWp(WpGui chosenWp) {
        this.chosenWp = chosenWp;
    }

    public ChooseWpGUI() {
        chosenWp = new WpGui();
        mainContainer = new VBox();
        secondContainer = new HBox();

        maps = new ArrayList<>();


        this.setPrefSize(1200, 650);
        // this.mainContainer.setPrefSize(700, 500);



        header = new HBox();

        initializeHeader();


        privateCardContainer = new VBox();

        initializeCardContainer();

        mapsContainer = new VBox();

        initializeMapContainer();

        secondContainer.getChildren().addAll(privateCardContainer, mapsContainer);

        this.mainContainer.getChildren().addAll(header, secondContainer);

        this.getChildren().add(mainContainer);

        formatWindow();



        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this:: pressedWindow);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this :: draggedWindow);
    }

    protected void pressedWindow(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    protected void draggedWindow(MouseEvent event) {
        GUIMain.getStage().setX(event.getScreenX() - xOffset);
        GUIMain.getStage().setY(event.getScreenY() - yOffset);
    }

    public void initializeMapContainer() {
        Label title = new Label("Scegli una mappa:");

        gridMaps = new GridPane();

        for(int i=0; i<2; i++) {
            RowConstraints rc = new RowConstraints(260);
            this.gridMaps.getRowConstraints().add(rc);
            for(int j=0; j<2; j++) {
                ColumnConstraints cc = new ColumnConstraints(280);
                this.gridMaps.getColumnConstraints().add(cc);

            }
        }

        this.mapsContainer.getChildren().addAll(title, gridMaps);
    }

    public void initializeCardContainer() {
        Label titleimg = new Label("La tua carta obiettivo privato:");
        this.privateCardContainer.getChildren().add(titleimg);
    }

    public void initializeHeader() {
        Label titleHeader = new Label("Sagrada - set up del gioco");
        titleHeader.setPadding(new Insets(3, 0, 0, 0));
        Button exit = new Button("X");
        this.header.getChildren().addAll(titleHeader, exit);

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.exit(0));
    }

    public void assignPrivateObjectiveCard(int id) {
        Image card = new Image("/cards/privateImages/private_" + id + ".png");
        ImageView view = new ImageView(card);
        this.privateCardContainer.getChildren().add(view);

        view.setFitHeight(340);
        view.setPreserveRatio(true);
    }

    public void formatWindow() {

        this.getStylesheets().add("style/backgrounds.css");
        this.getStyleClass().add("background");

        this.header.setSpacing(900);
        this.header.getChildren().get(0).getStyleClass().add("title");
        this.header.getChildren().get(1).getStyleClass().add("button-style");

        this.mainContainer.setPadding(new Insets(20, 25, 20, 25));
        this.mainContainer.getStyleClass().add("VBox");
        this.mainContainer.setMinWidth(1200);
        this.mainContainer.setMinHeight(700);
        this.mainContainer.setSpacing(20);
        this.mainContainer.getChildren().get(0).getStyleClass().add("text-label");

        this.secondContainer.setSpacing(100);
        this.secondContainer.setPadding(new Insets(10, 0, 20, 0));


        this.privateCardContainer.setSpacing(70);
        this.privateCardContainer.setAlignment(Pos.CENTER);
        this.privateCardContainer.getChildren().get(0).getStyleClass().add("text-label");
        this.privateCardContainer.getStyleClass().add("panel-glass");
        this.privateCardContainer.setPadding(new Insets(10, 25, 25, 25 ));
        this.privateCardContainer.setMinHeight(500);
        this.privateCardContainer.setMinWidth(300);

        this.mapsContainer.setSpacing(30);
        this.mapsContainer.setAlignment(Pos.CENTER);
        this.mapsContainer.getChildren().get(0).getStyleClass().add("text-label");
        this.mapsContainer.getStyleClass().add("panel-glass");
        this.mapsContainer.setPadding(new Insets(10, 25, 25, 25 ));
        this.mapsContainer.setMinHeight(250);
        this.mapsContainer.setMinWidth(750);

        this.gridMaps.setPadding(new Insets(0, 0, 0 , 600));
    }

    public void wpToShow(List<SimplifiedWindowPatternCard> listToFlow) {
        listToFlow.forEach(map -> {
            WpGui wp = new WpGui();
            wp.constructMap(map);
            maps.add(wp);
        });

        formatMapsContainer();
    }


    public void formatMapsContainer() {

        VBox cell0 = new VBox();
        cell0.setSpacing(20);
        cell0.setPadding(new Insets(5, 30, 5, 30));
        cell0.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> handleOnMapSelection(cell0));
        cell0.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cell0.setStyle("-fx-background-color: transparent"));
        cell0.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleChosenMap(cell0));

        WpGui wp0 = this.maps.get(0);

        cell0.getChildren().addAll(wp0.getIdMap(), wp0.getGlassWindow(), wp0.getDifficulty());
        this.gridMaps.add(cell0, 0, 0);


        VBox cell1 = new VBox();
        cell1.setPadding(new Insets(5, 30, 5, 30));
        WpGui wp1 = this.maps.get(1);
        cell1.setSpacing(20);

        cell1.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> handleOnMapSelection(cell1));
        cell1.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cell1.setStyle("-fx-background-color: transparent"));
        cell1.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleChosenMap(cell1));

        cell1.getChildren().addAll(wp1.getIdMap(), wp1.getGlassWindow(), wp1.getDifficulty());
        this.gridMaps.add(cell1, 1, 0);

        VBox cell2 = new VBox();
        WpGui wp2 = this.maps.get(2);
        cell2.setSpacing(20);

        cell2.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> handleOnMapSelection(cell2));
        cell2.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cell2.setStyle("-fx-background-color: transparent"));
        cell2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleChosenMap(cell2));

        cell2.getChildren().addAll(wp2.getIdMap(), wp2.getGlassWindow(), wp2.getDifficulty());
        cell2.setPadding(new Insets(5, 30, 5, 30));
        this.gridMaps.add(cell2, 0, 1);

        VBox cell3 = new VBox();
        WpGui wp3 = this.maps.get(3);
        cell3.setSpacing(20);

        cell3.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> handleOnMapSelection(cell3));
        cell3.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cell3.setStyle("-fx-background-color: transparent"));
        cell3.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleChosenMap(cell3));

        cell3.getChildren().addAll(wp3.getIdMap(), wp3.getGlassWindow(), wp3.getDifficulty());
        cell3.setPadding(new Insets(5, 30, 5, 30));
        this.gridMaps.add(cell3, 1, 1);
    }

    public void handleOnMapSelection(VBox chosenMap) {
        chosenMap.setMinWidth(240);
        chosenMap.setMinHeight(240);
        chosenMap.setStyle("-fx-background-color: rgba(102, 217, 255, 0.3)");
    }

    public void handleChosenMap(VBox map) {

        WpGui chosen = new WpGui();
        chosen.setIdMap((Label)map.getChildren().get(0));
        chosen.setGlassWindow((GridPane)map.getChildren().get(1));
        chosen.setDifficulty((Label)map.getChildren().get(2));
        this.setChosenWp(chosen);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("hai scelto la mappa! Attendi che anche gli altri giocatori lo facciano.");
        alert.showAndWait();
        alert.setOnCloseRequest(event -> alert.hide());

    }



    public VBox getMainContainer() {
        return mainContainer;
    }

    public void setMainContainer(VBox mainContainer) {
        this.mainContainer = mainContainer;
    }

    public HBox getHeader() {
        return header;
    }

    public void setHeader(HBox header) {
        this.header = header;
    }



    public VBox getPrivateCardContainer() {
        return privateCardContainer;
    }

    public void setPrivateCardContainer(VBox privateCardContainer) {
        this.privateCardContainer = privateCardContainer;
    }


    public VBox getMapsContainer() {
        return mapsContainer;
    }

    public void setMapsContainer(VBox mapsContainer) {
        this.mapsContainer = mapsContainer;
    }

    public GridPane getGridMaps() {
        return gridMaps;
    }

    public void setGridMaps(GridPane gridMaps) {
        this.gridMaps = gridMaps;
    }

    public List<WpGui> getMaps() {
        return maps;
    }

    public void setMaps(List<WpGui> maps) {
        this.maps = maps;
    }
}

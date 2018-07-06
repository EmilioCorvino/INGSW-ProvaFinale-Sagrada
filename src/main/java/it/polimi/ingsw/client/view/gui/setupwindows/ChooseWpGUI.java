package it.polimi.ingsw.client.view.gui.setupwindows;

import it.polimi.ingsw.client.view.gui.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the window that shows the private objective card assigned to the player and
 * the representation of the four maps among which to choose.
 */
public class ChooseWpGUI extends ParentWindow {

    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * This is the main container for all the elements of this window.
     */
    private VBox mainContainer;

    /**
     * This is the header of this window.
     */
    private HBox header;

    /**
     * This is the container for the private objective card assigned to the player by the server.
     */
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

    /**
     * This is the list of maps to be represented.
     */
    private List<WpGui> maps;

    /**
     * This attribute represents the data of the player.
     */
    private PlayersData playersData;

    /**
     * This is used to prevent that the user choses another map after have chosen another one.
     */
    private boolean isAlreadyChosen;

    /**
     * This attribute is the main communicator of the GUI toward the player and the server.
     */
    private final GUICommunicationManager communicator;

    public ChooseWpGUI(GUICommunicationManager manager) {
        this.communicator = manager;

        mainContainer = new VBox();
        secondContainer = new HBox();
        maps = new ArrayList<>();

        this.setMinHeight(720);
        this.setMinWidth(1200);
        this.setMaxHeight(720);
        this.setMaxWidth(1200);

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

    private void pressedWindow(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void draggedWindow(MouseEvent event) {
        GUIMain.getStage().setX(event.getScreenX() - xOffset);
        GUIMain.getStage().setY(event.getScreenY() - yOffset);
    }

    /**
     * This method constructs the container for the four maps among which to choose.
     */
    public void initializeMapContainer() {
        Label title = new Label("");
        gridMaps = new GridPane();

        for(int i=0; i<2; i++) {
            RowConstraints rc = new RowConstraints(270);
            this.gridMaps.getRowConstraints().add(rc);
            for(int j=0; j<2; j++) {
                ColumnConstraints cc = new ColumnConstraints(290);
                this.gridMaps.getColumnConstraints().add(cc);
            }
        }
        this.mapsContainer.getChildren().addAll(title, gridMaps);
    }

    /**
     * This method constructs the container for the private objective card assigned by the server.
     */
    public void initializeCardContainer() {
        Label titleimg = new Label("La tua carta obiettivo privato:");
        this.privateCardContainer.getChildren().add(titleimg);
    }

    /**
     * This method constructs the header of the window, with proper title and commands.
     */
    public void initializeHeader() {
        Label titleHeader = new Label("Sagrada - set up del gioco");
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

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.exit(0));
        help.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.communicator.communicateMessage("Seleziona una sola delle 4 mappe presentate"));
        minimize.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> GUIMain.getStage().setIconified(true));
    }

    /**
     * This method sets the private objective card assigned by the server.
     * @param id the id of the card assigned.
     */
    public void assignPrivateObjectiveCard(int id) {
        Image card = new Image("/cards/privateImages/private_" + id + ".png");
        ImageView view = new ImageView(card);
        this.privateCardContainer.getChildren().add(view);

        view.setFitHeight(340);
        view.setPreserveRatio(true);
    }

    /**
     * This method styles the window for the private objective card and the four maps among which to choose.
     */
    public void formatWindow() {
        this.getStylesheets().add("style/backgrounds.css");
        this.getStyleClass().add("background");

        this.header.setSpacing(860);
        this.header.getChildren().get(0).getStyleClass().add("title");

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

    /**
     * This method puts the four maps among which to choose in the container to be shown.
     */
    public void formatMapsContainer() {

        VBox cell0 = new VBox();
        cell0.setSpacing(15);
        cell0.setPadding(new Insets(5, 30, 5, 30));
        WpGui wp0 = this.maps.get(0);
        cell0.getChildren().addAll(wp0.getIdMap(), wp0.getGlassWindow(), wp0.getDifficulty());
        this.gridMaps.add(cell0, 0, 0);


        VBox cell1 = new VBox();
        cell1.setPadding(new Insets(5, 30, 5, 30));
        WpGui wp1 = this.maps.get(1);
        cell1.setSpacing(15);
        cell1.getChildren().addAll(wp1.getIdMap(), wp1.getGlassWindow(), wp1.getDifficulty());
        this.gridMaps.add(cell1, 1, 0);

        VBox cell2 = new VBox();
        WpGui wp2 = this.maps.get(2);
        cell2.setSpacing(15);
        cell2.getChildren().addAll(wp2.getIdMap(), wp2.getGlassWindow(), wp2.getDifficulty());
        cell2.setPadding(new Insets(5, 30, 5, 30));
        this.gridMaps.add(cell2, 0, 1);

        VBox cell3 = new VBox();
        WpGui wp3 = this.maps.get(3);
        cell3.setSpacing(15);
        cell3.getChildren().addAll(wp3.getIdMap(), wp3.getGlassWindow(), wp3.getDifficulty());
        cell3.setPadding(new Insets(5, 30, 5, 30));
        this.gridMaps.add(cell3, 1, 1);
    }

    /**
     * This method handles the hover action on a specific map.
     * @param chosenMap the hovered map.
     */
    public void handleOnMapSelection(VBox chosenMap) {
        chosenMap.setMinWidth(240);
        chosenMap.setMinHeight(240);
        chosenMap.setStyle("-fx-background-color: rgba(102, 217, 255, 0.3)");
    }

    /**
     * This method handles the selection of a map and keeps in mwemory the choice.
     * @param map the chosen map.
     */
    public void handleChosenMap(VBox map) {
        if(this.communicator.isCommandContained("Vetrata") && !this.isAlreadyChosen) {
            WpGui chosen = new WpGui();
            chosen.setIdMap((Label)map.getChildren().get(0));
            chosen.setGlassWindow((GridPane)map.getChildren().get(1));
            chosen.setDifficulty((Label)map.getChildren().get(2));
            map.addEventHandler(MouseEvent.MOUSE_EXITED, e -> map.setStyle("-fx-background-color: rgba(102, 217, 255, 0.3)"));
            this.playersData.setPersonalWp(chosen);
            this.isAlreadyChosen = true;
            this.communicator.executeCommandIfPresent("Vetrata");
        } else {
            this.communicator.communicateMessage("Hai già scelto una mappa.");
        }
    }

    /**
     * This method adds the handlers to the maps.
     */
    public void addHandlers() {
        for(int i=0; i<2; i++)
            for(int j=0; j<2; j++) {
                VBox cell = (VBox)this.gridMaps.getChildren().get(2*i + j);
                cell.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> handleOnMapSelection(cell));
                cell.addEventHandler(MouseEvent.MOUSE_EXITED, e -> cell.setStyle("-fx-background-color: transparent"));
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> handleChosenMap(cell));
            }
        }

    @Override
    public void showMessage(String mex) {

    }

    public List<WpGui> getMaps() {
        return maps;
    }

    public void setMaps(List<WpGui> maps) {
        this.maps = maps;
    }

    public void setPlayersData(PlayersData playersData) {
        this.playersData = playersData;
    }
}
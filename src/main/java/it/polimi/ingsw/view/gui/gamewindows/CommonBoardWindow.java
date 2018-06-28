package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.view.gui.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    /**
     * This is the main container of the common board gui window.
     */
    private VBox mainContainer;

    /**
     * This is the attribute that represents the header with title and buttons of this window.
     */
    private HBox header;

    /**
     * This is the container of the window containing the player's map, the drsft, the public and the tool cards,
     * the round track and the player's private objective card.
     */
    private HBox secondContainer;

    /**
     *
     */
    private HBox gameData;

    /**
     * This id the vbox contanining the hboxes for the public and tool cards.
     */
    private VBox publToolDraftCont;

    /**
     * This contains the map chosen by the user in the set up phase o the game.
     */
    private VBox box = new VBox();

    /**
     * This attribute represents the draft pool of the game.
     */
    private DraftPoolGUI draftPoolGUI;

    /**
     * This is the container for the private objective card of the player, the number o favor tokens of the player
     * and the draft pool of the game.
     */
    private VBox secondSecCont;

    /**
     * This is the round track of the game, updated at each round.
     */
    private RoundTrackGUI roundTrack;


    /**
     * This is the container for the commands during a turn of the player, such as the "pass turn" command.
     */
    private VBox commandsDraft = new VBox();

    /**
     * This is the communication manager used to check the commands associated to the buttons and the map on the
     * common board window.
     */
    private GUICommunicationManager manager;

    /**
     * This attribute repesents the container for the player's data, such us the favor tokens
     * and the personal window pattern card.
     */
    private PlayersData data;

    public CommonBoardWindow(GUICommunicationManager manager) {
        roundTrack = new RoundTrackGUI();
        draftPoolGUI = new DraftPoolGUI();
        secondSecCont = new VBox();
        this.secondSecCont.setSpacing(45);
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


    /**
     * This method constructs the header of the window with appropriate button and setting the proper spaces.
     */
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

    /**
     * This method formats the window in general.
     */
    public void formatWindow() {
        this.mainContainer.setPadding(new Insets(20, 25, 20,25));
        this.mainContainer.setSpacing(20);
    }

    /**
     * This method formats the container of the window where the maps and other commands for the
     * game are contained.
     */
    public void formatSecondContainer() {
        formatPlayersDataContainer();
        this.secondContainer.setSpacing(50);
        this.publToolDraftCont.setSpacing(30);
        this.secondContainer.getChildren().add(this.secondSecCont);

        VBox imgViewCont = new VBox();
        Image card = new Image("/cards/privateImages/private_" + this.data.getIdPrivateCard() + ".png");
        ImageView view = new ImageView(card);
        imgViewCont.getStyleClass().add("map-background");
        imgViewCont.setPadding(new Insets(20));
        view.setFitHeight(240);
        view.setPreserveRatio(true);
        imgViewCont.setMinHeight(260);
        imgViewCont.getChildren().add(view);

        this.secondSecCont.getChildren().add(imgViewCont);

        formatDraftCommands();
        this.secondSecCont.getChildren().add(this.draftPoolGUI);
        secondSecCont.setMinWidth(200);
        secondSecCont.setMaxWidth(200);
        this.secondContainer.getChildren().add(publToolDraftCont);
    }

    /**
     * This method formats the proper commands useful during the game, such as the "pass turn" command.
     */
    public void formatDraftCommands() {

        commandsDraft.setSpacing(20);
        commandsDraft.setAlignment(Pos.CENTER);

        HBox favorTok = new HBox();
        Label titleFav = new Label("Segnalini favore: ");
        favorTok.setPadding(new Insets(0, 0, 0, 35));
        titleFav.getStyleClass().add("text-label-bold");
        Label numFav = new Label("");
        numFav.getStyleClass().add("text-label-bold");
        favorTok.getChildren().addAll(titleFav, numFav);

        Button pass = new Button("Passa il turno");
        pass.getStyleClass().add("button-style");
        pass.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> passTurnHandler());

        commandsDraft.getChildren().addAll(favorTok, pass);
        this.secondSecCont.getChildren().add(commandsDraft);
    }

    /**
     * This method formats all that is relative to the player, such as its personal map and
     * its personal board card.
     */
    public void formatPlayersDataContainer() {

        StackPane base = new StackPane();
        VBox cardFavorCont = new VBox();
        GridPane map = this.data.getPersonalWp().getGlassWindow();

        ImageView view = new ImageView();
        cardFavorCont.getChildren().add(view);

        Random random = new Random();
        int val = random.nextInt(4) + 1;
        Image mapArc = new Image("/cards/boardsmap/map_" + val + ".png");
        ImageView view1 = new ImageView(mapArc);

        view1.setFitHeight(650);
        view1.setPreserveRatio(true);
        view.setFitHeight(230);
        view.setPreserveRatio(true);
        cardFavorCont.setPadding(new Insets(0, 0, 0, 25));

        base.setMinWidth(200);
        base.setMaxWidth(300);
        box.setMinWidth(280);
        box.setMaxWidth(280);
        box.setPadding(new Insets(80, 0, 0, 28));
        box.setSpacing(62);

        HBox favorTok = new HBox();
        Label titleFav = new Label(" ");
        favorTok.setPadding(new Insets(0, 0, 0, 45));
        titleFav.getStyleClass().add("text-label-bold");
        Label numFav = new Label("");
        numFav.getStyleClass().add("text-label-bold");

        favorTok.getChildren().addAll(titleFav, numFav);
        box.getChildren().addAll(cardFavorCont, favorTok, map);
        base.getChildren().addAll(view1, box);

        this.secondContainer.getChildren().add(base);
    }

    /**
     * This method is responsible for updating the number of favor tokens each time a
     * tool card is used.
     */
    public void setFavorTokens() {
        ((Label)((HBox)commandsDraft.getChildren().get(0)).getChildren().get(1)).setText(this.data.getNumFavTok() + "");
    }

    /**
     * This method sets the public objective cards images sent by the server.
     * @param idPublObj the id of the cards to set.
     */
    public void setPublicImages(int[] idPublObj) {
        HBox publObjCont = new HBox();
        publObjCont.getStyleClass().add("map-background");
        publObjCont.setPadding(new Insets(20));
        publObjCont.setSpacing(25);
        this.publToolDraftCont.getChildren().add(publObjCont);

        for(int i=0; i<idPublObj.length; i++) {
            Image publCard = new Image("/cards/publicImages/publObj" + idPublObj[i] + ".png");
            ImageView view = new ImageView(publCard);
            view.setFitHeight(240);
            view.setPreserveRatio(true);
            publObjCont.getChildren().add(view);
        }
    }

    /**
     * This method sets the tool card images sent by the server.
     * @param idTools the id of the tool cards to set.
     */
    public void setToolImages(int[] idTools) {
        HBox toolCont = new HBox();
        toolCont.getStyleClass().add("map-background");
        toolCont.setPadding(new Insets(20));
        toolCont.setSpacing(25);
        this.publToolDraftCont.getChildren().add(toolCont);

        for(int i=0; i<idTools.length; i++) { //TODO: perché non un for each?
            Image toolCard = new Image("/cards/toolImages/tool" + idTools[i] + ".png");
            ImageView view = new ImageView(toolCard);
            view.setFitHeight(240);
            view.setPreserveRatio(true);
            toolCont.getChildren().add(view);
        }
    }

    public void setPanelForInformation() {
        HBox messageBox = new HBox();
        Label label = new Label("");
        messageBox.getChildren().add(label);
        this.publToolDraftCont.getChildren().add(messageBox);
    }

    public void showMessage(String message) {
        ((Label)((HBox)this.publToolDraftCont.getChildren().get(2)).getChildren().get(0)).setText(message);
    }

    public void setRoundTrack() {
        this.secondContainer.getChildren().add(this.roundTrack);
    }

    public VBox getPublToolDraftCont() {
        return publToolDraftCont;
    }

    public void setPublToolDraftCont(VBox publToolDraftCont) {
        this.publToolDraftCont = publToolDraftCont;
    }

    public void setDraftPoolGUI(DraftPoolGUI draftPoolGUI) {
        this.draftPoolGUI = draftPoolGUI;
    }

    public PlayersData getData() {
        return data;
    }

    public void setData(PlayersData data) {
        this.data = data;
    }

    public DraftPoolGUI getDraftPoolGUI() {
        return draftPoolGUI;
    }

    @Override
    public void addHandlers() {

    }

    /**
     * This method manages the pass turn command.
     */
    private void passTurnHandler() {
        if(this.manager.isCommandContained(Commands.END_TURN)) {
            this.manager.executeCommandIfPresent(Commands.END_TURN);
        } else {
            this.manager.communicateMessage("Comando non supportato poichè non è il tuo turno.");
        }
    }
}
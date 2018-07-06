package it.polimi.ingsw.view.gui.gamewindows;

import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.view.gui.*;
import it.polimi.ingsw.view.gui.gamewindows.toolcardsGUImanagers.ToolCardGUI;
import it.polimi.ingsw.view.gui.gamewindows.toolcardsGUImanagers.ToolWindowBuilder;
import it.polimi.ingsw.view.gui.gamewindows.toolcardsGUImanagers.ToolWindowManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.Random;

/**
 * This class manages the common board window in the GUI.
 */
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
     * This is the vbox contanining the hboxes for the public and tool cards.
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

    /**
     * This is to confirm the default placement action.
     */
    private Button ok;

    /**
     * This manages the activation of the tool cards in the gui.
     */
    private ToolWindowManager toolWindowManager;

    /**
     * This manages the construction of the support windows for some tool cards.
     */
    private ToolWindowBuilder toolWindowBuilder;



    public CommonBoardWindow(GUICommunicationManager manager) {


        toolWindowBuilder = new ToolWindowBuilder(this);
        supportStageBuilder();

        roundTrack = new RoundTrackGUI();
        draftPoolGUI = new DraftPoolGUI();
        secondSecCont = new VBox();
        this.secondSecCont.setSpacing(25);
        this.manager = manager;
        mainContainer = new VBox();
        header = new HBox();
        publToolDraftCont = new VBox();

        secondContainer = new HBox();

        ok = new Button("Ok");
        ok.setVisible(false);

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
        this.mainContainer.getChildren().addAll(header, secondContainer);

        ok.getStyleClass().add("button-style");
        ok.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> validateMoveHandler());

        toolWindowManager = new ToolWindowManager(this);
        this.draftPoolGUI.setToolManager(this.toolWindowManager);
        toolWindowManager.setManager(this.manager);

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

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(this.manager.isCommandContained("Logout"))
                this.manager.executeCommandIfPresent("Logout");
            else
                this.manager.communicateMessage("Non disponibile");
        });
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
        EnlargementHandler.addToAnimation(view);
    }


    /**
     * This method formats the proper commands useful during the game, such as the "pass turn" command.
     */
    private void formatDraftCommands() {

        commandsDraft.setSpacing(10);
        commandsDraft.setAlignment(Pos.CENTER);

        HBox favorTok = new HBox();
        Label titleFav = new Label("Segnalini favore: ");
        favorTok.setPadding(new Insets(0, 0, 0, 35));
        titleFav.getStyleClass().add("text-label-bold");
        //the number of favor tokens updated by the server.
        Label numFav = new Label("");
        numFav.getStyleClass().add("text-label-bold");
        favorTok.getChildren().addAll(titleFav, numFav);

        Button pass = new Button("Passa il turno");
        pass.getStyleClass().add("button-style");
        pass.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> passTurnHandler());

        Button placement = new Button("Piazzamento base");
        placement.getStyleClass().add("button-style");
        placement.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> diePlacementHandler());

        commandsDraft.getChildren().addAll(favorTok, placement, pass, ok);
        this.secondSecCont.getChildren().add(commandsDraft);
    }

    public void updateFavorTokens(int nFavTok) {
        HBox box = (HBox)this.commandsDraft.getChildren().get(0);
        Label tok = (Label)box.getChildren().get(1);
        tok.setText(nFavTok + "");
    }

    private Button otherMaps;

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
        box.setPadding(new Insets(50, 0, 0, 28));
        box.setSpacing(62);

        VBox favorTok = new VBox();
        favorTok.setSpacing(5);
        Label titleFav = new Label(this.data.getUsername());
        favorTok.setPadding(new Insets(0, 0, 0, 45));
        titleFav.getStyleClass().add("text-label-bold");
        otherMaps = new Button("Mappe giocatori");
        otherMaps.getStyleClass().add("button-style");

        favorTok.getChildren().addAll(titleFav, otherMaps);
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
            EnlargementHandler.addToAnimation(view);
        }
    }

    private HBox toolCont;

    /**
     * This method sets the tool card images sent by the server.
     * @param idTools the id of the tool cards to set.
     */
    public void setToolImages(int[] idTools) {
        toolCont = new HBox();
        toolCont.getStyleClass().add("map-background");
        toolCont.setPadding(new Insets(20));
        toolCont.setSpacing(25);
        toolCont.setMinHeight(260);
        this.publToolDraftCont.getChildren().add(toolCont);

        for(int i=0; i<idTools.length; i++) {
            ToolCardGUI toolCard = new ToolCardGUI("/cards/toolImages/tool" + idTools[i] + ".png");
            toolCard.setIdTool(idTools[i] - 300 + 1);
            toolCard.setToolSlot(i);
            toolCont.getChildren().add(toolCard);
            toolCard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> chooseToolCardHandler(toolCard));

            HBox box = (HBox)toolCard.getChildren().get(1);
            VBox vBox = (VBox)box.getChildren().get(1);
            Button button = (Button)vBox.getChildren().get(0);
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                button.setVisible(false);
                this.toolWindowManager.invokeMoveValidator(toolCard.getIdTool());
            } );
        }
    }


    public void chooseToolCardHandler(ToolCardGUI tool) {
        HBox box = (HBox)tool.getChildren().get(1);
        VBox vBox = (VBox)box.getChildren().get(1); /* ocio */
        Button button = (Button)vBox.getChildren().get(0);
        if(this.manager.isCommandContained("Strumento " + (tool.getIdTool()))) {
            button.setVisible(true);
            this.toolWindowManager.invokeToolCommand(tool.getIdTool());
            this.toolWindowManager.setSlotId(tool.getToolSlot());
            this.data.setSlotChosen(tool.getToolSlot());

        } else {
            button.setVisible(false);
            this.manager.communicateMessage("Non puoi usare questa tool (Hai già effettuato un piazzamento oppure non" +
                    "hai abbastanza segnalini favore.");
        }
    }

    public void showDraftedDie(SetUpInformationUnit info) {
        DieFactory dieFactory =  new DieFactory();
        DieGUI die = dieFactory.getsDieGUI(info);

        VBox dieDrafted = new VBox();
        dieDrafted.setAlignment(Pos.CENTER);
        dieDrafted.getStylesheets().add("style/backgrounds.css");
        dieDrafted.getStyleClass().addAll("VBox", "background");
        dieDrafted.setSpacing(20);
        dieDrafted.setPadding(new Insets(30));

        Label title = new Label("Ecco il dado con il nuovo valore");
        title.getStyleClass().add("text-label");

        Button proceed = new Button("Procedi");
        proceed.getStyleClass().add("button-style");


        dieDrafted.getChildren().addAll(title, die, proceed);

        Stage newWindow = new Stage();
        newWindow.initStyle(StageStyle.TRANSPARENT);
        Scene second = new Scene(dieDrafted);
        proceed.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> newWindow.close());

        second.setFill(Color.TRANSPARENT);
        newWindow.setScene(second);
        GUIMain.centerScreen();
        GUIMain.dragWindow(dieDrafted);
        newWindow.initStyle(StageStyle.TRANSPARENT);
        newWindow.initOwner(GUIMain.getStage());
        newWindow.show();

        manageToolButtons(true);
    }

    public void manageToolButtons(Boolean val) {
        HBox toolCont = (HBox)this.publToolDraftCont.getChildren().get(1);
        ToolCardGUI tool = (ToolCardGUI)toolCont.getChildren().get(this.data.getSlotChosen());
        tool.hideShowButton(val);

        HBox toolInfoCont = (HBox)tool.getChildren().get(1);
        VBox toolComm = (VBox)toolInfoCont.getChildren().get(1);

        Button showButton = (Button)toolComm.getChildren().get(1);

        showButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.toolWindowBuilder.invokeMethodShowWindow(tool.getIdTool()));

    }

    public void updateToolCost(int slot, int cost) {
        HBox toolCont = (HBox)this.publToolDraftCont.getChildren().get(1);
        ToolCardGUI tool = (ToolCardGUI)toolCont.getChildren().get(slot);
        tool.updateCost(cost);
    }



    public void showMessage(String message) {
        //((Label)((HBox)this.publToolDraftCont.getChildren().get(2)).getChildren().get(0)).setText(message);
        this.manager.communicateMessage(message);
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
        GridPane grid = this.data.getPersonalWp().getGlassWindow();
        //This cycle adds css to the glass window of the player.
        for(int i=0; i< WpGui.MAX_ROW; i++)
            for(int j=0; j<WpGui.MAX_COL; j++) {
                StackPane stack = (StackPane) grid.getChildren().get(WpGui.MAX_COL * i + j);
                stack.getStyleClass().add("dieChosen");
            }
       this.otherMaps.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> otherMapsHandler());
        this.otherMaps.addEventHandler(MouseEvent.MOUSE_EXITED, e -> this.other.close());
    }


    /**
     * This method checks if the die placement move has been done correctly, if so,  invokes the proper method
     * to execute it.
     */
    public void validateMoveHandler() {
        if(!(this.getData().getPersonalWp().isWpCellClicked() && this.draftPoolGUI.isDraftCellChosen())) {
            this.manager.communicateMessage("Non hai riempito i campi corretti");
            this.ok.setVisible(false);
        }
        else {
            this.ok.setVisible(false);
            SetUpInformationUnit info = this.data.getSetUpInformationUnit();
            info.setSourceIndex(this.draftPoolGUI.getIndexChosenCell());
            info.setDestinationIndex(this.data.getPersonalWp().getClicked());
            this.manager.executeCommandIfPresent("Piazzamento");
        }
    }


    /**
     * This method manages the placement of a die from the draft pool to the personal
     * window pattern card in the GUI.
     */
    public void diePlacementHandler() {
         if(this.manager.isCommandContained("Piazzamento"))
             this.ok.setVisible(true);
         else {
             this.manager.communicateMessage("Non puoi effettuare un piazzamento");
         }
    }


    /**
     * This method manages the pass turn command.
     */
    private void passTurnHandler() {
        if(this.manager.isCommandContained("Passa"))
            this.manager.executeCommandIfPresent("Passa");
         else {
            this.manager.communicateMessage("Comando non supportato poichè non è il tuo turno.");
        }
    }

    private VBox otherMap;
    private Stage other = new Stage();

    /**
     * This method builds the stage for the support windows of the tools.
     */
    private void supportStageBuilder() {
        this.other = new Stage();
        this.other.initStyle(StageStyle.TRANSPARENT);

        GUIMain.centerScreen();

        other.initStyle(StageStyle.TRANSPARENT);
        other.initOwner(GUIMain.getStage());
    }

    private void otherMapsHandler() {
        otherMap = new VBox();
        otherMap.getStylesheets().add("style/backgrounds.css");
       otherMap.getStyleClass().addAll("VBox");

        List<VBox> list = this.data.getOtherPLayersMaps();
        list.forEach(elem -> otherMap.getChildren().add(elem));

        Scene maps = new Scene(otherMap);

        maps.setFill(Color.TRANSPARENT);
        other.setScene(maps);
        //other.setX(300);
      // other.setY(50);
        other.show();

    }



    public void resetIsAlreadyUsedFlag() {
        this.toolWindowManager.setAlreadyUsed(false);
    }

    public void sendMessage(String message) {
        this.manager.communicateMessage(message);
    }


    public RoundTrackGUI getRoundTrack() {
        return roundTrack;
    }

    public void setRoundTrack(RoundTrackGUI roundTrack) {
        this.roundTrack = roundTrack;
    }

    public GUICommunicationManager getManager() {
        return manager;
    }

    public void setManager(GUICommunicationManager manager) {
        this.manager = manager;
    }

    public Button getOtherMaps() {
        return otherMaps;
    }

    public void setOtherMaps(Button otherMaps) {
        this.otherMaps = otherMaps;
    }
}
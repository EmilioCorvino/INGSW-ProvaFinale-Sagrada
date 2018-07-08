package it.polimi.ingsw.client.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class manages the communication among the windows of the gui and the server.
 */
public class GUICommunicationManager {

   /**
    * This attribute represents the available commands for the player.
    */
   private Map<String, Runnable> functions;

   /**
    * This is the stage for the notifications.
    */
   private Stage newWindow;

   /**
    * This indicates if it is a reconnection state or not.
    */
   private boolean isReconnected;

   private  boolean isNewGame;

   public GUICommunicationManager() {
      functions = new LinkedHashMap<>();

      newWindow = new Stage();
      newWindow.initStyle(StageStyle.TRANSPARENT);
      newWindow.initOwner(GUIMain.getStage());
      GUIMain.centerScreen();

      //newWindow.setX(GUIMain.getStage().getX() + 200);
      //newWindow.setY(GUIMain.getStage().getY() + 300);
   }

   /**
    * This method checks if a command is available for the player.
    * @param commands the command to check.
    * @return true if the command is contained in the map, false otherwise.
    */
   public boolean isCommandContained(String commands){
      return functions.containsKey(commands);
   }

   /**
    * This method executes the command the player chose.
    * @param command the command to execute.
    */
   public void executeCommandIfPresent(String command) {// EXECUTE COMMAND IF PRESENT
      if(functions.containsKey(command)) {
         functions.get(command).run();
      }
   }

   /**
    * This method executes the command the player chose for the tool card.
    * @param tool the command tool to execute.
    */
   public void executeCommandToolIfPresent(int tool) {// EXECUTE COMMAND IF PRESENT
      if(functions.containsKey("Strumento " + tool)) {
         functions.get("Strumento " + tool).run();
      }
   }

   /**
    * This method constructs a notification to display to the user.
    * @param message the message to display.
    */
   public void communicateMessage(String message) {

      VBox secondWindow = new VBox();
      secondWindow.setAlignment(Pos.CENTER);
      secondWindow.setSpacing(5);
      Label secondLabel = new Label(message);

      //Adds the message
      secondWindow.getChildren().addAll(secondLabel);
      secondLabel.setPrefWidth(400);
      secondLabel.setWrapText(true);
      secondLabel.setPadding(new Insets(15));
      secondLabel.setAlignment(Pos.CENTER);
      secondLabel.getStyleClass().add("text-label");

      if(isCommandContained("Nuova Partita")) {
         HBox buttons = new HBox();
         buttons.setAlignment(Pos.CENTER);
         Button newGame = new Button("Nuova partita");
         newGame.getStyleClass().add("button-style");

         buttons.getChildren().addAll(newGame);

         newGame.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(this.isCommandContained("Nuova Partita")) {
               this.isNewGame = true;
               buttons.getChildren().remove(newGame);
               this.executeCommandIfPresent("Nuova Partita");

            }

            else
               this.communicateMessage("Non disponibile");
         });

         //Adds the buttons
         secondWindow.getChildren().add(buttons);
      }


      //Adds the ok button to close the window.
      Button ok = new Button("ok");
      ok.getStyleClass().add("button-style");
      secondWindow.getChildren().add(ok);

      ok.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> this.newWindow.close());

      secondWindow.getStylesheets().add("style/backgrounds.css");
      secondWindow.getStyleClass().add("background");
      secondWindow.getStyleClass().add("notification");

      Scene second = new Scene(secondWindow, 400, 200);
      second.setFill(Color.TRANSPARENT);
      newWindow.setScene(second);
      newWindow.show();
   }

   public void manageOnePlayerLeft() {
      VBox commands = new VBox();
      commands.setPadding(new Insets(10, 20, 10, 20));
      commands.getStylesheets().add("style/backgrounds.css");
      commands.getStyleClass().addAll("background", "VBox");
      commands.setAlignment(Pos.CENTER);
      commands.setSpacing(10);

      Label title = new Label("Scegli cosa vuoi fare");
      title.getStyleClass().add("text-label");

      HBox buttons = new HBox();
      buttons.setSpacing(5);
      Button newGame = new Button("Nuova partita");
      newGame.getStyleClass().add("button-style");
      Button exit = new Button("Esci");
      exit.getStyleClass().add("button-style");
      buttons.getChildren().addAll(newGame, exit );

      commands.getChildren().addAll(title, buttons);



      exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
         if(this.isCommandContained("Logout"))
            this.executeCommandIfPresent("Logout");
         else
            this.communicateMessage("Non disponibile");
      });

      Scene second = new Scene(commands);
      second.setFill(Color.TRANSPARENT);
      newWindow.setScene(second);
      newWindow.show();

   }

   public void setFunctions(Map<String, Runnable> functions) {
      this.functions = functions;
   }

   public boolean isReconnected() {
      return isReconnected;
   }

   public void setReconnected(boolean reconnected) {
      isReconnected = reconnected;
   }

   public boolean isNewGame() {
      return isNewGame;
   }

   public void setNewGame(boolean newGame) {
      isNewGame = newGame;
   }
}
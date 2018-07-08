package it.polimi.ingsw.client.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

   GUICommunicationManager() {
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

      secondWindow.getChildren().addAll(secondLabel);
      secondLabel.setPrefWidth(400);
      secondLabel.setWrapText(true);
      secondLabel.setPadding(new Insets(15));
      secondLabel.setAlignment(Pos.CENTER);
      secondLabel.getStyleClass().add("text-label");

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

   public void setFunctions(Map<String, Runnable> functions) {
      this.functions = functions;
   }

   public boolean isReconnected() {
      return isReconnected;
   }

   public void setReconnected(boolean reconnected) {
      isReconnected = reconnected;
   }
}
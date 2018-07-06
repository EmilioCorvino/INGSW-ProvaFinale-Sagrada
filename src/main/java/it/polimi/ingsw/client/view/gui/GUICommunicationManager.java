package it.polimi.ingsw.client.view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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

   private Stage newWindow;

   GUICommunicationManager() {
      functions = new LinkedHashMap<>();

      newWindow = new Stage();
      newWindow.initStyle(StageStyle.TRANSPARENT);



      //newWindow.initModality(Modality.WINDOW_MODAL);

      newWindow.initOwner(GUIMain.getStage());


      newWindow.setX(GUIMain.getStage().getX() + 200);
      newWindow.setY(GUIMain.getStage().getY() + 100);
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

      Pane secondWindow = new Pane();
      Label secondLabel = new Label(message);
      //secondWindow.setLayoutX(200);
     // secondWindow.setLayoutY(100);
      secondWindow.getChildren().addAll(secondLabel);
      secondLabel.setPrefWidth(400);
      secondLabel.setWrapText(true);
      secondLabel.setPadding(new Insets(15));
      secondLabel.setAlignment(Pos.CENTER);
      secondLabel.getStyleClass().add("text-label");


      secondWindow.getStylesheets().add("style/backgrounds.css");
      secondWindow.getStyleClass().add("background");
      secondWindow.getStyleClass().add("notification");

      Scene second = new Scene(secondWindow, 400, 110);
      second.setFill(Color.TRANSPARENT);

       newWindow.setScene(second);

       newWindow.show();

   }

   public Map<String, Runnable> getFunctions() {
      return functions;
   }

   public void setFunctions(Map<String, Runnable> functions) {
      this.functions = functions;
   }
}
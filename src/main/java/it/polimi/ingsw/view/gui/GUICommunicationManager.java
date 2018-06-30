package it.polimi.ingsw.view.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

   GUICommunicationManager() {
      functions = new LinkedHashMap<>();
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
    * This method constructs a notification to display to the user.
    * @param message the message to display.
    */
   public void communicateMessage(String message) {
      /*
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.initStyle(StageStyle.TRANSPARENT);
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
      alert.setOnCloseRequest(event -> alert.hide());
      */

      Pane secondWindow = new Pane();
      Label secondLabel = new Label(message);
      secondWindow.getChildren().addAll(secondLabel);

      Scene second = new Scene(secondWindow, 100, 100);

      Stage newWindow = new Stage();
      newWindow.setTitle("Second Stage");
      newWindow.setScene(second);

      newWindow.initModality(Modality.WINDOW_MODAL);

      newWindow.initOwner(GUIMain.getStage());


      newWindow.setX(GUIMain.getStage().getX() + 200);
      newWindow.setY(GUIMain.getStage().getY() + 100);

      newWindow.show();

   }

   public Map<String, Runnable> getFunctions() {
      return functions;
   }

   public void setFunctions(Map<String, Runnable> functions) {
      this.functions = functions;
   }
}
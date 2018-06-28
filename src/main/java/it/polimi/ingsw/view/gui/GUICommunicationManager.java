package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.Commands;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.Map;

public class GUICommunicationManager {

   private Map<Commands, Runnable> functions;

   GUICommunicationManager() {

      functions = new LinkedHashMap<>();
   }

   public Map<Commands, Runnable> getFunctions() {
      return functions;
   }

   public void setFunctions(Map<Commands, Runnable> functions) {
      this.functions = functions;
   }

   public boolean isCommandContained(Commands commands){
      return functions.containsKey(commands);
   }

   public void executeCommandIfPresent(Commands command) {// EXECUTE COMMAND IF PRESENT
      if(functions.containsKey(command)) {
         functions.get(command).run();
         this.functions.remove(command);
      }
   }

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

}

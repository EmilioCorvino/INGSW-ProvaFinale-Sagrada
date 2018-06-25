package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.Commands;
import javafx.scene.control.Alert;

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

   public boolean isCommandContained(Commands command) {
      if(functions.containsKey(command)) {
         functions.get(command).run();
         this.functions.remove(command);
         return true;
      }
      return false;
   }

   public void communicateMessage(String message) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
      alert.setOnCloseRequest(event -> alert.hide());

   }


}

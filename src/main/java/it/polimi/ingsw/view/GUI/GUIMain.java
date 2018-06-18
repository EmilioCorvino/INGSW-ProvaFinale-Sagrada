package it.polimi.ingsw.view.GUI;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIMain extends Application {


    private static Scene scene;


    @Override
    public void start(Stage primaryStage) {


        Parent root = new GameHome();

        scene = new Scene(root, 900, 700);
        root.setStyle("-fx-background-color : #001a4d");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        GUIMain.scene = scene;
    }
}
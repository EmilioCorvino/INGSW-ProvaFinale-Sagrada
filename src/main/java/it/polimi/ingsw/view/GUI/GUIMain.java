package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.view.IViewMaster;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIMain extends Application {


    private static Scene scene;


    @Override
    public void start(Stage primaryStage) {


        IViewMaster guiView = new GUIView();

        Parent root = new GameHome("Indirizzo ip del server", "RMI", "Socket");

        scene = new Scene(root, 900, 700);
        root.setStyle("-fx-background-color : #001a4d");
        primaryStage.setScene(scene);
        primaryStage.show();

        ((GUIView) guiView).setLoginManager((GameHome)root);
        guiView.createConnection(guiView);
    }


    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        GUIMain.scene = scene;
    }
}
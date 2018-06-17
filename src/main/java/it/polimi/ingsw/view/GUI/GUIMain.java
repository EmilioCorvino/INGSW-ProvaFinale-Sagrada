package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIMain extends Application {

    Pane root;

    public GUIMain() {
        root = new Pane();
    }

    @Override
    public void start(Stage primaryStage) {


        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
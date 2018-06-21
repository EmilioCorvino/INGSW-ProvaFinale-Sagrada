package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.IViewMaster;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class GUIMain extends Application {

    private static Scene scene;

    private static IViewMaster guiView;

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        setStage(primaryStage);
        guiView = new GUIView();
        RootLoginGui root = new LoginIpAddrTypeConnGUI();
        scene = new Scene(root, 1400, 730);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);



        primaryStage.setScene(scene);
        primaryStage.show();
        ((GUIView) guiView).setLoginManager((LoginIpAddrTypeConnGUI)root);
    }


    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        GUIMain.scene = scene;
    }

    public static IViewMaster getGuiView() {
        return guiView;
    }

    public static void setGuiView(IViewMaster guiView) {
        GUIMain.guiView = guiView;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        GUIMain.stage = stage;
    }
}
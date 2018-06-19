package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.IViewMaster;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIMain extends Application {

    private static Scene scene;

    private static IViewMaster guiView;

    @Override
    public void start(Stage primaryStage) {
        guiView = new GUIView();
        Parent root = new LoginIpAddrTypeConnGUI();
        scene = new Scene(root, 900, 700);

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
}
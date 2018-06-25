package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.gui.loginwindows.LoginIpAddrTypeConnGUI;
import it.polimi.ingsw.view.gui.loginwindows.LoginRootGUI;
import it.polimi.ingsw.view.IViewMaster;
import javafx.application.Application;
import javafx.scene.Parent;
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
        LoginRootGUI root = new LoginIpAddrTypeConnGUI();
        scene = new Scene(root);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);


        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        ((GUIView) guiView).setLoginManager((LoginIpAddrTypeConnGUI)root);
    }

/*
    public static Scene getScene() {
        return scene;
    }

    */

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

    /**
     * This method is used to change the shown screen.
     * @param root the screen to show
     */
    public static void setRoot(Parent root) {
        scene.setRoot(root);
        stage.sizeToScene();
    }

    /**
     * This method is used to center the new window in the monitor.
     */
    public static void centerScreen() {
        stage.centerOnScreen();
    }


}
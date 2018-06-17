package it.polimi.ingsw;

import it.polimi.ingsw.view.GUI.GUIMain;
import it.polimi.ingsw.view.IViewMaster;
import it.polimi.ingsw.view.cli.CliView;
import javafx.application.Application;

import java.util.Scanner;

/**
 * This class is used to launch a client that uses the command line interface.
 * @see CliView
 */
public class ClientMain {

    public static void main(String[] args) {

        System.out.println("Scegli il tipo di view: -cli-  o -gui-");
        Scanner scan = new Scanner(System.in);
        String code = scan.next();

        while(!(code.equals("cli") || code.equals("gui"))) {
            System.out.println("Scegli il tipo di view: -cli-  o -gui-");
            code = scan.next();
        }

        if(code.equals("cli")) {
            IViewMaster viewMaster = new CliView();
            viewMaster.createConnection(viewMaster);
        }

        if(code.equals("gui")) {
            GUIMain gui = new GUIMain();
            Application.launch(gui.getClass(), args);

        }

    }
}

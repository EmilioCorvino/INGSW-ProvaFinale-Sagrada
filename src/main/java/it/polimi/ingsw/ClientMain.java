package it.polimi.ingsw;

import it.polimi.ingsw.view.IViewMaster;
import it.polimi.ingsw.view.cli.CliView;
import it.polimi.ingsw.view.cli.managers.general.InputOutputManager;
import it.polimi.ingsw.view.gui.GUIMain;
import javafx.application.Application;


/**
 * This class is used to launch a client that uses the command line interface.
 * @see CliView
 */
public class ClientMain {

    public static void main(String[] args) {
        InputOutputManager inputOutputManager = new InputOutputManager();

        String code = inputOutputManager.askInformation("Scegli il tipo di view: \033[0;1mcli\033[0m o \033[0;1mgui\033[0m");

        while(!("cli".equalsIgnoreCase(code) || "gui".equals(code))) {
            code = inputOutputManager.askInformation("Scegli il tipo di view: -cli-  o -gui-");
        }

        if("cli".equalsIgnoreCase(code)) {
            IViewMaster viewMaster = new CliView(inputOutputManager);
            viewMaster.createConnection(viewMaster);
        }else{
            inputOutputManager.closeScanner();
            Application.launch(GUIMain.class, args);
        }
    }
}

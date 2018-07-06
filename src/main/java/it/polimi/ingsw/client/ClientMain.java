package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.IViewMaster;
import it.polimi.ingsw.client.view.cli.CliView;
import it.polimi.ingsw.client.view.cli.managers.general.InputOutputManager;
import it.polimi.ingsw.client.view.gui.GUIMain;
import it.polimi.ingsw.common.utils.SagradaLogger;
import javafx.application.Application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;


/**
 * This class is used to launch a client that uses the command line interface.
 * @see CliView
 */
public class ClientMain {

    public static void main(String[] args) {
        String localAddress = "127.0.0.1";
        try {
            localAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            SagradaLogger.log(Level.SEVERE, "Unable to retrieve host IP");
        }
        System.setProperty("java.rmi.server.hostname", localAddress);
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

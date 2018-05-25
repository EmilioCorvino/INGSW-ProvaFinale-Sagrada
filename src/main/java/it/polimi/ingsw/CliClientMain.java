package it.polimi.ingsw;

import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.CliView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * This class is used to launch a client that uses the command line interface.
 * @see CliView
 */
public class CliClientMain {

    //Client Logger setup.
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger LOGGER = Logger.getLogger("clientConfigurationLogger");
    static {
        try {
            logManager.readConfiguration(new FileInputStream(
                    "./src/main/java/it/polimi/ingsw/utils/logs/logger.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error in loading configuration", e);
        }
    }

    public static void main(String[] args) {
        AViewMaster viewMaster = new CliView();
        viewMaster.createConnection(viewMaster);
    }
}

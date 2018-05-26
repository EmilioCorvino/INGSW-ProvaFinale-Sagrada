package it.polimi.ingsw;

import it.polimi.ingsw.utils.logs.SagradaLogger;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.CliView;

import java.util.logging.Level;

/**
 * This class is used to launch a client that uses the command line interface.
 * @see CliView
 */
public class CliClientMain {

    public static void main(String[] args) {
        AViewMaster viewMaster = new CliView();
        viewMaster.createConnection(viewMaster);
    }
}

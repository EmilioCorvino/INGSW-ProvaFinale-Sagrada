package it.polimi.ingsw;

import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.CliView;

/**
 * This class is used to launch a client that uses the command line interface.
 * @see CliView
 */
public class CliClientMain {

    public static void main(String[] args) {
        //new Thread(new ScannerThread(this::analyzeStringInput)).start();
        AViewMaster viewMaster = new CliView();
        viewMaster.createConnection(viewMaster);
    }
}

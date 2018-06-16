package it.polimi.ingsw;

import it.polimi.ingsw.view.IViewMaster;
import it.polimi.ingsw.view.cli.CliView;

/**
 * This class is used to launch a client that uses the command line interface.
 * @see CliView
 */
public class ClientMain {

    public static void main(String[] args) {
        IViewMaster viewMaster = new CliView();
        viewMaster.createConnection(viewMaster);
    }
}

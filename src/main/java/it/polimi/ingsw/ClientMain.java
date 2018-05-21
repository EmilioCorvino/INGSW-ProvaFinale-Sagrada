package it.polimi.ingsw;

import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.CliView;

public class ClientMain {

    public static final int PORT = 54242;

    public static void main(String[] args) {

        /*todo here an initial GUI window should be launched, that allows to choose between
        CLI and GUI interface */
        AViewMaster view = new CliView();
        view.createConnection();
    }
}

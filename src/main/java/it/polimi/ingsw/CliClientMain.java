package it.polimi.ingsw;

import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.CliView;

public class CliClientMain {


    public static void main(String[] args) {

        /*todo here an initial GUI window should be launched, that allows to choose between
        CLI and GUI interface */
        AViewMaster viewMaster = new CliView();
        viewMaster.createConnection(viewMaster);
    }
}

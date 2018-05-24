package it.polimi.ingsw;

import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.CliView;

public class CliClientMain {


    public static void main(String[] args) {

        AViewMaster viewMaster = new CliView();
        viewMaster.createConnection(viewMaster);
    }
}

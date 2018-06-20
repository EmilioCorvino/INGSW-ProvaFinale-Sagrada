package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.view.cli.generalmanagers.InputOutputManager;

public class CliCommunicationManager {
    /**
     * A reference to the view.
     */
    private CliView view;

    /**
     * A reference to the inputOutputManager of the view
     */
    private InputOutputManager inputOutputManager;

    /**
     * The network interface for the connection
     */
    private IFromClientToServer server;

    public CliCommunicationManager(CliView view){
        this.view = view;
        this.inputOutputManager = view.getInputOutputManager();
        this.server = view.getServer();
    }

    public CliView getView() {
        return view;
    }

    public void setView(CliView view) {
        this.view = view;
    }

    public InputOutputManager getInputOutputManager() {
        return inputOutputManager;
    }

    public void setInputOutputManager(InputOutputManager inputOutputManager) {
        this.inputOutputManager = inputOutputManager;
    }

    public IFromClientToServer getServer() {
        return server;
    }

    public void setServer(IFromClientToServer server) {
        this.server = server;
    }
}

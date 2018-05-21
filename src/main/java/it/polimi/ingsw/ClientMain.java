package it.polimi.ingsw;

import it.polimi.ingsw.network.rmi.RmiClient;
import it.polimi.ingsw.view.AViewMaster;
import it.polimi.ingsw.view.cli.CliView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientMain {

    public static final int PORT = 54242;
    private final AViewMaster viewMaster;

    private ClientMain(AViewMaster viewMaster) {
        this.viewMaster = viewMaster;
    }

    public static void main(String[] args) {

        /*todo here an initial GUI window should be launched, that allows to choose between
        CLI and GUI interface */
        ClientMain clientMain = new ClientMain(new CliView());
        try {
            RmiClient rmiClient = new RmiClient(clientMain.viewMaster);
            IClient skeleton = (IClient) UnicastRemoteObject.exportObject(rmiClient, PORT);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.bind("IClient", skeleton);
        } catch (Exception e) {
            System.err.println("Rmi client exception: " + e.toString());
            e.printStackTrace();
        }
        clientMain.viewMaster.createConnection();
    }
}

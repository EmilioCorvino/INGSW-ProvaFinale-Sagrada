package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.network.rmi.RmiFromClientToServer;
import it.polimi.ingsw.network.socket.SocketFromClientToServer;
import it.polimi.ingsw.view.AViewMaster;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

/**
 * This class is used to manage all the interaction during the connection and login state.
 */
public class LoginCli implements Serializable {

    /**
     * This method ask the IP address of the server to the user.
     * @return The IP address chosen by the user.
     */
    public String getIp(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Inserire l'indirizzo IP del server: ");
        return scan.next();
    }

    public String getUsername(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Inserire l'username: ");
        return scan.next();
    }

    public String getGameMode(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Inserire la modalità di partita (GiocatoreSingolo/Multigiocatore): ");
        String gameMode = scan.next();
        while (gameMode.equals("GiocatoreSignolo") || gameMode.equals("Multigiocatore")) {
            System.out.println("ERRORE: Scelta non supportata, inserire GiocatoreSingolo o Multigiocatore: ");
            gameMode = scan.next();
        }

        return gameMode;
    }

    /**
     * This method allow the user to choose the kind of network interface
     * @return An instance of the network interface chosen.
     * @param ip : the ip address uses to create the connection.
     * @param viewMaster
     */
    public IFromClientToServer chooseNetworkInterface(String ip, AViewMaster viewMaster) throws RemoteException{

        Scanner scan = new Scanner(System.in);
        System.out.println("Scegliere l'interfaccia di rete desiderata, digitare Socket o RMI: ");
        String networkType = scan.next();
        while (networkType.equals("Socket") || networkType.equals("rmi")) {
            System.out.println("ERRORE: Scelta errata, digitare Socket o RMI");
            networkType = scan.next();
        }
        if (networkType.equals("Socket"))
            return new SocketFromClientToServer();
        else if (networkType.equals("RMI"))
            return new RmiFromClientToServer(ip, viewMaster);

        return null;
    }

    /**
     * This method print the waiting room when the player wait for start a match.
     * @param players
     */
    public void showRoom(List<String> players) {
        System.out.println("In attesa di connessione di altri giocatori...");
        System.out.println("Giocatori momentaneamente connessi: ");
        for (String name : players)
            System.out.println(name);

    }
}

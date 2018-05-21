package it.polimi.ingsw.view.cli;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.network.rmi.RmiFromClientToServer;
import it.polimi.ingsw.network.socket.SocketFromClientToServer;
import it.polimi.ingsw.view.AViewMaster;

import java.util.List;
import java.util.Scanner;

public class CliView extends AViewMaster {

    /**
     * The network interface for the connection
     */
    IFromClientToServer server;

    /**
     * This method create the connection and logs the player
     */
    @Override
    public void createConnection() {

        Scanner scan = new Scanner(System.in);
        System.out.println("Inserire l'indirizzo IP del server: ");
        String ip = scan.next();

        System.out.println("Inserire l'username: ");
        String userName = scan.next();

        System.out.println("Inserire la modalità di partita (GiocatoreSingolo/Multigiocatore): ");
        String gameMode = scan.next();
        while (gameMode.equals("GiocatoreSignolo") || gameMode.equals("Multigiocatore")) {
            System.out.println("ERRORE: Scelta non supportata, inserire GiocatoreSingolo o Multigiocatore: ");
            gameMode = scan.next();
        }

        this.server = chooseNetworkInterface(ip);
        this.server.login(userName, ip, gameMode);
    }

    /**
     * This method allow the user to choose the kind of network interface
     * @return An instance of the network interface chosen.
     * @param ip
     */
    @Override
    public IFromClientToServer chooseNetworkInterface(String ip) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Scegliere l'interfaccia di rete desiderata, digitare Socket o RMI: ");
        String networkType = scan.next();
        while (networkType.equals("Socket") || networkType.equals("RMI")) {
            System.out.println("ERRORE: Scelta errata, digitare Socket o RMI");
            networkType = scan.next();
        }
        if (networkType.equals("Socket"))
            return new SocketFromClientToServer();
        else if (networkType.equals("RMI"))
            return new RmiFromClientToServer(ip);

        return null;
    }

    /**
     * This method print the waiting room when the player wait for start a match.
     * @param players
     */
    @Override
    public void showRoom(List<String> players) {
        System.out.println("In attesa di connessione di altri giocatori...");
        System.out.println("Giocatori momentaneamente connessi: ");
        for (String name : players)
            System.out.println(name);

    }

    @Override
    public void showInitializedBoard(CommonBoard board) {

    }

    @Override
    public void showRank(String[] players, int[] score) {

    }

    @Override
    public void showAvailablePlaces(List<Integer> idDice) {

    }


}

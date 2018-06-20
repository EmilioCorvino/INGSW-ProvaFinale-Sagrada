package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.network.IFromClientToServer;
import it.polimi.ingsw.network.rmi.RmiFromClientToServer;
import it.polimi.ingsw.network.socket.SocketFromClientToServer;
import it.polimi.ingsw.view.IViewMaster;
import it.polimi.ingsw.view.cli.generalmanagers.InputOutputManager;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class is used to manage all the interaction during the connection and notifyWaitingPlayers state.
 */
public class LoginManager {

    /**
     * Input Output Manager
     */
    private InputOutputManager inputOutputManager;

    public LoginManager(InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
    }
    /**
     * This method ask the IP address of the server to the user.
     * @return The IP address chosen by the user.
     */
    public String getIp(){

        Pattern p = Pattern.compile("^"
                + "(((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}" // Domain name
                + "|"
                + "localhost" // localhost
                + "|"
                + "(([0-9]{1,3}\\.){3})[0-9]{1,3})"); // Ip

        String ip = inputOutputManager.askInformation("Inserire l'indirizzo IP del server: ");
        boolean validIp = p.matcher(ip).matches();

        while(!validIp) {
            ip = inputOutputManager.askInformation("Errore: Indirizzo IP non corretto!\nInserire nuovamente: ");
            validIp = p.matcher(ip).matches();
        }

        return ip;
    }

    /**
     * This method ask the username to the user.
     * @return the username.
     */
    public String getUserName(){
        return inputOutputManager.askInformation("\nInserire l'username: ");
    }

    /**
     * This method allow the user to chose the game mode between single and multi player.
     * @return The identifier of the game mode.
     */
    public int getGameMode(){
        String gameMode = inputOutputManager.askInformation("\nInserire la modalità di partita:\n\t 1 - Multigiocatore\n\t 2 - Giocatore Singolo");
        while (!("1".equals(gameMode) || "2".equals(gameMode))) {
            inputOutputManager.print("ERRORE: Scelta non supportata, inserire GiocatoreSingolo o Multigiocatore: ");
            gameMode = inputOutputManager.read();
        }
        return Integer.parseInt(gameMode);
    }

    /**
     * This method allow the user to choose the kind of network interface
     * @return An instance of the network interface chosen.
     * @param ip : the ip address uses to create the connection.
     * @param view: An instance of view.
     */
    public IFromClientToServer chooseNetworkInterface(String ip, IViewMaster view) throws BrokenConnectionException {

        Scanner scan = new Scanner(System.in);
        String networkType;

        do {
            inputOutputManager.print("\nScegliere l'interfaccia di rete desiderata:\n\t 1 - Socket\n\t 2 - RMI");
            networkType = scan.next().trim();
        } while (!("1".equals(networkType) || "2".equals(networkType)));
        if (networkType.equals("1"))
            return new SocketFromClientToServer();

        return new RmiFromClientToServer(ip, view);
    }

    /**
     * This method print the waiting room when the player wait for start a match.
     * @param players: list of username of players connected to the server.
     */
    public void showRoom(List<String> players) {
        inputOutputManager.print("\n--------------------------------");
        inputOutputManager.print("In attesa di connessione di altri giocatori...");
        inputOutputManager.print("Giocatori momentaneamente connessi: ");
        for (String name : players)
            inputOutputManager.print(" - "+name);

    }
}

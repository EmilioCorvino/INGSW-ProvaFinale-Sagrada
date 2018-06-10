package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.Commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains the map of all the possible command usable in the game and the method to filter them depending
 * of the game state (depending of commands given by the controller).
 */
public class Bank {

    /**
     * This map contains all the possible command usable in all the game.
     */
    private Map<Commands, Runnable> bank;

    /**
     * This is a reference to the view
     */
    private CliView view;

    /**
     * This is the object that contains all the possible function related to the commands.
     */
    private CommunicationManager communicationManager;


    public Bank (CliView view){
        this.bank = new HashMap<>();
        this.createMap();
        this.view = view;
        this.communicationManager = new CommunicationManager(this.view);
    }

    /**
     * This method populate the map matching the command with his method.
     */
    private void createMap(){
        bank.put(Commands.PLACEMENT, communicationManager::defaultPlacement);
        bank.put(Commands.OTHER_PLAYERS_MAPS, communicationManager::showAllWp);
        bank.put(Commands.PUBLIC_OBJ_CARDS, communicationManager::showPublicObj);
        bank.put(Commands.AVAILABLE_TOOL_CARDS, communicationManager::showTool);
        bank.put(Commands.PRIVATE_OBJECTIVE_CARDS, communicationManager::showPrivateObj);
    }

    /**
     * This method create a map of commands taking them from the bank
     * @param commands: The list of commands available
     * @return: the new map that contains the methods just of the command available.
     */
    public Map<String, Runnable> getCommandMap(List <Commands> commands){
        Map<String, Runnable> function = new HashMap<>();

       commands.forEach(c -> function.put(commandToStringConverter(c), bank.get(c)));

        return function;
    }

    /**
     * This method convert a command to a string
     * @param command: The command that need to be converted.
     * @return: the string of the command.
     */
    private String commandToStringConverter(Commands command){
        return command.toString();
    }
}

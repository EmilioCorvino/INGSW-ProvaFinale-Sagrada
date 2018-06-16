package it.polimi.ingsw.view.cli.commands;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.view.ICommunicationManager;

import java.util.*;

/**
 * This class contains the map of all the possible command usable in the game and the method to filter them depending
 * of the game state (depending of commands given by the controller).
 */
public class Bank {

    /**
     * This map contains all the possible command usable in all the game.
     */
    private Map<Commands, Runnable> availableCommands;

    /**
     * This map match the command with his corresponding italian translation (with an attribute string that will be the string
     * printed to the user)
     */
    private Map<Commands,UserCommands> commandMap;

    /**
     * This is the object that contains all the possible function related to the commands.
     */
    private ICommunicationManager communicationManager;


    public Bank (){
        this.availableCommands = new EnumMap<>(Commands.class);
        this.commandMap = new EnumMap<>(Commands.class);
    }

    /**
     * This method populate the map matching the command with his method.
     */
    private void populateAvailableCommandMap(){
        availableCommands.put(Commands.CHOOSE_WP, communicationManager::chooseWp);
        availableCommands.put(Commands.PLACEMENT, communicationManager::defaultPlacement);
        availableCommands.put(Commands.OTHER_PLAYERS_MAPS, communicationManager::showAllWp);
        availableCommands.put(Commands.PUBLIC_OBJ_CARDS, communicationManager::showPublicObj);
        availableCommands.put(Commands.AVAILABLE_TOOL_CARDS, communicationManager::showTool);
        availableCommands.put(Commands.PRIVATE_OBJ_CARD, communicationManager::showPrivateObj);
        availableCommands.put(Commands.END_TURN, communicationManager::moveToNextTurn);
        availableCommands.put(Commands.LOGOUT, communicationManager::exitGame);
    }

    /**
     * This method populate the map matching the command with the corresponding command translated.
     */
    private void populateCommandMap(){
        commandMap.put(Commands.CHOOSE_WP, UserCommands.SCELTA_WP);
        commandMap.put(Commands.PLACEMENT, UserCommands.PIAZZAMENTO);
        commandMap.put(Commands.OTHER_PLAYERS_MAPS, UserCommands.MAPPE_ALTRI_GIOCATORI);
        commandMap.put(Commands.PUBLIC_OBJ_CARDS, UserCommands.OBBIETTIVI_PUBBLICI);
        commandMap.put(Commands.AVAILABLE_TOOL_CARDS, UserCommands.CARTE_STRUMENTO_DISPONIBILI);
        commandMap.put(Commands.PRIVATE_OBJ_CARD, UserCommands.OBIETTIVO_PRIVATO);
        commandMap.put(Commands.END_TURN, UserCommands.PASSA);
        commandMap.put(Commands.LOGOUT, UserCommands.LOGOUT);
    }


    /**
     * This method create a map of commands taking them from the bank
     * @param commands: The list of commands available
     * @return the new map that contains the methods just of the command available.
     */
    public Map<String, Runnable> getCommandMap(List <Commands> commands){
        Map<String, Runnable> function = new LinkedHashMap<>();

        commands.forEach(c -> function.put(commandToStringConverter(c), availableCommands.get(c)));
        function.put(UserCommands.HELP.getDescription(), communicationManager::printCommands);

        return function;
    }

    /**
     * This method convert a command to a string
     * @param command: The command that need to be converted.
     * @return the string of the command.
     */
    private String commandToStringConverter(Commands command){
        return commandMap.get(command).getDescription();
    }

    public void setCommunicationManager(ICommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
        this.populateAvailableCommandMap();
        this.populateCommandMap();
    }
}

package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.view.cli.commands.UserCommands;

import java.util.EnumMap;
import java.util.LinkedHashMap;
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
    private Map<Commands, Runnable> availableCommands;

    /**
     * This map match the command with his corresponding italian translation (with an attribute string that will be the string
     * printed to the user)
     */
    private Map<Commands,UserCommands> commandMap;

    /**
     * This is the object that contains all the possible function related to the commands.
     */
    private IDefaultMatchManager defaultMatchManager;

    /**
     * This object contains all the possible function related to the tool cards.
     */
    private IToolCardManager toolCardManager;


    public Bank (){
        this.availableCommands = new EnumMap<>(Commands.class);
        this.commandMap = new EnumMap<>(Commands.class);
    }

    /**
     * This method populate the map matching the command with his method.
     */
    private void populateAvailableCommandMap(){
        availableCommands.put(Commands.CHOOSE_WP, defaultMatchManager::chooseWp);
        availableCommands.put(Commands.PLACEMENT, defaultMatchManager::defaultPlacement);
        /*
        availableCommands.put(Commands.TOOL1, toolCardManager::tool1);
        availableCommands.put(Commands.TOOL2, toolCardManager::tool2);
        availableCommands.put(Commands.TOOL3, toolCardManager::tool3);
        availableCommands.put(Commands.TOOL4, toolCardManager::tool4);
        availableCommands.put(Commands.TOOL5, toolCardManager::tool5);
        availableCommands.put(Commands.TOOL6, toolCardManager::tool6);
        availableCommands.put(Commands.TOOL7, toolCardManager::tool7);
        availableCommands.put(Commands.TOOL8, toolCardManager::tool8);
        availableCommands.put(Commands.TOOL9, toolCardManager::tool9);
        availableCommands.put(Commands.TOOL10, toolCardManager::tool10);
        availableCommands.put(Commands.TOOL11, toolCardManager::tool11);
        availableCommands.put(Commands.TOOL12, toolCardManager::tool12);
        availableCommands.put(Commands.EXTRA_TOOL6, toolCardManager::tool6Extra);
        availableCommands.put(Commands.EXTRA_TOOL11, toolCardManager::tool11Extra);
        */
        availableCommands.put(Commands.OTHER_PLAYERS_MAPS, defaultMatchManager::showAllWp);
        availableCommands.put(Commands.PUBLIC_OBJ_CARDS, defaultMatchManager::showPublicObj);
        availableCommands.put(Commands.PRIVATE_OBJ_CARD, defaultMatchManager::showPrivateObj);
        availableCommands.put(Commands.AVAILABLE_TOOL_CARDS, defaultMatchManager::showTool);
        availableCommands.put(Commands.ROUND_TRACK, defaultMatchManager::showRoundTrack);
        availableCommands.put(Commands.END_TURN, defaultMatchManager::moveToNextTurn);
        availableCommands.put(Commands.LOGOUT, defaultMatchManager::exitGame);
        availableCommands.put(Commands.START_ANOTHER_GAME, defaultMatchManager::newGame);
    }

    /**
     * This method populate the map matching the command with the corresponding command translated.
     */
    private void populateCommandMap(){
        commandMap.put(Commands.CHOOSE_WP, UserCommands.SCELTA_WP);
        commandMap.put(Commands.PLACEMENT, UserCommands.PIAZZAMENTO);
        commandMap.put(Commands.TOOL1, UserCommands.TOOL1);
        commandMap.put(Commands.TOOL2, UserCommands.TOOL2);
        commandMap.put(Commands.TOOL3, UserCommands.TOOL3);
        commandMap.put(Commands.TOOL4, UserCommands.TOOL4);
        commandMap.put(Commands.TOOL5, UserCommands.TOOL5);
        commandMap.put(Commands.TOOL6, UserCommands.TOOL6);
        commandMap.put(Commands.TOOL7, UserCommands.TOOL7);
        commandMap.put(Commands.TOOL8, UserCommands.TOOL8);
        commandMap.put(Commands.TOOL9, UserCommands.TOOL9);
        commandMap.put(Commands.TOOL10, UserCommands.TOOL10);
        commandMap.put(Commands.TOOL11, UserCommands.TOOL11);
        commandMap.put(Commands.TOOL12, UserCommands.TOOL12);
        commandMap.put(Commands.EXTRA_TOOL6, UserCommands.EXTRA_TOOL6);
        commandMap.put(Commands.EXTRA_TOOL11, UserCommands.EXTRA_TOOL11);
        commandMap.put(Commands.OTHER_PLAYERS_MAPS, UserCommands.MAPPE_ALTRI_GIOCATORI);
        commandMap.put(Commands.PUBLIC_OBJ_CARDS, UserCommands.OBBIETTIVI_PUBBLICI);
        commandMap.put(Commands.PRIVATE_OBJ_CARD, UserCommands.OBIETTIVO_PRIVATO);
        commandMap.put(Commands.AVAILABLE_TOOL_CARDS, UserCommands.CARTE_STRUMENTO_DISPONIBILI);
        commandMap.put(Commands.ROUND_TRACK, UserCommands.ROUND_TRACK);
        commandMap.put(Commands.END_TURN, UserCommands.PASSA);
        commandMap.put(Commands.LOGOUT, UserCommands.LOGOUT);
        commandMap.put(Commands.START_ANOTHER_GAME, UserCommands.NUOVA_PARTITA);
    }


    /**
     * This method create a map of commands taking them from the bank
     * @param commands: The list of commands available
     * @return the new map that contains the methods just of the command available.
     */
    public Map<String, Runnable> getCommandMap(List <Commands> commands){
        Map<String, Runnable> function = new LinkedHashMap<>();

        commands.forEach(c -> function.put(commandToStringConverter(c), availableCommands.get(c)));
        function.put(UserCommands.HELP.getDescription(), defaultMatchManager::printCommands);

        return function;
    }

    public Map<Commands, Runnable> getAvailableCommands(List<Commands> commands) {
        Map<Commands, Runnable> map = new LinkedHashMap<>();

        commands.forEach(c -> map.put(c, availableCommands.get(c)));

        return map;
    }

    /**
     * This method convert a command to a string
     * @param command: The command that need to be converted.
     * @return the string of the command.
     */
    private String commandToStringConverter(Commands command){
        return commandMap.get(command).getDescription();
    }

    public void setDefaultMatchManager(IDefaultMatchManager defaultMatchManager) {
        this.defaultMatchManager = defaultMatchManager;
    }

    public void setToolCardManager(IToolCardManager toolCardManager){
        this.toolCardManager = toolCardManager;
    }

    public void populateBank(){
        this.populateAvailableCommandMap();
        this.populateCommandMap();
    }
}

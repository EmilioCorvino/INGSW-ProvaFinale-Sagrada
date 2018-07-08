package it.polimi.ingsw.client.view.cli.boardelements;

import it.polimi.ingsw.common.Commands;

/**
 * This class represents the Tool Cards on the View.
 */
public class ToolCardView {

    /**
     * Description of the Tool Card: it explains what it does.
     */
    private String description;

    /**
     * Cost of the Tool Card, that is the number of Favor Tokens needed to use it.
     */
    private int cost;

    /**
     * Command relative to the Tool Card.
     */
    private Commands command;

    public ToolCardView(String description, int cost, Commands command){
        this.description = description;
        this.cost = cost;
        this.command = command;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public Commands getCommand() {
        return command;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setCommand(Commands command) {
        this.command = command;
    }
}

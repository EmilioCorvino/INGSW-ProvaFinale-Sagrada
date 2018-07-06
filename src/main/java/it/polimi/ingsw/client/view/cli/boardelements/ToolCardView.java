package it.polimi.ingsw.client.view.cli.boardelements;

import it.polimi.ingsw.common.Commands;

public class ToolCardView {

    private String description;

    private int cost;

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

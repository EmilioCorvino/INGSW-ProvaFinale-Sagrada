package it.polimi.ingsw.view.cli.boardElements;

public class ToolCard {

    private String description;

    private int cost;

    public ToolCard(String description, int cost){
        this.description = description;
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}

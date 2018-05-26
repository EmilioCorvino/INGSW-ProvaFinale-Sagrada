package it.polimi.ingsw.view.cli.die;

public enum ViewColor {
    PURPLE(35,"P"),
    BLUE(34,"B"),
    GREEN(36,"G"),
    RED(31,"R"),
    YELLOW(33,"Y");

    private int colorNumber;
    private String id;

    public int getColorNumber() {
        return colorNumber;
    }

    public String getId(){
        return id;
    }

    ViewColor(int colorNumber, String id){
        this.colorNumber = colorNumber;
        this.id = id;
    }
}

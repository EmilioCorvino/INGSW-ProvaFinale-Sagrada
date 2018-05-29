package it.polimi.ingsw.view.cli.die;

import it.polimi.ingsw.model.player.Player;

public class PlayerView {
    private String userName;
    private WindowPatternCardView wp;

    public String getUserName() {
        return userName;
    }

    public WindowPatternCardView getWp() {
        return wp;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWp(WindowPatternCardView wp) {
        this.wp = wp;
    }

    public boolean isSamePlayerAs(Player player) {
        return this.getUserName().equals(player.getPlayerName());
    }
}

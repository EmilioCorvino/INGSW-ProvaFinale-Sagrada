package it.polimi.ingsw.network;

import java.util.*;

/**
 * This enum is used to identify the four different players that can be in a match. It's set in {@link Connection}.
 */
public enum PlayerColor {
    RED,
    PURPLE,
    BLUE,
    GREEN;

    /**
     *
     * @return
     */
    public PlayerColor selectRandomColor() {
        List<PlayerColor> playerColors = new ArrayList<>();
        playerColors.add(RED);
        playerColors.add(PURPLE);
        playerColors.add(BLUE);
        playerColors.add(GREEN);

        Random randomColor = new Random();

        PlayerColor selectedColor = playerColors.get(randomColor.nextInt(playerColors.size()));

        playerColors.remove(selectedColor);

        return selectedColor;
    }
}
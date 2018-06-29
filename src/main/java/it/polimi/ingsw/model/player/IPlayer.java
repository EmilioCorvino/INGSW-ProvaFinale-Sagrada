package it.polimi.ingsw.model.player;

/**
 * Interface representing what each player can do.
 */
public interface IPlayer {

    /**
     * Checks if {@code this} is the same player as the one passed as parameter.
     * @param player to check if is the same as {@code this}.
     * @return {@code true} if it's the same player, {@code false} otherwise.
     */
    boolean isSamePlayerAs(Player player);
}

package it.polimi.ingsw.model.player;


import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.move.IMove;

/**
 * Interface representing what each player can do.
 */
public interface IPlayer {

    /**
     * Peforms an {@link IMove} on the {@link CommonBoard} base on parameters taken from {@link IOController}.
     * @param commonBoard board the player is watching.
     * @param ioController class storing the user input.
     * @param move effective move to perform.
     */
    public void performMove(CommonBoard commonBoard, IOController ioController, IMove move);

    /**
     * Checks if {@code this} is the same player as the one passed as parameter.
     * @param player to check if is the same as {@code this}.
     * @return {@code true} if it's the same player, {@code false} otherwise.
     */
    public boolean isSamePlayerAs(Player player);
}

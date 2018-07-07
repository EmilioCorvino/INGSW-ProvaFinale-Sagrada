package it.polimi.ingsw.common;

/**
 * This enumeration represents all possible commands a player can require from the server. The commands shown to the
 * player on duty are different from the ones shown to the others.
 * Moreover, different commands can be shown in different matches, due to the fact that different
 * {@link it.polimi.ingsw.server.model.cards.tool.ToolCard}s can be drawn.
 *
 * These commands are sent from {@link it.polimi.ingsw.server.controller.managers.AGameManager}s, and their translation
 * is done in the {@link it.polimi.ingsw.client.view.Bank}.
 * @see it.polimi.ingsw.server.controller.managers.StartGameManager
 * @see it.polimi.ingsw.server.controller.managers.GamePlayManager
 * @see it.polimi.ingsw.server.controller.managers.EndGameManager
 * @see it.polimi.ingsw.client.view.Bank
 */
public enum Commands {

    /**
     * Represents the {@link it.polimi.ingsw.server.model.die.containers.WindowPatternCard} choice.
     */
    CHOOSE_WP,

    /**
     * Represents the {@link it.polimi.ingsw.server.model.move.DefaultDiePlacementMove}.
     */
    PLACEMENT,

    /**
     * Represents the usage of the Tool Card 1.
     */
    TOOL1,

    /**
     * Represents the usage of the Tool Card 2.
     */
    TOOL2,

    /**
     * Represents the usage of the Tool Card 3.
     */
    TOOL3,

    /**
     * Represents the usage of the Tool Card 4.
     */
    TOOL4,

    /**
     * Represents the usage of the Tool Card 5.
     */
    TOOL5,

    /**
     * Represents the usage of the Tool Card 6.
     */
    TOOL6,

    /**
     * Represents the usage of the Tool Card 7.
     */
    TOOL7,

    /**
     * Represents the usage of the Tool Card 8.
     */
    TOOL8,

    /**
     * Represents the usage of the Tool Card 9.
     */
    TOOL9,

    /**
     * Represents the usage of the Tool Card 10.
     */
    TOOL10,

    /**
     * Represents the usage of the Tool Card 11.
     */
    TOOL11,

    /**
     * Represents the usage of the Tool Card 12.
     */
    TOOL12,

    /**
     * Represents the extra action needed to use the Tool Card 6.
     */
    EXTRA_TOOL6,

    /**
     * Represents the extra action needed to use the Tool Card 11.
     */
    EXTRA_TOOL11,

    /**
     * Represents the action performed to move to the next turn.
     */
    END_TURN,

    /**
     * Represents the willingness to play another game after the one the player was in is over.
     */
    START_ANOTHER_GAME,

    /**
     * Represents the possibility for a player to reconnect to the game.
     */
    RECONNECT,

    /**
     * Represents the possibility for a player to disconnect from the game.
     */
    LOGOUT,

    /**
     * Represents the possibility for a player to have a look at the game board.
     */
    VISUALIZATION
}
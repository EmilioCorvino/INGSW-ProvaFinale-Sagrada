package it.polimi.ingsw.controller;

/**
 * This enumeration represents all possible commands a player can require from the server. The commands shown to the
 * player on duty are different from the ones shown to the others.
 * Moreover, different commands can be shown in different matches, due to the fact that different
 * {@link it.polimi.ingsw.model.cards.tool.ToolCard}s can be drawn.
 */
public enum Commands {
    CHOOSE_WP,
    PLACEMENT,
    TOOL1,
    TOOL2,
    TOOL3,
    TOOL4,
    TOOL5,
    TOOL6,
    TOOL6_EXTRA,
    TOOL7,
    TOOL8,
    TOOL9,
    TOOL10,
    TOOL11,
    TOOL11_EXTRA,
    TOOL12,
    OTHER_PLAYERS_MAPS,
    PUBLIC_OBJ_CARDS,
    PRIVATE_OBJ_CARD,
    AVAILABLE_TOOL_CARDS,
    ROUND_TRACK,
    END_TURN,
    START_ANOTHER_GAME,
    LOGOUT
}
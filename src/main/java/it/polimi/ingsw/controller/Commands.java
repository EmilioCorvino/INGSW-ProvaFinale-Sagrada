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
    //todo insert specific tool card effects here
    CHOOSE_VALUE_EFFECT,
    ADJIACENT_RESTRICTION_EFFECT,
    OPPOSITE_VALUE_EFFECT,
    VALUE_RESTRICTION_EFFECT,
    COLOR_RESTRICTION_EFFECT,
    PLACEMENT_RESTRICTION_EFFECT,
    SWAP_DRAFTPOOL_ROUNDTRACK,
    SWAP_DRAFTPOOL_DICEBAG,
    COLOR_PLACEMENT_RESTRICTION_EFFECT,
    DRAFT_VALUE_EFFECT,
    OTHER_PLAYERS_MAPS,
    PUBLIC_OBJ_CARDS,
    PRIVATE_OBJ_CARD,
    AVAILABLE_TOOL_CARDS,
    END_TURN,
    LOGOUT
}
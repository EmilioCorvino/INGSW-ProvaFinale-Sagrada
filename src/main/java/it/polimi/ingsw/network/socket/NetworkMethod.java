package it.polimi.ingsw.network.socket;

import java.io.Serializable;

/**
 * This enumeration lists the possible messages that can be exchanged through the network.
 * It is used by socket connections.
 * @see NetworkMessage
 * @see ServerEncoderDecoder
 * @see ClientEncoderDecoder
 */
public enum NetworkMethod implements Serializable {

    //Methods server can call from the client.
    SHOW_ROOM,
    SHOW_WP_TO_CHOOSE,
    REQUIRE_CHOSEN_WP_ID,
    SET_COMMON_BOARD,
    SET_DRAFT_POOL,
    SET_PLAYER,
    SHOW_COMMAND,
    GIVE_OBJ_TO_FILL,
    UPDATE_OWN_WP,
    UPDATE_OTHERS_WP,
    UPDATE_DRAFT_POOL,
    UPDATE_FAVOR_TOKENS,
    SHOW_NOTICE,
    SET_TURN,

    //Methods client can call from the server.
    LOGIN,
    WP_REQUEST,
    DEFAULT_MOVE_REQUEST,
    PERFORM_MOVE_REQUEST,
    EXIT
}

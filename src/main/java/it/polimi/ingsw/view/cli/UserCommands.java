package it.polimi.ingsw.view.cli;

/**
 * This enum map the command available for the user.
 */
public enum UserCommands {

    SCELTA_WP("Scelta della vetrata"),
    PIAZZAMENTO("Piazzamento dado"),
    CARTA_STRUMENTO("Utilizzo carta strumento"),
    VALUE_RESTRICTION_EFFECT(""),
    COLOR_RESTRICTION_EFFECT(""),
    PLACEMENT_RESTRICTION_EFFECT(""),
    SWAP_DIE_EFFECT(""),
    DRAFT_VALUE_EFFECT(""),
    MAPPE_ALTRI_GIOCATORI("Mappe altri giocatori"),
    OBBIETTIVI_PUBBLICI("Obiettivi pubblici"),
    OBIETTIVO_PRIVATO("Obiettivo Privato"),
    CARTE_STRUMENTO_DISPONIBILI("Carte strumento disponibili"),
    LOGOUT(""),
    HELP("Comandi disponibili");

    /**
     * This is the italian description that will be print and digit by the user, during command choosing.
     */
    private String description;

    UserCommands (String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

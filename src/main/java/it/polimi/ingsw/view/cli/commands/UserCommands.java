package it.polimi.ingsw.view.cli.commands;

/**
 * This enum map the command available for the user.
 */
public enum UserCommands {

    SCELTA_WP("Scelta della vetrata"),
    PIAZZAMENTO("Piazzamento dado"),
    TOOL1("Pinza Sgrossatrice"),
    TOOL2("Pennello per Eglomise"),
    TOOL3("Alesatore per lamina di rame"),
    TOOL4("Lathekin"),
    TOOL5("Taglierina circolare"),
    TOOL6("Pennello per Pasta Salda"),
    TOOL7("Martelletto"),
    TOOL8("Tenaglia a rotelle"),
    TOOL9("Riga in sughero"),
    TOOL10("Tampone diamantato"),
    TOOL11("Diluente per pasta salda"),
    TOOL12("Taglierina manuale"),
    MAPPE_ALTRI_GIOCATORI("Mappe altri giocatori"),
    OBBIETTIVI_PUBBLICI("Obiettivi pubblici"),
    OBIETTIVO_PRIVATO("Obiettivo Privato"),
    CARTE_STRUMENTO_DISPONIBILI("Carte strumento"),
    ROUND_TRACK("Tracciato round"),
    PASSA("Passa"),
    HELP("Aiuto"),
    LOGOUT("Logout"),
    NUOVA_PARTITA("Nuova Partita");

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

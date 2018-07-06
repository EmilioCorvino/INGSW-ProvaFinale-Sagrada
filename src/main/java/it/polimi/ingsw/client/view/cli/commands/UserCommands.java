package it.polimi.ingsw.client.view.cli.commands;

/**
 * This enum map the command available for the user.
 */
public enum UserCommands {

    SCELTA_WP("Vetrata","Questo comando permette di scegliere l'id della vetrata desiderata"),
    PIAZZAMENTO("Piazzamento", "Questo comando permette di fare un piazzamento standard di un dado sulla draft"),
    TOOL1("Strumento 1","Pinza Sgrossatrice:\n\tDopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6."),
    TOOL2("Strumento 2","Pennello per Eglomise:\n\tMuovi un qualsiasi dado nella tua vetrata IGNORANDO LE RESTRIZIONI DI COLORE.\n\tDevi rispettare tutte le altre restrizioni di piazzamento."),
    TOOL3("Strumento 3","Alesatore per lamina di rame:\n\tMuovi un qualsiasi dado nella tua vetrata IGNORANDO LE RESTRIZIONI DI VALORE.\n\tDevi rispettare tutte le altre restrizioni di piazzamento."),
    TOOL4("Strumento 4","Lathekin:\n\tMuovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento."),
    TOOL5("Strumento 5","Taglierina circolare:\n\tDopo aver scelto un dado, scambia quel dado con una dado sul Tracciato dei Round."),
    TOOL6("Strumento 6","Pennello per Pasta Salda:\n\tDopo aver scelto un dado, tira nuovamente quel dado.\n\tSe non puoi piazzarlo, riponilo nella Riserva."),
    TOOL7("Strumento 7","Martelletto:\n\tTira nuovamente tutti i dadi della riserva.\n\tQuesta carta può essere usata solo durante il tuo secondo turno, prima di scegliere il secondo dado."),
    TOOL8("Strumento 8","Tenaglia a rotelle:\n\tDopo il tuo primo turno scegli immediatamente un altro dado. Salta il tuo secondo turno in questo round."),
    TOOL9("Strumento 9","Riga in sughero:\n\tDopo aver scelto un dado, piazzalo in una casella che non sia adiacente ad un altro dado.\n\tDevi rispettare tutte le restrizioni di piazzamento."),
    TOOL10("Strumento 10","Tampone diamantato:\n\tDopo aver scelto un dado, giralo sulla faccia opposta.\n\t6 diventa 1, 5 diventa 2, 4 diventa 3 ecc..."),
    TOOL11("Strumento 11","Diluente per pasta salda:\n\tDopo aver scelto un dado, riponilo nel Sacchetto, poi pescane un altro dal Sacchetto.\n\tScegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento."),
    TOOL12("Strumento 12","Taglierina manuale:\n\tMuovi fino a due dadi dello stesso colore di un solo dado sul tracciato dei round.\n\tDevi rispettare tutte le restrizioni di piazzamento."),
    EXTRA_TOOL6("Piazza", "Questo comando permette di piazzare il dado visualizzato sulla mappa"),
    EXTRA_TOOL11("Valore", "Questo comando permette di scegliere il valore del dado che vuoi piazzare"),
    MAPPE_ALTRI_GIOCATORI("Mappe", "Questo comando permette di visualizzare le mappe degli altri giocatori"),
    OBBIETTIVI_PUBBLICI("Pubblici", "Questo commando permette di visualizzare gli obiettivi pubblici"),
    OBIETTIVO_PRIVATO("Privato", "Questo comando permette di visualizzare l'obiettivo privato"),
    CARTE_STRUMENTO_DISPONIBILI("Strumenti", "Questo comando permette di visualizzare le carte strumento disponibili"),
    ROUND_TRACK("Round", "Questo comando permette di visualizzare il tracciato del round, e tutti i dadi presenti su di esso"),
    PASSA("Passa", "Questo comando permette di passare il proprio turno"),
    AIUTO("Aiuto", "Visualizza la descrizione del comando che viene digitato"),
    COMANDI("Comandi", "Questo comando permette di visualizzare i comandi disponibili"),
    LOGOUT("Logout", "Questo comando permette di disconnettersi durante la partita"),
    NUOVA_PARTITA("Nuova Partita", "Questo comando permette di iniziare una nuova partita mantenendo lo stesso username"),
    RECONNECT("Riconnessione", "Questo comando permette di riconettersi dopo esere stati sospesi durante la partita"),
    VISUALIZZAZIONE("Plancia", "Questo comando permette di visualizzare i componenti della plancia");

    /**
     * This is the name of the command.
     */
    private String name;

    /**
     * This is the italian description that will be print and digit by the user, during command choosing.
     */
    private String description;

    UserCommands (String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}

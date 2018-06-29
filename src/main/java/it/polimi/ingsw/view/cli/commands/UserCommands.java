package it.polimi.ingsw.view.cli.commands;

/**
 * This enum map the command available for the user.
 */
public enum UserCommands {

    SCELTA_WP("Vetrata","Questo comando permette di scegliere l'id della vetrata desiderata"),
    PIAZZAMENTO("Piazzamento", "Questo comando permette di fare un piazzamento standard di un dado sulla draft"),
    TOOL1("Strumento 1","Il nome della carta è: Pinza Sgrossatrice (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL2("Strumento 2","Il nome della carta è: Pennello per Eglomise (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL3("Strumento 3","Il nome della carta è: Alesatore per lamina di rame (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL4("Strumento 4","Il nome della carta è: Lathekin (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL5("Strumento 5","Il nome della carta è: Taglierina circolare (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL6("Strumento 6","Il nome della carta è: Pennello per Pasta Salda (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL7("Strumento 7","Il nome della carta è: Martelletto (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL8("Strumento 8","Il nome della carta è: Tenaglia a rotelle (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL9("Strumento 9","Il nome della carta è: Riga in sughero (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL10("Strumento 10","Il nome della carta è: Tampone diamantato (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL11("Strumento 11","Il nome della carta è: Diluente per pasta salda (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
    TOOL12("Strumento 12","Il nome della carta è: Taglierina manuale (Si consiglia di digitare il comando strumenti per conoscere la sua funzione)"),
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
    RECONNECT("Riconnessione", "Questo comando permette di riconettersi dopo esere stati sospesi durante la partita");

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

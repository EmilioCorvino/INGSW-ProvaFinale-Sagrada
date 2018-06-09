package it.polimi.ingsw.view.cli;

import java.util.Scanner;

/**
 * This class is use to menage the input output communication with the user.
 */
public class InputOutputManager {

    public String askInformation(String input){
        this.print(input);
        return this.read();
    }

    public String read(){
       Scanner scan = new Scanner (System.in);
       return scan.next().trim();
    }

    public void print(String string){
        System.out.println(string);
        /*
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(string);
        */
    }

}

package it.polimi.ingsw.view.cli.generalmanagers;

import java.util.Scanner;

/**
 * This class is use to menage the input output communication with the user.
 */
public class InputOutputManager {

    private Scanner scan = new Scanner (System.in);

    /**
     * This method print a string to the user and get the input
     * @param input: the string to print
     * @return: the string read.
     */
    public String askInformation(String input){
        this.print(input);
        return this.read();
    }

    void printCommandQuestion(String s){
        System.out.print(s);
    }
    /**
     * This method take an input from the user.
     * @return: the string read.
     */
    public String read(){
       return scan.nextLine().trim();
    }

    /**
     * This method is used to print a specific string to the user.
     * @param string: the string that need to be printed.
     */
    public void print(String string){
        System.out.println(string);
        /*
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
        AnsiConsole.out.println(string);
        */
    }

    /**
     * This method close the scanner in this class, that is the unique scanner in the entire application.
     */
    public void closeScanner(){
        scan.close();
    }

}

package it.polimi.ingsw.client.view.cli.managers.general;

import java.util.Scanner;

/**
 * This class is use to menage the input output communication with the user.
 */
public class InputOutputManager {

    /**
     * Scanner used to retrieve information from the keyboard.
     */
    private Scanner scan;

    public InputOutputManager(){
        scan = new Scanner (System.in);
    }

    /**
     * This method prints a string to the user and gets the input.
     * @param input the string to print.
     * @return the string read.
     */
    public String askInformation(String input){
        this.print(input);
        return this.read();
    }

    public int askInt(String input){
        boolean validInput;
        int in = 0;
        do{
            try {
                this.print(input);
                validInput = true;
                in = Integer.parseInt(this.read());
            }catch (NumberFormatException e){
                this.print("Errore, devi inserire un numero!");
                validInput = false;
            }
        }while (!validInput);
        return in;
    }

    void printCommandQuestion(){
        System.out.print("> ");
    }
    /**
     * This method take an input from the user.
     * @return the string read.
     */
    String read(){
        return scan.nextLine().trim();
    }

    /**
     * This method is used to print a specific string to the user.
     * @param string the string that need to be printed.
     */
    public void print(String string){
        System.out.println(string);
    }

    /**
     * This method close the scanner in this class, that is the unique scanner in the entire application.
     */
    public void closeScanner(){
        scan.close();
    }

}
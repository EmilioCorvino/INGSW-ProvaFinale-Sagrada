package it.polimi.ingsw.view.cli.managers.general;

import java.util.function.Consumer;

/**
 * This thread is used to read 	continuously the input from the user.
 */
public class ScannerThread extends Thread {

    /**
     * An instance of the input output manager of the cliView.
     */
    private InputOutputManager inputOutputManager;

    /**
     * The function that search in a map the corresponding entry of the string insert by the user.
     */
    private Consumer<String> functionToInvoke;

    /**
     * This attribute identify if the match is active.
     */
    private boolean isOnGame = true;

    public ScannerThread(Consumer<String> functionToInvoke, InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
        this.functionToInvoke = functionToInvoke;
    }

    @Override
    public void run() {
        while (isOnGame){
            inputOutputManager.printCommandQuestion();
            String stuff = inputOutputManager.read();
            if(isOnGame)
                functionToInvoke.accept(stuff);
        }
    }

    /**
     * This method close the thread and the scanner of the input output manager.
     */
    void stopExecution() {
        isOnGame = false;
    }

    public void setEndState() {
        stopExecution();
        this.inputOutputManager.print("Premi [Enter] per terminare il programma...");
    }
}

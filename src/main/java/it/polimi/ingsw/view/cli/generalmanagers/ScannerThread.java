package it.polimi.ingsw.view.cli.generalmanagers;

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
            String stuff = inputOutputManager.read();
            functionToInvoke.accept(stuff);
        }
    }

    /**
     * This method close the thread and the scanner of the input output manager.
     */
    public void stopExecution(){
        inputOutputManager.closeScanner();
        isOnGame = false;
    }
}

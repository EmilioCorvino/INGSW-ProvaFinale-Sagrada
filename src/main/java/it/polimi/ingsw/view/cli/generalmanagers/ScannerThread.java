package it.polimi.ingsw.view.cli.generalmanagers;

import java.util.function.Consumer;

public class ScannerThread extends Thread {
    private InputOutputManager inputOutputManager;
    private Consumer<String> functionToInvoke;
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

    void stopExecution(){
        isOnGame = false;
    }
}

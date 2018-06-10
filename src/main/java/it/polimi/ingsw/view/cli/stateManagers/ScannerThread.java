package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.view.cli.InputOutputManager;

import java.util.function.Consumer;

public class ScannerThread implements Runnable {
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

    public void stopExecution(){
        isOnGame = false;
    }
}

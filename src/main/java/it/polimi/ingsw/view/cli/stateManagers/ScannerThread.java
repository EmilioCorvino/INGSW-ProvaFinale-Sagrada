package it.polimi.ingsw.view.cli.stateManagers;

import it.polimi.ingsw.view.cli.InputOutputManager;

import java.util.Scanner;
import java.util.function.Consumer;

public class ScannerThread implements Runnable {
    private InputOutputManager inputOutputManager;
    private Consumer<String> functionToInvoke;

    public ScannerThread(Consumer<String> functionToInvoke, InputOutputManager inputOutputManager){
        this.inputOutputManager = inputOutputManager;
        this.functionToInvoke = functionToInvoke;
    }

    @Override
    public void run() {
        //SOstituire il true con una variabile
        while (true){
            String stuff = inputOutputManager.read();
            functionToInvoke.accept(stuff);

        }
    }

    public void stopExecution(){
        //Imposta il flag di run a false
    }
}

package it.polimi.ingsw.view.cli.stateManagers;

import java.util.Scanner;
import java.util.function.Consumer;

public class ScannerThread implements Runnable {
    private Scanner scanner;
    private Consumer<String> functionToInvoke;

    public ScannerThread(Consumer<String> functionToInvoke){
        this.scanner = new Scanner(System.in);
        this.functionToInvoke = functionToInvoke;
    }

    @Override
    public void run() {
        //SOstituire il true con una variabile
        while (true){
            String stuff = scanner.next();
            functionToInvoke.accept(stuff);
        }
    }

    public void stopExecution(){
        //Imposta il flag di run a false
    }
}

/*package it.polimi.ingsw.view.cli;

import java.util.Scanner;

public class OutOfTurnManager implements Runnable {

    private CliView view;

    private InputOutputManager inputOutputManager = new InputOutputManager();

    OutOfTurnManager(CliView view){
        this.view = view;
    }

    @Override
    public void run() {

        Thread threadInputs = new Thread(() -> {
            while (!view.isMyTurn()) {
                this.showNotMyTurnCommand();
                int commandChosen = Integer.parseInt(inputOutputManager.read());
                switch (commandChosen) {
                    case 1:
                        view.getGamePlayManager().printAllWp(view.getCommonBoard().getPlayers(), view.getPlayer());
                        break;

                    case 2:
                        view.getGamePlayManager().printPubObj(view.getCommonBoard().getPublicObjectiveCards());
                        break;

                    case 3:
                        view.getGamePlayManager().printTool(view.getCommonBoard().getToolCards());
                        break;

                    case 4:
                        view.getGamePlayManager().printPrivateObj(view.getPlayer().getPrivateObjCard());
                        break;
                    default:
                }
            }
        });

        threadInputs.start();
    }

    private void showNotMyTurnCommand(){
        inputOutputManager.print("\nScegli il comando:" +
                "\n\t 1 - Visualizza mappe altri giocatori" +
                "\n\t 2 - Visualizza obiettivi pubblici\n\t" +
                " 3 - Visualizza carte strumento\n\t 4 - Visualizza obiettivo privato");
    }
}
*/
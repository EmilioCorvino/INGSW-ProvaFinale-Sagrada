package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.player.Player;

/**
 * TODO
 */
public class DiePlacementMove implements IMove {

    private ChooseDieMove chooseDieMove;

    public DiePlacementMove() {

    }

    /**
     * This method performs the placement move in the window pattern card.
     * @param manager the manager.
     * @param setUpInfoUnit the information container in which the coordinates to use are stored.
     */
    @Override
    public void executeMove(GamePlayManager manager, SetUpInformationUnit setUpInfoUnit) {

        Player p = manager.getPlayerColorList().get(manager.getCurrentPlayer());
        WindowPatternCard wp = p.getWindowPatternCard();
        Die die = new Die(setUpInfoUnit.getValue(), setUpInfoUnit.getColor());
        //CAREFUL
        Cell desiredCell = new Cell(setUpInfoUnit.getIndex() / WindowPatternCard.getMaxCol(), setUpInfoUnit.getIndex() % WindowPatternCard.getMaxCol());
        wp.setDesiredCell(desiredCell);

        if (!wp.canBePlaced(die, desiredCell)) {
            if(manager.getCurrentRound() == 1) {
                manager.showNotification("il dado deve essere piazzato sui bordi o in uno degli angoli");
                manager.givePlayerObjectTofill();
                return;
            }
        else {
            manager.showNotification("il dado non può essere piazzato perchè non rispetta le restrizioni");
            manager.givePlayerObjectTofill();
            return;
        }
    }

        wp.update(die);
        manager.getControllerMaster().getCommonBoard().getDraftPool().update(die);
        manager.showPlacementResult(p, setUpInfoUnit);
    }
}


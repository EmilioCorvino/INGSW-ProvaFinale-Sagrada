package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.player.Player;

/**
 *
 */
public class DiePlacementMove implements IMove {

    private ChooseDieMove chooseDieMove;

    public DiePlacementMove() {

    }

    /**
     *
     * @param commonBoard
     * @param ioController
     */
    @Override
    public void executeMove(CommonBoard commonBoard, IOController ioController) {

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
        Cell desiredCell = new Cell(setUpInfoUnit.getIndex() / WindowPatternCard.getMaxCol(), setUpInfoUnit.getIndex() % WindowPatternCard.getMaxCol());

        if (!wp.canBePlaced(die, desiredCell)) {
            //tell the client that the die cannot be placed

            manager.givePlayerObjectTofill();

        }
        wp.update(die);
        manager.getControllerMaster().getCommonBoard().getDraftPool().update(die);
        manager.showPlacementResult(p, setUpInfoUnit);
    }
}


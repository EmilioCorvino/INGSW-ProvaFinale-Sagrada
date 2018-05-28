package it.polimi.ingsw.model.move;

import it.polimi.ingsw.controller.GamePlayManager;
import it.polimi.ingsw.controller.IOController;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.model.CommonBoard;
import it.polimi.ingsw.model.die.Cell;
import it.polimi.ingsw.model.die.Die;
import it.polimi.ingsw.model.die.diecontainers.ADieContainer;
import it.polimi.ingsw.model.die.diecontainers.WindowPatternCard;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.PlayerColor;

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
     * @param currentPlayer
     * @param manager
     * @param informationUnit
     * @param source
     */
    @Override
    public void executeMove(PlayerColor currentPlayer, GamePlayManager manager, SetUpInformationUnit informationUnit, ADieContainer source) {

        Player p = manager.getControllerMaster().getCommonBoard().getPlayerMap().get(currentPlayer);

        WindowPatternCard wp = p.getWindowPatternCard();

        Die die = new Die(informationUnit.getValue(), informationUnit.getColor());

        Cell desiredCell = new Cell(informationUnit.getIndex() / WindowPatternCard.getMaxCol(), informationUnit.getIndex() % WindowPatternCard.getMaxCol());

        if (!wp.canBePlaced(die, desiredCell)) {
            //tell the client that the die cannot be placed
        }

        wp.update(die);
        source.update(die);

    }

}


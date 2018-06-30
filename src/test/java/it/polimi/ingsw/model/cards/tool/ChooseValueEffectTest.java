package it.polimi.ingsw.model.cards.tool;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.tool.valueeffects.AValueEffect;
import it.polimi.ingsw.model.cards.tool.valueeffects.ChooseValueEffect;
import it.polimi.ingsw.model.die.Die;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChooseValueEffectTest {

    /*
    @Test
    public void executeMove() {
        Map<String, Connection> map = new HashMap<>();
        map.put("rita", new Connection("rita"));
        Connection conn = new Connection("rita");
        ControllerMaster controllerMaster = new ControllerMaster(map);
        controllerMaster.getCommonBoard().getDraftPool().populateDiceDraftPool(4);
        ChooseValueEffect chooseValueEffect = new ChooseValueEffect();
        chooseValueEffect.setOffset(1);
        SetUpInformationUnit info = new SetUpInformationUnit();
        info.setSourceIndex(2);
        info.setExtraParam(0);

        Player p = new Player("rita", controllerMaster.getCommonBoard());
        controllerMaster.getCommonBoard().getPlayers().add(p);
        controllerMaster.getGameState().initializePlayerList(controllerMaster.getCommonBoard().getPlayers());

        WindowPatternCard wp = controllerMaster.getCommonBoard().getWindowPatternCardDeck().getAvailableWP().get(2);
        p.setWindowPatternCard(wp);

        chooseValueEffect.executeMove((GamePlayManager)controllerMaster.getGamePlayManager(), info);

        DiceDraftPool draftPool = controllerMaster.getCommonBoard().getDraftPool();
        Die die = draftPool.getAvailableDice().get(info.getSourceIndex());

       assertEquals(die.getOriginalDieValue() + 1, die.getActualDieValue());

    }
*/
    @Test
    public void increaseDieValue() {

        Die die = new Die(4, Color.BLUE);
        AValueEffect toolEffect = new ChooseValueEffect();
        assertEquals(5, ((ChooseValueEffect) toolEffect).increaseDieValue(die).getActualDieValue() );

        Die die1 = new Die(4, Color.PURPLE);
        AValueEffect toolEffect1 = new ChooseValueEffect(1);
        assertEquals(5, ((ChooseValueEffect) toolEffect1).increaseDieValue(die1).getActualDieValue());


        Die die2 = new Die(4, Color.YELLOW);
        AValueEffect toolEffect2 = new ChooseValueEffect(1);

        for(int i=0; i<2; i++)
            ((ChooseValueEffect) toolEffect2).increaseDieValue(die2);

        assertEquals(5, die2.getActualDieValue());

        Die die3 = new Die(6, Color.GREEN);
        AValueEffect toolEffect3 = new ChooseValueEffect(1);
        assertEquals(6, ((ChooseValueEffect) toolEffect3).increaseDieValue(die3).getActualDieValue());
    }

    @Test
    public void decreaseDieValueEffect() {

        Die die = new Die(6, Color.PURPLE);
        AValueEffect effect = new ChooseValueEffect();
        assertEquals(5, ((ChooseValueEffect) effect).decreaseDieValue(die).getActualDieValue());

        Die die1 = new Die(2, Color.GREEN);
        AValueEffect effect1 = new ChooseValueEffect(1);

        for(int i=0; i<3; i++)
            ((ChooseValueEffect) effect1).decreaseDieValue(die1);

        assertEquals(1, ((ChooseValueEffect) effect1).decreaseDieValue(die1).getActualDieValue());
    }
}
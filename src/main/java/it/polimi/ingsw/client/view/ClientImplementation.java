package it.polimi.ingsw.client.view;

import it.polimi.ingsw.common.Commands;
import it.polimi.ingsw.common.network.IFromServerToClient;
import it.polimi.ingsw.common.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.common.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.common.utils.PropertyLoader;
import it.polimi.ingsw.common.utils.SagradaLogger;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 * This class handles messages arriving from the server, calling methods from {@link IViewMaster}.
 */
public class ClientImplementation implements IFromServerToClient {

    /**
     * Task associated with the timer
     */
    private TimerTask task;

    /**
     * This is the timer that starts at the last show notice sent by the serve.
     * If it expires, the player will be disconnected, because it means that the server is death.
     */
    private final Timer serverTimer = new Timer();

    /**
     * Maximum amount of time server has to respond.
     */
    private long timeOut;

    /**
     * Timer to use in case the loading from file fails. Value is in milliseconds.
     */
    private static final long BACK_UP_TIMER = 90000;

    /**
     * View chosen by the user.
     */
    private IViewMaster view;

    public ClientImplementation(IViewMaster view) {
        this.view = view;

        //Back up value.
        timeOut = BACK_UP_TIMER;

        //Value read from file. If the loading is successful, it overwrites the back up.
        if (PropertyLoader.getPropertyLoader().getTurnTimer() != 0) {
            timeOut = PropertyLoader.getPropertyLoader().getTurnTimer()*2;
        }
    }

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     */
    @Override
    public void showRoom(List<String> players) {
        view.showRoom(players);
        startTimer();
    }

    @Override
    public void showPrivateObjective(int idPrivateObj){
        view.showPrivateObjective(idPrivateObj);
    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) {
        view.showMapsToChoose(listWp);
    }

    @Override
    public void setCommonBoard(Map<String,SimplifiedWindowPatternCard> players, int [] idPubObj, int[] idTool){
        view.setCommonBoard(players,idPubObj, idTool);
    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft){
        view.setDraft(draft);
    }

    @Override
    public void setFavorToken(int nFavTokens){
        view.setFavorToken(nFavTokens);
    }

    @Override
    public void setRestoredWindowPatternCards(Map<String, List<SetUpInformationUnit>> diceToRestore) {
        view.setRestoredWindowPatternCards(diceToRestore);
    }

    @Override
    public void showCommand(List<Commands> commands) {
        view.showCommand(commands);
    }

    @Override
    public void addOnOwnWp(SetUpInformationUnit unit){
        view.addOnOwnWp(unit);
    }

    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit){
        view.removeOnOwnWp(unit);
    }

    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit){
        view.addOnOtherPlayerWp(userName,infoUnit);
    }

    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit){
        view.removeOnOtherPlayerWp(userName, infoUnit);
    }

    @Override
    public void addOnDraft(SetUpInformationUnit info){
        view.addOnDraft(info);
    }

    @Override
    public void removeOnDraft(SetUpInformationUnit info){
        view.removeOnDraft(info);
    }

    @Override
    public void addOnRoundTrack(SetUpInformationUnit info){
        view.addOnRoundTrack(info);
    }

    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info){
        view.removeOnRoundTrack(info);
    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken){
        view.updateFavTokenPlayer(nFavorToken);
    }

    @Override
    public void updateToolCost(int idSlot, int cost){
        view.updateToolCost(idSlot,cost);
    }

    @Override
    public void showDie(SetUpInformationUnit informationUnit){
        view.showDie(informationUnit);}

    @Override
    public void showRank(String[] playerNames, int[] scores) {
        view.showRank(playerNames, scores);
    }

    @Override
    public void forceLogOut() {
        view.setServer(null);
        view.forceLogOut();
    }

    @Override
    public void showNotice(String notice) {
        view.showNotice(notice);
        startTimer();

    }

    private void startTimer(){
        if (task != null) {
            task.cancel();
            task = null;
        }

        task = new TimerTask() {
            @Override
            public void run() {
                    SagradaLogger.log(Level.SEVERE, "Server timeout");
                    forceLogOut();
                }
        };
        serverTimer.schedule(task, timeOut);
    }

}

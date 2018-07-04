package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.SagradaLogger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
     * Path of the file containing the maximum amount of time available for players to make a choice.
     */
    private static final String TIMER_FILE = "./src/main/resources/config/turnTimer";

    /**
     * View chosen by the user.
     */
    private IViewMaster view;

    public ClientImplementation(IViewMaster view) {
        this.view = view;

        //Back up value.
        timeOut = BACK_UP_TIMER;

        //Value read from file. If the loading is successful, it overwrites the back up.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(TIMER_FILE)))) {
            timeOut = Long.parseLong(reader.readLine())*2;
        } catch (IOException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to load the server timer from file", e);
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
                    view.forceLogOut();
                }
        };
        serverTimer.schedule(task, timeOut);
    }

}

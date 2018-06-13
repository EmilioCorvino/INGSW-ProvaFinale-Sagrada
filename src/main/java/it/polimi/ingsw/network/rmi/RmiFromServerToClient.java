package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplified_view.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplified_view.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;
import it.polimi.ingsw.utils.logs.SagradaLogger;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * This class represents the client on the server side. Controller can call view's methods
 * through this class using RMI protocol.
 */
public class RmiFromServerToClient implements IFromServerToClient {

    /**
     * Client's remote interface. This object can call remotely {@link IRmiClient} methods.
     */
    private IRmiClient rmiClient;

    RmiFromServerToClient(IRmiClient rmiClient) {
        this.rmiClient = rmiClient;
    }

    /**
     * Shows the waiting room to the player owning the client.
     * @param players names of the players already connected (including the player itself).
     * @throws BrokenConnectionException when the connection drops.
     */
    @Override
    public void showRoom(List<String> players) throws BrokenConnectionException {
        try {
            this.rmiClient.showRoom(players);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the updated room", e);
            throw new BrokenConnectionException();
        }
    }

    /**
     *
     *
     * @param listWp:
     * @throws BrokenConnectionException
     */
    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws
            BrokenConnectionException {
        try {
            this.rmiClient.showMapsToChoose(listWp);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the window pattern cards", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void choseWpId() throws BrokenConnectionException {
        try {
            this.rmiClient.choseWpId();
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show make the client choose the wp id", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void showPrivateObjective(int idPrivateObjCard) throws BrokenConnectionException {
        try {
            this.rmiClient.showPrivateObjective(idPrivateObjCard);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients their private objective card", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) throws BrokenConnectionException {
        try {
            this.rmiClient.setCommonBoard(players, idPubObj, idTool);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the initialized board", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft) throws BrokenConnectionException {
        try {
            this.rmiClient.setDraft(draft);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the initialized draft pool", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void setFavorToken(int nFavTokens) throws BrokenConnectionException {
        try {
            this.rmiClient.setFavorToken(nFavTokens);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to correctly set the player", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void showCommand(List<Commands> commands) throws BrokenConnectionException {
        try {
            this.rmiClient.showCommand(commands);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the available commands", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void showUpdatedWp(String username, SetUpInformationUnit info) throws BrokenConnectionException {
        try {
            this.rmiClient.showUpdatedWp(username, info);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show to the client the updated wp", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void addOnOwnWp(SetUpInformationUnit unit) throws BrokenConnectionException {
        try {
            this.rmiClient.addOnOwnWp(unit);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to addDie the window pattern card of the player", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit) throws BrokenConnectionException {

    }

    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException {
        try {
            this.rmiClient.addOnOtherPlayerWp(userName, infoUnit);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to addDie the window pattern cards of the" +
                    " other players", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException {

    }

    @Override
    public void addOnDraft(SetUpInformationUnit info) throws BrokenConnectionException {

    }

    @Override
    public void removeOnDraft(SetUpInformationUnit info) throws BrokenConnectionException {
        try {
            this.rmiClient.removeOnDraft(info);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to addDie draft pool", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void addOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException {

    }

    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException {

    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken) throws BrokenConnectionException {
        try {
            this.rmiClient.updateFavTokenPlayer(nFavorToken);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to addDie the facor tokens owned by the player", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void updateToolCost(int idSlot, int cost) throws BrokenConnectionException {

    }

    @Override
    public void showNotice(String notice) throws BrokenConnectionException {
        try {
            this.rmiClient.showNotice(notice);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show to the client the instruction message", e);
            throw new BrokenConnectionException();
        }
    }


}

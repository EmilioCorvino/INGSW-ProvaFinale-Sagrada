package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.simplified_view.InformationUnit;
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
     * @param listWp
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
    public void showCommonBoard(List<SetUpInformationUnit> draftPool, SimplifiedWindowPatternCard wp) throws
            BrokenConnectionException {
        try {
            this.rmiClient.showCommonBoard(draftPool, wp);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the initialized board", e);
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
    public void setPlayer(String userName, int nFavTokens, int idPrivateObj) throws BrokenConnectionException {
        try {
            this.rmiClient.setPlayer(userName, nFavTokens, idPrivateObj);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to correctly set the player", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void showCommand() throws BrokenConnectionException {
        try {
            this.rmiClient.showCommand();
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the available commands", e);
            throw new BrokenConnectionException();
        }
    }

    /**
     *
     * @param setInfoUnit
     * @throws BrokenConnectionException
     */
    @Override
    public void giveProperObjectToFill(SetUpInformationUnit setInfoUnit) throws BrokenConnectionException {
        try {
            this.rmiClient.giveProperObjectToFill(setInfoUnit);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show to the client the proper object to fill", e);
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
    public void updateOwnWp(String userName, SetUpInformationUnit unit) throws BrokenConnectionException {
        try {
            this.rmiClient.updateOwnWp(userName, unit);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to update the window pattern card of the player", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void updateAllWp(Map<String, SetUpInformationUnit> allWp) throws BrokenConnectionException {
        try {
            this.rmiClient.updateAllWp(allWp);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to update the window pattern cards of the" +
                    " other players", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void updateDraft(InformationUnit info) throws BrokenConnectionException {
        try {
            this.rmiClient.updateDraft(info);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to update draft pool", e);
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void updateFavTokenPlayer(String userName, int nFavorToken) throws BrokenConnectionException {
        try {
            this.rmiClient.updateFavTokenPlayer(userName, nFavorToken);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to update the facor tokens owned by the player", e);
            throw new BrokenConnectionException();
        }
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

    @Override
    public void setMyTurn(boolean myTurn) throws BrokenConnectionException {
        try {
            this.rmiClient.setMyTurn(myTurn);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to say to the player if it's or it's not his turn", e);
            throw new BrokenConnectionException();
        }
    }

}

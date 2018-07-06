package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.controller.Commands;
import it.polimi.ingsw.controller.simplifiedview.SetUpInformationUnit;
import it.polimi.ingsw.controller.simplifiedview.SimplifiedWindowPatternCard;
import it.polimi.ingsw.network.IFromServerToClient;
import it.polimi.ingsw.utils.SagradaLogger;
import it.polimi.ingsw.utils.exceptions.BrokenConnectionException;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * This class represents the client on the server side. Controller can call view's methods
 * through this class using RMI protocol.
 * Methods in this class catch {@link RemoteException} by throwing a new {@link BrokenConnectionException}, so that
 * disconnection handling can be unified both for RMI and socket connections.
 * For methods documentation:
 * @see IFromServerToClient
 * @see it.polimi.ingsw.view.ClientImplementation
 */
public class RmiFromServerToClient implements IFromServerToClient {

    /**
     * Client's remote interface. This object can call remotely {@link IRmiClient} methods.
     */
    private IRmiClient rmiClient;

    RmiFromServerToClient(IRmiClient rmiClient) {
        this.rmiClient = rmiClient;
    }

    @Override
    public void showRoom(List<String> players) throws BrokenConnectionException {
        try {
            this.rmiClient.showRoom(players);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the updated room");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void showMapsToChoose(List<SimplifiedWindowPatternCard> listWp) throws
            BrokenConnectionException {
        try {
            this.rmiClient.showMapsToChoose(listWp);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the window pattern cards");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void showPrivateObjective(int idPrivateObjCard) throws BrokenConnectionException {
        try {
            this.rmiClient.showPrivateObjective(idPrivateObjCard);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients their private objective card");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void setCommonBoard(Map<String, SimplifiedWindowPatternCard> players, int[] idPubObj, int[] idTool) throws BrokenConnectionException {
        try {
            this.rmiClient.setCommonBoard(players, idPubObj, idTool);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the initialized board");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void setDraft(List<SetUpInformationUnit> draft) throws BrokenConnectionException {
        try {
            this.rmiClient.setDraft(draft);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the initialized draft pool");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void setFavorToken(int nFavTokens) throws BrokenConnectionException {
        try {
            this.rmiClient.setFavorToken(nFavTokens);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to correctly set the favor tokens to the player");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void setRestoredWindowPatternCards(Map<String, List<SetUpInformationUnit>> diceToRestore) throws BrokenConnectionException {
        try {
            this.rmiClient.setRestoredWindowPatternCards(diceToRestore);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to restore the Window Pattern Cards to the player");
        }
    }

    @Override
    public void showCommand(List<Commands> commands) throws BrokenConnectionException {
        try {
            this.rmiClient.showCommand(commands);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show clients the available commands");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void addOnOwnWp(SetUpInformationUnit unit) throws BrokenConnectionException {
        try {
            this.rmiClient.addOnOwnWp(unit);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to add a die the window pattern card of the player");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void removeOnOwnWp(SetUpInformationUnit unit) throws BrokenConnectionException {
        try {
            this.rmiClient.removeOnOwnWp(unit);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to remove a die from the window pattern card of " +
                    "the player");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void addOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException {
        try {
            this.rmiClient.addOnOtherPlayerWp(userName, infoUnit);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to add a die to the window pattern cards of " +
                    "other players");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void removeOnOtherPlayerWp(String userName, SetUpInformationUnit infoUnit) throws BrokenConnectionException {
        try {
            this.rmiClient.removeOnOtherPlayerWp(userName, infoUnit);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to remove a die from the window pattern card of " +
                    "other players");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void addOnDraft(SetUpInformationUnit info) throws BrokenConnectionException {
        try {
            this.rmiClient.addOnDraft(info);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to add a die to draft pool");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void removeOnDraft(SetUpInformationUnit info) throws BrokenConnectionException {
        try {
            this.rmiClient.removeOnDraft(info);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to remove a die from draft pool");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void addOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException {
        try {
            this.rmiClient.addOnRoundTrack(info);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to add a die to round track");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void removeOnRoundTrack(SetUpInformationUnit info) throws BrokenConnectionException {
        try {
            this.rmiClient.removeOnRoundTrack(info);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to remove a die from round track");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void updateFavTokenPlayer(int nFavorToken) throws BrokenConnectionException {
        try {
            this.rmiClient.updateFavTokenPlayer(nFavorToken);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to update the favor tokens owned by the player");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void updateToolCost(int idSlot, int cost) throws BrokenConnectionException {
        try {
            this.rmiClient.updateToolCost(idSlot, cost);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to update the cost of a tool card");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void showDie(SetUpInformationUnit informationUnit) throws BrokenConnectionException {
        try {
            this.rmiClient.showDie(informationUnit);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show the updated die to the client");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void showRank(String[] playerNames, int[] scores) throws BrokenConnectionException {
        try {
            this.rmiClient.showRank(playerNames, scores);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show the rank");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void showNotice(String notice) throws BrokenConnectionException {
        try {
            this.rmiClient.showNotice(notice);
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to show to the client the instruction message");
            throw new BrokenConnectionException();
        }
    }

    @Override
    public void forceLogOut() throws BrokenConnectionException {
        try {
            this.rmiClient.forceLogOut();
        } catch (RemoteException e) {
            SagradaLogger.log(Level.SEVERE, "Impossible to force the client to log out");
            throw new BrokenConnectionException();
        }
    }
}

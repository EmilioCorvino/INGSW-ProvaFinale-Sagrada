package it.polimi.ingsw.common.network.socket;

import java.io.Serializable;
import java.util.List;

/**
 * This class represent the messages sent through socket protocol.
 */
public class NetworkMessage implements Serializable {

    /**
     * Parameters the machine on the other end of the connection will use.
     */
    private List<Serializable> parameters;

    /**
     * Enum value representing the method the sender wants to call from the machine on the other end.
     */
    private NetworkMethod method;

    NetworkMessage(NetworkMethod method, List<Serializable> parameters) {
        this.method = method;
        this.parameters = parameters;
    }

    NetworkMethod getMethod() {
        return method;
    }

    List<Serializable> getParameters() {
        return parameters;
    }
}

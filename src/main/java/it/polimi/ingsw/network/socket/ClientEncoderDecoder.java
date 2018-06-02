package it.polimi.ingsw.network.socket;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ClientEncoderDecoder {

    private ServerEncoderDecoder serverEncoderDecoder;

    private Map<ClientMethod, Consumer<List<Serializable>>> clientMethods;

    /*public NetworkMessage encodeMessage(ServerMethod serverMethod, List<Serializable> parameters) {

    }*/

    /*public Consumer<List<Serializable>> decodeMessage(NetworkMessage message) {

    }*/

    private void sendMessage(NetworkMessage message) {

    }
}

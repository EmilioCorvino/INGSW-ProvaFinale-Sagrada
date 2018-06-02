package it.polimi.ingsw.network.socket;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ServerEncoderDecoder {

    private ClientEncoderDecoder clientEncoderDecoder;

    private Map<ServerMethod, Consumer<List<Serializable>>> serverMethods;

    /*public NetworkMessage encodeMessage(ClientMethod clientMethod, List<Serializable> parameters) {

    }*/

    /*public Consumer<List<Serializable>> decodeMessage(NetworkMessage message) {

    }*/

    private void sendMessage(NetworkMessage message) {

    }
}

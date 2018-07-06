package it.polimi.ingsw.common.network.socket;

import it.polimi.ingsw.server.controller.ServerImplementation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class is the server side encoder-decoder: it can encode messages containing {@link NetworkMethod}s and send them
 * to {@link ClientEncoderDecoder}. Moreover, it can decode messages coming from {@link ClientEncoderDecoder}, through
 * {@link #serverMethods} map and call the corresponding methods from {@link ServerImplementation}.
 */
public class ServerEncoderDecoder {
    /**
     * Server this encoder-decoder is referring to.
     */
    private ServerImplementation serverImplementation;

    /**
     * It's the object that encodes and decodes messages on the client side.
     */
    private ClientEncoderDecoder clientEncoderDecoder;

    /**
     * This map acts as a dictionary for decoding messages coming from {@link ClientEncoderDecoder}.
     */
    private Map<NetworkMethod, Consumer<List<Serializable>>> serverMethods;

    NetworkMessage encodeMessage(NetworkMethod method, List<Serializable> parameters) {
        return new NetworkMessage(method, parameters);
    }

    void sendMessage(NetworkMessage message) {
        clientEncoderDecoder.decodeMessage(message);
    }

    void decodeMessage(NetworkMessage message) {

    }
}

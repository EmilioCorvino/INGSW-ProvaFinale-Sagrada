package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.view.ClientImplementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class is the client side encoder-decoder: it can encode messages containing {@link NetworkMethod}s and send them
 * to {@link ServerEncoderDecoder}. Moreover, it can decode messages coming from {@link ServerEncoderDecoder}, through
 * {@link #clientMethods} map and call the corresponding methods from {@link ClientImplementation}.
 */
public class ClientEncoderDecoder {

    /**
     * Client this encoder-decoder is referring to.
     */
    private ClientImplementation clientImplementation;

    /**
     * It's the object that encodes and decodes messages on the server side.
     */
    private ServerEncoderDecoder serverEncoderDecoder;

    /**
     * This map acts as a dictionary for decoding messages coming from {@link ServerEncoderDecoder}.
     */
    private Map<NetworkMethod, Consumer<ArrayList<? extends Serializable>>> clientMethods;

    public ClientEncoderDecoder(ClientImplementation clientImplementation, ServerEncoderDecoder serverEncoderDecoder) {
        this.clientImplementation = clientImplementation;
        this.serverEncoderDecoder = serverEncoderDecoder;
        this.buildDictionary();
    }

    NetworkMessage encodeMessage(NetworkMethod method, List<Serializable> parameters) {
        return new NetworkMessage(method, parameters);
    }

    void sendMessage(NetworkMessage message) {
        serverEncoderDecoder.decodeMessage(message);
        //todo insert waiting logic here?
    }


    Consumer decodeMessage(NetworkMessage message) {
        NetworkMethod networkMethod = message.getMethod();
        return clientMethods.get(networkMethod);
    }

    private void buildDictionary() {
        this.clientMethods = new EnumMap<>(NetworkMethod.class);
        this.clientMethods.put(NetworkMethod.SHOW_ROOM, (ArrayList<String> names) -> clientImplementation.showRoom(names));

    }
}

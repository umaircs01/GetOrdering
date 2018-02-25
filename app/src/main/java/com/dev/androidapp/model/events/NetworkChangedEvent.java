package com.dev.androidapp.model.events;

/**
 * Created by Experiments on 05-Mar-17.
 */

public class NetworkChangedEvent {

    private final boolean isConnected;

    public NetworkChangedEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public String toString() {
        return "NetworkChangedEvent{" +
                "isConnected=" + isConnected +
                '}';
    }
}

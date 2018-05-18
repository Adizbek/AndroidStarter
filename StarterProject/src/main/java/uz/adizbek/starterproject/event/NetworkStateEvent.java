package uz.adizbek.starterproject.event;

public class NetworkStateEvent {
    public boolean connected;

    public NetworkStateEvent(boolean connected) {
        this.connected = connected;
    }
}

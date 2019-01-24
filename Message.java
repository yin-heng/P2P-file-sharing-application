
import java.io.Serializable;

public abstract class Message implements Serializable {
    protected byte[] msBytes;

    public Message() {
    }

    public Message(byte[] ms) {
        this.msBytes = ms;
    }

    public byte[] getMessageBytes() {
        return msBytes;
    }

}

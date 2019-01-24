import java.io.IOException;

/* This is the parent class of messages expect handshake message. They have some common features so that they can
interit this calass.*/

public class ActualMessage extends Message {
    protected int length;
    protected MessageType msType = null;

    public ActualMessage(MessageType msType) throws IOException {
        this.length = 1;
        this.msType = msType;
    }

    public ActualMessage(int length, MessageType msType) throws IOException {
        this.msType = msType;
        this.length = length;
    }

    public int getMsgLength() {
        return length;
    }

    public void setMsgLength(int length) {
        this.length = length;
    }

    public void setMsgType(MessageType msType) {
        this.msType = msType;
    }

    public MessageType getMsgType() {
        return msType;
    }

    public int getMessageLength() {
        return this.length;
    }

}

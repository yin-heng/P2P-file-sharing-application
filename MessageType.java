import java.io.Serializable;

public enum MessageType implements Serializable {
    choke(0),
    unchoke(1),
    interested(2),
    notinterested(3),
    have(4),
    bitfield(5),
    request(6),
    piece(7);

    private byte msType;

    private MessageType(int msType) {
        this.msType = (byte) msType;
    }

    public static MessageType getMessageType(byte value) {
        if(value==0)return MessageType.choke;
        else if(value==1) return MessageType.unchoke;
        else if(value==2) return MessageType.interested;
        else if(value==3) return MessageType.notinterested;
        else if(value==4) return MessageType.have;
        else if(value==5) return MessageType.bitfield;
        else if(value==6) return MessageType.request;
        else if(value==7) return MessageType.piece;
        else  return null;
    }

    public byte getMessageType() {
        return this.msType;
    }
}
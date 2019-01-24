
import java.io.ByteArrayOutputStream;
import java.io.IOException;
// This calss is to construct the peice message.

public class PieceMessage extends ActualMessage {
    private int index;
    private byte[] payload;

    public PieceMessage(int index, byte[] payload) throws IOException {
        super(5 + payload.length, MessageType.piece);
        this.index = index;
        this.payload = payload;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(intToByteArray(this.length));
        out.write(this.msType.getMessageType());
        out.write(intToByteArray(this.index));
        out.write(this.payload);
        this.msBytes = out.toByteArray();
        out.close();
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public byte[] getContent() {
        return this.payload;
    }

    public void setContent(byte[] content) {
        this.payload = payload;
    }
    public static byte[] intToByteArray(int a) {  
        return new byte[] {  
            (byte) ((a >> 24) & 0xFF),  
            (byte) ((a >> 16) & 0xFF),     
            (byte) ((a >> 8) & 0xFF),     
            (byte) (a & 0xFF)  
        };  
    } 

}


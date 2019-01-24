
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//This class is to construct the HaveMessage. 

public class HaveMessage extends ActualMessage {
    private int index;

    public HaveMessage(int index) throws IOException {
        super(5, MessageType.have);
        this.index = index;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(intToByteArray(this.length));
        out.write(this.msType.getMessageType());
        out.write(intToByteArray(this.index));
        this.msBytes = out.toByteArray();
        out.close();
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
// This class is to construct the Interested Message.

public class InterestedMessage extends ActualMessage {

    public InterestedMessage() throws IOException {
        super(1, MessageType.interested);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(intToByteArray(this.length));
        out.write(this.msType.getMessageType());
        this.msBytes = out.toByteArray();
        out.close();
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

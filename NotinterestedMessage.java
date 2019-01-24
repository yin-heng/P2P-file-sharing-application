import java.io.ByteArrayOutputStream;
import java.io.IOException;

//This class is to construct the notinterested message.

public class NotinterestedMessage extends ActualMessage{

    public NotinterestedMessage() throws IOException {
        super(1, MessageType.notinterested);
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

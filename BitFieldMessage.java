import java.io.ByteArrayOutputStream;
import java.io.IOException;
//This class is to define the format of bitfield message. 
//The most important thing is hotw to use bitfield to construct a bitfield message.
public class BitFieldMessage extends ActualMessage {
    private byte[] bit_field;

    public BitFieldMessage(byte[] bit_field) throws IOException {
        super(1 + bit_field.length, MessageType.bitfield);// Message length equals teh sum of 1 bit for message type and the length of message payload.
        this.bit_field = bit_field;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] s1 = intToByteArray(this.length);
        out.write(s1);
        out.write(this.msType.getMessageType());
        out.write(this.bit_field);
        this.msBytes = out.toByteArray();
        out.close();
    }

    public byte[] getBitfield() {
        return bit_field;
    }

    public void setBitfield(byte[] bit_field) {
        this.bit_field = bit_field;
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

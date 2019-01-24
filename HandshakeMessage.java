import java.io.ByteArrayOutputStream;
import java.io.IOException;

//This class is to construct the HandShake message.
public class HandshakeMessage extends Message {
    private static String header = "P2PFILESHARINGPROJ";
    private static byte[] zero;
    private int peerID;

    public HandshakeMessage(int peer_ID) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(header.getBytes());
        zero = new byte[10];
        for(int i = 0; i <10; i++){
            zero[i] = 0;
        }
        out.write(this.zero);  //10 bytes zero bits
        peerID = peer_ID;
        out.write(intToByteArray(peerID));
        this.msBytes = out.toByteArray();
        out.close();
    }

    public int getPeerID() {
        return this.peerID;
    }

    public void setPeerID(int peer_ID) {
        peerID = peer_ID;
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

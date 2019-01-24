import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This class is to deal with bit field such as set bit and generate bit field message.
public class Bitfield {
// 
    private int length;
    private List<Integer> bits;
    private boolean hasCompleteFile;

    public Bitfield(int length, Boolean hasfile) {
    // This is used at the beignning of the program when we need to check which peers have file and the other do not have.
        this.length = length;
        bits = new ArrayList<Integer>(length);
        outputInfo();
        for (int i = 0; i < this.length; i++) {
            if (hasfile) {
                bits.add(i, 1);
                this.hasCompleteFile = true;
            } else {
                bits.add(i, 0);
                this.hasCompleteFile = false;
            }
        }
    }

    public Bitfield(int length, byte[] bitfieldBuffer) {
        // This is to construct peer's bit field while sharing the file.
        this.length = length;
        bits = new ArrayList<Integer>(length);
        getInfo();
        for (int i = 0; i < this.length; i++) {
            if (byteToInt(bitfieldBuffer[0]) != 0) {
                bits.add(i, 1);
                this.hasCompleteFile = true;
            } else {
                bits.add(i, 0);
                this.hasCompleteFile = false;
            }
        }
        System.out.println(bits.size());
    }

   private void getInfo() {
        boolean a = true;
        if (!a) {
            System.out.println("Get some info");
        }
    }

    private void outputInfo() {
        boolean a = true;
        if (!a) {
            System.out.println("Put some info");
        }
    }

    public int getLength() {
        return length;
    }

    synchronized public int getBit(int index) {
        return bits.get(index);
    }

    synchronized public void setBit(int index, int value) {
        // This is used to chage bit in the bitfiled when recieve have message and piece message.
        bits.set(index, value);
        //Each time we need to check whether have complete file.
        if (this.hasCompleteFile == true) {
            return;
        } else {
            this.hasCompleteFile = true;
            for (int i = 0; i < bits.size(); i++) {
                if (bits.get(i) == 0)
                    this.hasCompleteFile = false;
            }
        }
    }

    synchronized public boolean isHasCompleteFile() {
        return this.hasCompleteFile;
    }

    synchronized public int getPieceNum() {
        // To get how many pieces currently have.
        int pieceNum = 0;
        for (int i = 0; i < this.length; i++) {
            if (bits.get(i) == 1)
                pieceNum++;
        }
        return pieceNum;

    }

    synchronized public Message genBitFieldMessage() throws IOException {
        int size = this.length;
        int messagelength = this.length / 8;
        int leftbits = this.length % 8;
        byte[] data;

        if (leftbits == 0) {
            data = new byte[messagelength];
        } else {
            data = new byte[messagelength + 1];
        }

        for (int i = 0; i < messagelength; i++) {
            int value = 0;
            for (int j = 0; j < 8; j++) {
                value = value * 2;
                value = value + bits.get(i * 8 + j);
            }
            data[i] = intToByte(value);
        }
        System.out.println(messagelength);
        if (leftbits != 0) {
            int value = 0;
            for (int j = 0; j < leftbits; j++) {
                value = value * 2;
                System.out.println(j);
                value = value + bits.get(messagelength * 8 + j);
            }
            for (int j = 0; j < 8 - leftbits; j++) {
                value = value * 2;
            }
            data[messagelength] = intToByte(value);
        }
        return new BitFieldMessage(data);
    }
    public static int byteArrayToInt(byte[] b) {  
        return   b[3] & 0xFF |  
                (b[2] & 0xFF) << 8 |  
                (b[1] & 0xFF) << 16 |  
                (b[0] & 0xFF) << 24;  
    } 
    public static byte intToByte(int a){
        return (byte) a;
    } 
    public static int byteToInt(byte b){
        return b & 0xFF;
    }


}

import java.io.*;

//This class is used to deal with file such as writing content to specific piece and read specific piece.
public class FileManager {
    private int ID;
    private File myFile;
    private int FileSize;
    private int PieceSize;
    private File myDirectory;

    public FileManager(int id, String filename, int SizeofFile, int SizeofPiece) throws FileNotFoundException {
        this.setID(id);//peer id
        this.FileSize = SizeofFile;
        this.PieceSize = SizeofPiece;
        createDirectory("peer_" + id);
        this.myFile = new File("peer_" + id + "/" + filename);
    }

    public boolean createFile() throws IOException {
        if (myFile.exists()) {
            return false;
        }
        if (!myFile.createNewFile()) {
            return false;
        }

        FileOutputStream fos = new FileOutputStream(myFile);
        byte[] tempBuffer = new byte[PieceSize];
        for (int i = PieceSize; i < FileSize; i += PieceSize) {
            fos.write(tempBuffer);
        }
        int remainingBytes = FileSize % PieceSize;
        if (remainingBytes > 0) {
            byte[] remainingtempBuffer = new byte[remainingBytes];
            fos.write(remainingtempBuffer);
        }
        fos.close();
        return true;
    }

    /**
     * given a pieceID returns the corresponding chunk
     * data should be already present in file else request message would not come
     */
    public byte[] readPiece(int pieceID) throws IOException {
        RandomAccessFile read = new RandomAccessFile(myFile, "r");
        read.seek(pieceID * PieceSize);

        byte[] x;
        int remainingBytes = this.FileSize - pieceID * PieceSize;
        if (remainingBytes < PieceSize) {
            x = new byte[remainingBytes];
        } else {
            x = new byte[PieceSize];
        }
        read.read(x);
        read.close();
        return x;
    }

    public void writePiece(int pieceID, byte[] pieceData) throws IOException {
        RandomAccessFile read = new RandomAccessFile(this.myFile, "rws");
        int length = pieceData.length;
        read.seek(pieceID * PieceSize);
        read.write(pieceData, 0, length);
        read.close();
    }

    public File getMyFile() {
        return this.myFile;
    }

    public void setMyFile(File file) {
        myFile = file;
    }

    public int getFileSize() {
        return this.FileSize;
    }

    public void setFileSize(int fileSize) {
       this.FileSize = fileSize;
    }

    public int getPieceSize() {
        return PieceSize;
    }

    public void setPieceSize(int pieceSize) {
        this.PieceSize = pieceSize;
    }

    public boolean createDirectory(String DirectoryPath) {
        myDirectory = new File(DirectoryPath);
        if (myDirectory.exists()) {
            return true;
        }
        return myDirectory.mkdir();
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int iD) {
        ID = iD;
    }


}
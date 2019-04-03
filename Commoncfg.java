
// This is used to store parameters in the common.cfg.
public class Commoncfg {
    int Num_Of_PreferredNeighbors;
    int Unchoking_Interval;
    int Optimistic_Unchoking_Interval;
    String FileName;
    int FileSize;
    int PieceSize;

    public Commoncfg() {

    }

    public Commoncfg(int nop, int ui, int oui, String fn, int fs, int ps) {
        this.Num_Of_PreferredNeighbors = nop;
        this.Unchoking_Interval = ui;
        this.Optimistic_Unchoking_Interval = oui;
        this.FileName = fn;
        this.FileSize = fs;
        this.PieceSize = ps;
    }

    public int getNum_Of_PreferredNeighbors() {
        
        return Num_Of_PreferredNeighbors;
    }


    public void setNum_Of_PreferredNeighbors(int num_Of_PreferredNeighbors) {
        Num_Of_PreferredNeighbors = num_Of_PreferredNeighbors;
    }

    public int getUnchoking_Interval() {
  
        return Unchoking_Interval;
    }


    

    public void setUnchoking_Interval(int Unchoking_Interval) {
        this.Unchoking_Interval = Unchoking_Interval;
    }

    public int getOptimistic_Unchoking_Interval() {
        return this.Optimistic_Unchoking_Interval;
    }

    public void setOptimistic_Unchoking_Interval(int Optimistic_Unchoking_Interval) {
       this.Optimistic_Unchoking_Interval = Optimistic_Unchoking_Interval;
    }

    public String getFileName() {
        return this.FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public int getFileSize() {
        return this.FileSize;
    }

    public void setFileSize(int FileSize) {
        this.FileSize = FileSize;
    }

    public int getPieceSize() {
        return this.PieceSize;
    }

    public void setPieceSize(int PieceSize) {
        this.PieceSize = PieceSize;
    }

}


//This class is to store the parameter in the peerinfo.cfg
public class PeerInfo {
    private int peerID;
    private String hostName;
    private int port;
    private Boolean fileStatus;

    public PeerInfo() {
    }

    public PeerInfo(int peerID, String hn, int pt, Boolean fs) {
        hostName = hn;
        port = pt;
        fileStatus = fs;
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

    public int getPeerID() {
        getInfo();
        return peerID;
    }

    public void setPeerID(int peerID) {
        outputInfo();
        this.peerID = peerID;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Boolean getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Boolean fileStatus) {
        this.fileStatus = fileStatus;
    }

}

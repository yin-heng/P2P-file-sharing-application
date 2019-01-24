import java.net.Socket;

// This class is used to store the information of each peer such as choked or unchoked and so on. Besides
// this class also creates the comparator according to the download speed.

public class NeighborInfo implements Comparable {
    private int peerID;
    private Socket socket;
    private String hostName;
    private int port;
    private boolean preferred;
    private boolean optimisticallyUnchoked;
    private double downloadRate;
    private boolean hasCompleteFile;
    private boolean isInterested;
    private boolean interest;
    private boolean isChoked = true;
    private boolean choke = true;
    private Client client;
    private int downloadedPieceNum = 0;

    public int getDownloadedPieceNum() {
        return downloadedPieceNum;
    }

    public void setDownloadedPieceNum(int downloadedPieceNum) {
        this.downloadedPieceNum = downloadedPieceNum;
    }

    public void downloadedPieceNumIncrement() {
        this.downloadedPieceNum += 1;
    }

    public NeighborInfo(int peerID, String hostName, int port, Boolean hasCompleteFile, Client client) {
        this.peerID = peerID;
        this.hostName = hostName;
        this.port = port;
        this.hasCompleteFile = hasCompleteFile;
        this.client = client;
    }
    public NeighborInfo(int peerID, String hostName, int port, Boolean hasCompleteFile) {
        this.peerID = peerID;
        this.hostName = hostName;
        this.port = port;
        this.hasCompleteFile = hasCompleteFile;
    }

    public int getPeerID() {
        return peerID;
    }

    public void setPeerID(int peerID) {
        this.peerID = peerID;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
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

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public boolean isOptimisticallyUnchoked() {
        return optimisticallyUnchoked;
    }

    public void setOptimisticallyUnchoked(boolean optimisticallyUnchoked) {
        this.optimisticallyUnchoked = optimisticallyUnchoked;
    }

    public double getDownloadRate() {
        return downloadRate;
    }

    public void setDownloadRate(double downloadRate) {
        this.downloadRate = downloadRate;
    }

    public boolean isHasCompleteFile() {
        return hasCompleteFile;
    }

    public void setHasCompleteFile(boolean hasCompleteFile) {
        this.hasCompleteFile = hasCompleteFile;
    }

    public boolean isInterested() {
        return isInterested;
    }

    public void setInterested(boolean isInterested) {
        this.isInterested = isInterested;
    }

    public boolean isInterest() {
        return interest;
    }

    public void setInterest(boolean interest) {
        this.interest = interest;
    }

    public boolean isChoked() {
        return isChoked;
    }

    public void setChoked(boolean isChoked) {
        this.isChoked = isChoked;
    }

    public boolean isChoke() {
        return choke;
    }

    public void setChoke(boolean choke) {
        this.choke = choke;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public int compareTo(Object o) {
        NeighborInfo neighborInfo = (NeighborInfo) o;
        return (int) (this.downloadRate - neighborInfo.downloadRate);
    }
}
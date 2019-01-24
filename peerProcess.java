import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;

public class peerProcess {
    private int peerid;
    private int portnum;
    private Commoncfg commoncfg;
    private Map<Integer, PeerInfo> peerinfo;
    private Map<Integer, NeighborInfo> neighborlist;
    private CommunicationManager cm;
    private boolean hasFile;
    private Bitfield bitfield;
    private int sizeofpiece;
    private int numofpiece;
    private int lastpiecesize;
    private Logger log;

    public peerProcess(int myPeerID) {
        try {
            this.peerid = myPeerID;
            String filepath = "Common.cfg";
            Parser parser1 = new Parser(filepath);
            commoncfg = parser1.parseCommon();
            String filepath_peer = "PeerInfo.cfg";
            Parser parser2 = new Parser(filepath_peer);
            peerinfo = parser2.parsePeerInfo();
            neighborlist = new ConcurrentHashMap<Integer, NeighborInfo>();
            System.out.println("peerID = " + peerid);
            System.out.println("NumberOfPreferredNeighbors = " + commoncfg.getNum_Of_PreferredNeighbors());
            System.out.println("UnchokingInterval = " + commoncfg.getUnchoking_Interval() + "s");
            System.out.println("OptimisticUnchokingInt;rval = " + commoncfg.getOptimistic_Unchoking_Interval() + "s");
            System.out.println("FileName is " + commoncfg.getFileName());
            System.out.println("FileSize = " + commoncfg.getFileSize() + "bytes");
            System.out.println("PieceSize = " + commoncfg.getPieceSize() + "bytes");
            this.sizeofpiece = commoncfg.getPieceSize();
            int sizeoffile = commoncfg.getFileSize();
            log = new Logger();

            this.numofpiece = sizeoffile / this.sizeofpiece;
            int tmp = sizeoffile % this.sizeofpiece;
            if (tmp == 0) {
                this.lastpiecesize = this.sizeofpiece;
            } else {
                this.lastpiecesize = tmp;
                this.numofpiece = this.numofpiece + 1;
            }
            for (Integer peerID : peerinfo.keySet()) {
                PeerInfo peer = peerinfo.get(peerID);
                if (peer.getPeerID() != peerid) {
                    neighborlist.put(peer.getPeerID(), new NeighborInfo(peer.getPeerID(), peer.getHostName(), peer.getPort(), peer.getFileStatus()));
                } else {
                    portnum = peer.getPort();
                    hasFile = peer.getFileStatus();
                    this.bitfield = new Bitfield(this.numofpiece, this.hasFile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startProcess() {
        Thread serverThread;
        log.CreateLog("" + peerid);
        try {
            cm = new CommunicationManager(peerid, commoncfg, neighborlist, bitfield, peerinfo);
            serverThread = new Thread(new Server(portnum, cm, log));
            serverThread.start();
            int tmp[] = new int[peerinfo.size()];
            int i  = 0;
            for (Integer peerID : peerinfo.keySet()){
                tmp[i++] = peerID;
             }
             Arrays.sort(tmp);


            for (Integer peerID : peerinfo.keySet()) {
                if (peerID < peerid) {
                    //peer has already been started, try to make a connection
                    Client newClient = new Client(peerinfo.get(peerID).getHostName(),
                            peerinfo.get(peerID).getPort());
                    Thread handlerThread = new Thread(new MessageHandler(peerID, newClient, cm, log));
                    newClient.connect();
                    handlerThread.start();
                    log.TcpConnectionOutgoing("" + peerid, "" + peerID);

                }
                else if(peerid == tmp[tmp.length - 1]) serverThread.stop();
            }

            Thread preferredThread = new Thread(new PreferredNeighborsHandler(commoncfg, neighborlist,bitfield, log, peerid));
            Thread optimisticThread = new Thread(new OptimisticalUnchokedHandler(commoncfg, neighborlist, log, peerid));
            preferredThread.start();
            optimisticThread.start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        peerProcess peer = new peerProcess(Integer.valueOf(args[0]));
        peer.startProcess();
        System.out.println("Peer process is started!");
    }
}
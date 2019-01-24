
import java.util.*;

// This class is used to change preferred neightbors.
public class PreferredNeighborsHandler implements Runnable {
    private Map<Integer, NeighborInfo> neighborsMap;
    private Map<Integer, NeighborInfo> interestedNeighborMap;
    private Map<Integer, NeighborInfo> prefferedNeighbors;
    private List<NeighborInfo> downLoadRateList;
    private Commoncfg commoncfg;
    private Bitfield myBitfield;
    private Logger logger;
    private int myPeerID;


    public PreferredNeighborsHandler(Commoncfg commoncfg, Map<Integer, NeighborInfo> neighborsMap, Bitfield myBitfield, Logger logger, int myPeerID) {
        this.myBitfield = myBitfield;
        this.neighborsMap = neighborsMap;
        this.commoncfg = commoncfg;
        interestedNeighborMap = new HashMap<>();
        downLoadRateList = new ArrayList<>();
        prefferedNeighbors = new HashMap<>();
        this.logger = logger;
        this.myPeerID = myPeerID;

    }

    public void run() {
        try {
            while (true) {
                selectPrefferedNeighbors();
                if (!prefferedNeighbors.isEmpty()) {
                    System.out.println("Select preffered neighbors as follows: ");
                    for (Integer peerID : prefferedNeighbors.keySet()) {
                        System.out.println("Peer: " + peerID);
                    }
                }
                Map<Integer, Integer> prevPieceNumMap = getPieceNum();
                Thread.sleep(commoncfg.getUnchoking_Interval() * 1000);
                Map<Integer, Integer> nowPieceNumMap = getPieceNum();
                calculateAndUpdateDownloadRate(prevPieceNumMap, nowPieceNumMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void selectPrefferedNeighbors() throws Exception {
        if (!neighborsMap.isEmpty()) {
            Map<Integer, NeighborInfo> prevPreffered = getPrevPrefferedNeighbors();
            int count;
            getInteresetedNeighbor();
            sortByDownloadRate();
            boolean a = false;
            if (a) {
                System.out.println("To select Preffered Neighbors");
            }
            if (!prefferedNeighbors.isEmpty()) {
                prefferedNeighbors.clear();
            }

            int numofPreffered = commoncfg.getNum_Of_PreferredNeighbors();
            if (numofPreffered <= 0) {
                throw new Exception("The number of preferred neighbors can not be less or equal than 0 !");
            } else if (numofPreffered >= interestedNeighborMap.size()) {
                count = interestedNeighborMap.size();
            } else {
                count = commoncfg.getNum_Of_PreferredNeighbors();
            }

            if (myBitfield.isHasCompleteFile() == true) {
                Set<Integer> set = new HashSet<Integer>(count);
                while (set.size() < count) {
                    Random random = new Random();
                    int index = random.nextInt(downLoadRateList.size());
                    set.add(index);
                }
                Iterator<Integer> iterator = set.iterator();

                while (iterator.hasNext()) {
                  
                    NeighborInfo newPreffered = downLoadRateList.get(iterator.next());
                    prefferedNeighbors.put(newPreffered.getPeerID(), newPreffered);
                    neighborsMap.get(newPreffered.getPeerID()).setPreferred(true);
                    neighborsMap.get(newPreffered.getPeerID()).setChoked(false);
                }
            } else {
                for (int i = downLoadRateList.size() - 1; i > downLoadRateList.size() - 1 - count; i--) {
                    NeighborInfo newPreffered = downLoadRateList.get(i);
                    prefferedNeighbors.put(newPreffered.getPeerID(), newPreffered);
                    neighborsMap.get(newPreffered.getPeerID()).setPreferred(true);
                    neighborsMap.get(newPreffered.getPeerID()).setChoked(false);

                }
            }
            if (!prevPreffered.equals(prefferedNeighbors)) {
                logger.PrefNeighbours("" + myPeerID, prefferedNeighbors);
            }
            if (prevPreffered != null) {
                for (Integer peerID : prefferedNeighbors.keySet()) {
                    if (!prevPreffered.containsKey(peerID)) {
                        Message msg = new UnchokeMessage();
                        System.out.println("Send an unchoke message");
                        neighborsMap.get(peerID).getClient().send(msg.getMessageBytes());
                    }
                }
            } else {
                for (Integer peerID : prefferedNeighbors.keySet()) {
                    Message msg = new UnchokeMessage();
                    System.out.println("Send an unchoke message");
                    neighborsMap.get(peerID).getClient().send(msg.getMessageBytes());

                }
            }

            for (Integer peerID : neighborsMap.keySet()) {
                NeighborInfo neighbor = neighborsMap.get(peerID);
                if ((neighbor.isOptimisticallyUnchoked() == false && neighbor.isChoked() == false)
                        && (neighbor.isPreferred() == false && neighbor.isChoked() == false)) {
                    Message msg = new ChokeMessage();
                    System.out.println("Send a choke message");
                    neighbor.getClient().send(msg.getMessageBytes());

                }
            }
        }
    }

    private void sortByDownloadRate() {
        downLoadRateList.clear();
        boolean a = true;
        if (!a) {
        System.out.println("To sort by download rate");
        }
        for (Integer peerID : interestedNeighborMap.keySet()) {
            downLoadRateList.add(interestedNeighborMap.get(peerID));
        }
        Collections.sort(downLoadRateList);
    }

    private void getInteresetedNeighbor() {
        interestedNeighborMap.clear();
        boolean a = true;
        if (!a) {
            System.out.println("To get Interested Neighbor");
        }
        for (Integer peerID : neighborsMap.keySet()) {
            NeighborInfo neighbor = neighborsMap.get(peerID);
            if (neighbor.isInterest() == true) {
                interestedNeighborMap.put(peerID, neighbor);
            }
        }
    }

    private Map<Integer, Integer> getPieceNum() {
        Map<Integer, Integer> pieceNumMap = new HashMap<>();
        boolean a = true;
        if (!a) {
            System.out.println("To get piece number");
        }
        for (Integer peerID : neighborsMap.keySet()) {
            NeighborInfo neighbor = neighborsMap.get(peerID);
            if (neighbor != null) {
                pieceNumMap.put(neighbor.getPeerID(), neighbor.getDownloadedPieceNum());
            }
        }
        return pieceNumMap;
    }

    private void calculateAndUpdateDownloadRate(Map<Integer, Integer> prev, Map<Integer, Integer> now) {
        boolean a = true;
        if (!a) {
            System.out.println("To calculate and update downloadrate");
        }
        for (Integer peerID : prev.keySet()) {
            int pieceNumDiff = now.get(peerID) - prev.get(peerID);
            if (pieceNumDiff >= 0) {
                double downloadRate = pieceNumDiff * commoncfg.getPieceSize() * 1.0 / commoncfg.getUnchoking_Interval();
                neighborsMap.get(peerID).setDownloadRate(downloadRate);
            }
        }
    }

    private Map<Integer, NeighborInfo> getPrevPrefferedNeighbors() {
        Map<Integer, NeighborInfo> prev = new HashMap<Integer, NeighborInfo>();
        boolean a = true;
        if (!a) {
            System.out.println("To get prevpreffered neighbors");
        }
        for (Integer peerID : this.prefferedNeighbors.keySet()) {
            prev.put(peerID, this.prefferedNeighbors.get(peerID));
        }
        return prev;
    }
}

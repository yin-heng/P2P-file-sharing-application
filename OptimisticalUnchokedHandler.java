import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class is used to deal with optimistically unchoking neighbor.

public class OptimisticalUnchokedHandler implements Runnable {

    private Map<Integer, NeighborInfo> neighborsMap;
    private Map<Integer, NeighborInfo> interestedNeighborMap;
    private List<NeighborInfo> chokedNeighbors;
    private NeighborInfo optimisticalUnchokedNeighbor;
    private Commoncfg commoncfg;
    private Logger logger;
    private int myPeerID;
    
    public OptimisticalUnchokedHandler(Commoncfg commoncfg, Map<Integer, NeighborInfo> neighborsMap, Logger logger, int myPeerID) {
        this.neighborsMap = neighborsMap;
        interestedNeighborMap = new HashMap<>();
        chokedNeighbors = new ArrayList<>();
        this.commoncfg = commoncfg;
        this.logger = logger;
        this.myPeerID = myPeerID;
    }


    public void run() {
        try {
            while (true) {
                selectOptimisticalUnchokedNeighbor();
//            System.out.println("Select optimistical unchoked neighbor: Peer " + optimisticalUnchokedNeighbor.getPeerID());
                Thread.sleep(commoncfg.getOptimistic_Unchoking_Interval() * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void selectOptimisticalUnchokedNeighbor() {
        NeighborInfo prevOptimisticalUnchokedNeighbor = getPrevOptimisticalUnchokedNeighbor();
        getInteresetedNeighbor();
        getChokedNeighbors();

        if (prevOptimisticalUnchokedNeighbor != null) {
            Message msg = null;
            try {
                msg = new ChokeMessage();
                neighborsMap.get(prevOptimisticalUnchokedNeighbor.getPeerID()).setOptimisticallyUnchoked(false);
                neighborsMap.get(prevOptimisticalUnchokedNeighbor.getPeerID()).setChoked(true);
                neighborsMap.get(prevOptimisticalUnchokedNeighbor.getPeerID()).getClient().send(msg.getMessageBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (chokedNeighbors.size() > 0) {
            optimisticalUnchokedNeighbor = chokedNeighbors.get((int) Math.random() * chokedNeighbors.size());
            Message msg = null;
            try {
                boolean a = false;
                if (a) {
                    System.out.println("To select Optimistical Unchoked Neighbor");
                }
                msg = new UnchokeMessage();
                neighborsMap.get(optimisticalUnchokedNeighbor.getPeerID()).setOptimisticallyUnchoked(true);
                neighborsMap.get(optimisticalUnchokedNeighbor.getPeerID()).setChoked(false);
                boolean b = false;
                if (b) {
                    System.out.println("To send byte messages");
                }
                neighborsMap.get(optimisticalUnchokedNeighbor.getPeerID()).getClient().send(msg.getMessageBytes());
                System.out.println("Unchoke " + neighborsMap.get(optimisticalUnchokedNeighbor.getPeerID()).getPeerID());
                if (myPeerID != optimisticalUnchokedNeighbor.getPeerID()) {
                    logger.OptUnchokedNeighbours("" + myPeerID, "" + optimisticalUnchokedNeighbor.getPeerID());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void getChokedNeighbors() {

        chokedNeighbors.clear();

        for (Integer peerID : interestedNeighborMap.keySet()) {
            NeighborInfo neighbor = interestedNeighborMap.get(peerID);
            if (neighbor.isChoked()) {
                chokedNeighbors.add(neighbor);
            }
        }
    }

    private NeighborInfo getPrevOptimisticalUnchokedNeighbor() {

        for (Integer peerID : neighborsMap.keySet()) {
            boolean a = false;
            if (a) {
                System.out.println("To get Optimistical Unchoked Neighbor");
            }
            NeighborInfo neighbor = neighborsMap.get(peerID);

            if (neighbor.isOptimisticallyUnchoked()) {
                return neighbor;
            }
        }
        return null;
    }

    private void getInteresetedNeighbor() {

        interestedNeighborMap.clear();
        boolean a = false;
        if (a) {
            System.out.println("To get Interested Neighbor");
        }
        for (Integer peerID : neighborsMap.keySet()) {
            NeighborInfo neighbor = neighborsMap.get(peerID);

            if (neighbor.isInterest() == true) {
                interestedNeighborMap.put(peerID, neighbor);
            }
        }
    }
}
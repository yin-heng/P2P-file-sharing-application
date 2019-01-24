import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

//This class is to create and write log.

public class Logger {
    //Creating a Log file named log_peer_[peerID].log if one doesnt exist
    public void CreateLog(String peer) {
        try {
            String filename = "log_peer_" + peer + ".log";
            File output = new File("peer_" + peer);
            output.mkdir();

            File file = new File(filename);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Log File for Peer " + peer + ".");
                bw.newLine();
                bw.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Writing Log for Incoming TCP connection from Peer 2 to peer 1
    public void TcpConnectionIncoming(String peer1, String peer2) {
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " is connected from Peer " + peer2 + ".";
            bw.write(str);//appends the string to the file
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Writing Log for making the TCP connection from Peer1 to Peer2
    public void TcpConnectionOutgoing(String peer1, String peer2) {
        //String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }
        try {

            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " makes a connection to Peer " + peer2 + ".";
            bw.write(str);//appends the string to the file
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Writing Log for change of preferred neighbors
    public void PrefNeighbours(String peer1, Map<Integer, NeighborInfo> preferredMap) {
        //String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            if (!preferredMap.isEmpty()) {
                String str = getDate() + ": Peer " + peer1 + " has the preferred neighbors: ";
                bw.write(str);//appends the string to the file
                for (Integer i : preferredMap.keySet()) {
                    bw.write(i + ", ");
                }
                bw.newLine();
            }
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    //Writing Log forchange of optimistically unchoked neighbor
    public void OptUnchokedNeighbours(String peer1, String peer2) {
        //String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " has the optimistically unchoked neighbour Peer " + peer2 + ".";
            bw.write(str);//appends the string to the file
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    //Writing Log for unchoking
    public void Unchoked(String peer1, String peer2) {
        //String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " is unchoked by Peer " + peer2 + ".";
            bw.write(str);//appends the string to the file
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    //Writing Log for choking
    public void Choked(String peer1, String peer2) {
        //String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " is choked by Peer " + peer2 + ".";
            bw.write(str);//appends the string to the file
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    //Writing Log for receiving ‘have�? message
    public void Have(String peer1, String peer2, int index) {
        //String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " received the 'HAVE' message from Peer " + peer2 + " for the piece " + index + ".";
            bw.write(str);//appends the string to the file
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    //Writing Log for  receiving ‘interested�? message
    public void Interested(String peer1, String peer2) {
        //String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " received the 'INTERESTED' message from Peer " + peer2 + ".";
            bw.write(str);//appends the string to the file
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Writing Log for receiving ‘not interested�? message
    public void NotInterested(String peer1, String peer2) {
        //String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " received the 'NOT INTERESTED' message from Peer " + peer2 + ".";
            //appends the string to the file
            bw.write(str);
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Writing Log for downloading a piece
    public void PieceDownload(String peer1, String peer2, int index, int pieces) {
       // String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";\
        String filename = "log_peer_" + peer1 + ".log";
        File file = new File(filename);
        if (!file.exists()) {
            CreateLog(peer1);
        }

        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " has downloaded the piece " + index + " from Peer " + peer2 + ". Now the number of pieces it has is " + pieces + ".";
            bw.write(str);//appends the string to the file
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Writing Log for completion of download
    public void DownloadComplete(String peer1) {
        //String filename = "peer_" + peer1 + "/log_peer_" + peer1 + ".log";
        String filename = "log_peer_" + peer1 + ".log";
        try {
            FileWriter fw = new FileWriter(filename, true); //the true will append the new data
            BufferedWriter bw = new BufferedWriter(fw);
            String str = getDate() + ": Peer " + peer1 + " has downloaded the complete file.";
            bw.write(str);//appends the string to the file
            bw.newLine();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //Returns present local date time hours minutes and seconds 
    public String getDate() {
        // Instantiate a Date object
        Date date = new Date();
        // display time and date using toString()
        return (date.toString());
    }


}

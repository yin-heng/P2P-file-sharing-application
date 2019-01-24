import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server implements Runnable {
    private int listenport;
    private ServerSocket serverSocket;
    private static final int ACCEPT_TIMEOUT = 1000;
    private CommunicationManager communicationManager;
    private Logger logger;

    public Server(int port, CommunicationManager communicationManager, Logger logger) throws UnknownHostException, IOException {
        this.listenport = port;
        this.serverSocket = new ServerSocket(port);
        this.communicationManager = communicationManager;
        this.logger = logger;
    }

    public void run() {
        System.out.println("Start listening on port: " + this.listenport + ".");

        //listen for connections from other peers
        while (true) {
            System.out.println("Listen for connections from other peers");
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                Client newClient = new Client(socket);
                Thread handlerThread = new Thread(new MessageHandler(newClient, this.communicationManager, this.logger));
                handlerThread.start();

            }
             catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getListenport() {
        boolean a = true;
        if (!a) {
            System.out.println("Get listen port");
        }
        return listenport;
    }

    public void setListenport(int listenport) {
        boolean a = true;
        if (!a) {
            System.out.println("Set listen port");
        }
        this.listenport = listenport;
    }

}
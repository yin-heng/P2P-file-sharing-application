import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client implements Runnable {

    private Socket socket;

    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private PipedOutputStream outputToHandler;
    private String IP_ADDR;
    private int PORT;

    public Client(String IP_ADDR, int PORT) {
        this.IP_ADDR = IP_ADDR;
        this.PORT = PORT;
    }

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.inputStream = new DataInputStream(socket.getInputStream());
            outputClientInfo();
            this.outputToHandler = new PipedOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    public void connect() {
        // This method is used to connect peers.
        try {
            System.out.println("Connect to peer:" + IP_ADDR + ".");
            this.socket = new Socket(IP_ADDR, PORT);
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.inputStream = new DataInputStream(socket.getInputStream());
            this.outputToHandler = new PipedOutputStream();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  
    public PipedOutputStream getOutputToHandler() {
        return this.outputToHandler;
    }

    public synchronized void send(byte[] data) {
        try {
            this.outputStream.write(data);
            this.outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        try {
            byte[] lengthBuffer = new byte[4];
            if (this.inputStream.read(lengthBuffer) == 4) {
                int length = byteArrayToInt(lengthBuffer);

                this.outputToHandler.write(lengthBuffer);

                // now read the data indicated by length and write it to buffer
                byte[] buffer = new byte[length];
                this.inputStream.readFully(buffer);
                this.outputToHandler.write(buffer);
                this.outputToHandler.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void receiveHandShake() throws EOFException, IOException {
        byte[] buffer = new byte[32];
      
        this.inputStream.readFully(buffer);
        this.outputToHandler.write(buffer);
        this.outputToHandler.flush();
    }

    @Override
    public void run() {
        try {
            System.out.println("Client Thread Start!");
            while (true) {
                this.receive();
            }
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static int byteArrayToInt(byte[] b) {  
        return   b[3] & 0xFF |  
                (b[2] & 0xFF) << 8 |  
                (b[1] & 0xFF) << 16 |  
                (b[0] & 0xFF) << 24;  
    }  
}

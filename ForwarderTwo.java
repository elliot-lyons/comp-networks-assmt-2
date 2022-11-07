import java.util.*;
import java.io.*;
import java.net.*;

public class ForwarderTwo
{
    static final int DEFAULT_PORT = 50002;
    static final int DEFAULT_CLIENT_PORT = 50003;
    static final int DEFAULT_FOR_PORT = 50004;
    static final String DEFAULT_CLIENT_NODE = "ClientTwo";
    static final String DEFAULT_FOR_NODE = "ForwarderThree";

    public static void run()
    {
        try
        {
            DatagramSocket socket = new DatagramSocket(DEFAULT_PORT);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);
            socket.close();

            int nextPort = DEFAULT_CLIENT_PORT;
            String nextNode = DEFAULT_CLIENT_NODE;

            String data = new String(packet.getData());
            byte[] theData = packet.getData();
            byte header = theData[0];

            int head = header;


            if (head == 1)            // forward table eventually
            {
                System.out.println("Withdrawal wanted.");
                nextPort = DEFAULT_FOR_PORT;
                nextNode = DEFAULT_FOR_NODE;
            }

            else
            {
                System.out.println("Deposit wanted.");
            }
            
            InetSocketAddress nextAddress = new InetSocketAddress(nextNode, nextPort);
            DatagramSocket nextSocket = new DatagramSocket(nextPort);
            
            packet.setSocketAddress(nextAddress);
            
            nextSocket.send(packet);
            nextSocket.close();
        }   catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        run();
    }
}
import java.util.*;
import java.io.*;
import java.net.*;

public class ForwarderOne 
{
  
    static final int DEFAULT_PORT = 50001;
    static final int DEFAULT_FOR_PORT = 50002;
	static final String DEFAULT_DST_NODE = "ForwarderTwo";
	InetSocketAddress dstAddress;

    public static void run()
    {
        try
        {
            DatagramSocket socket = new DatagramSocket(DEFAULT_PORT);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);
            socket.close();

            System.out.println("" + (new String(packet.getData())));
            
            InetSocketAddress toForwarderTwo = new InetSocketAddress(DEFAULT_DST_NODE, DEFAULT_FOR_PORT);
            DatagramSocket forward = new DatagramSocket(DEFAULT_FOR_PORT);
            
            packet.setSocketAddress(toForwarderTwo);
            
            forward.send(packet);
            forward.close();
        }   catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        run();
    }
}

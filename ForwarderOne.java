import java.util.*;
import java.io.*;
import java.net.*;

public class ForwarderOne 
{
  
    static final int DEFAULT_PORT = 1;
    static final int DEFAULT_FOR_PORT = 6;
	static final String DEFAULT_DST_NODE = "Controller";
	InetSocketAddress dstAddress;

    public static void run()
    {
        System.out.println("Waiting for contact...");
        try
        {
            DatagramSocket socket = new DatagramSocket(DEFAULT_PORT);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);
            socket.close();

            System.out.println("Packet received.");
            
            InetSocketAddress toController = new InetSocketAddress(DEFAULT_DST_NODE, DEFAULT_FOR_PORT);
            DatagramSocket controller = new DatagramSocket(DEFAULT_FOR_PORT);

            byte[] control = new byte[1];
            control[0] = sent[0];

            DatagramPacket xPacket = new DatagramPacket(control, 0, control.length);
            
            xPacket.setSocketAddress(toController);
            System.out.println("Sending to control");
            controller.send(xPacket);

            byte[] fromControl = new byte[1024];
            DatagramPacket yPacket = new DatagramPacket(fromControl, fromControl.length);
            controller.receive(yPacket);
            System.out.println("Received from control");
            controller.close();

            byte[] received = yPacket.getData();

            byte[] r = new byte[received.length - 1];

            for (int i = 0; i < r.length; i++)
            {
                r[i] = received[i+1];
            }

            int nextPort = received[0];

            String nextNode = new String(r);
            System.out.println("Node: " + nextNode);

            System.out.println("Port: " + nextPort);

            InetSocketAddress toForwarder = new InetSocketAddress(nextNode, nextPort);
            DatagramSocket forward = new DatagramSocket(nextPort);

            packet.setSocketAddress(toForwarder);
            forward.send(packet);

            System.out.println("Forwarding packet.");
            forward.close();

            System.out.println("Program completed.");
        }   catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        run();
    }
}

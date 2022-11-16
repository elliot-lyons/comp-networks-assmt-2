import java.util.*;
import java.io.*;
import java.net.*;

public class ForwarderThree
{
    static final int DEFAULT_PORT = 4;
    static final int DEFAULT_FOR_PORT = 6;
    static final String DEFAULT_DST_NODE = "Controller";

    public static void run()
    {
        System.out.println("Waiting for contact...");
        byte[] sent = new byte[1024];
        boolean error = false;
        DatagramPacket  packet = new DatagramPacket(sent, sent.length);;

        try
        {
            DatagramSocket socket = new DatagramSocket(DEFAULT_PORT);
            socket.setSoTimeout(20*1000);
            socket.receive(packet);
            socket.close();
        }

        catch (Exception e)
        {
            error = true;
            System.out.println("Timeout has occurred. Deposit wanted");
        }

        if (!error)
        {
            try
            {
                System.out.println("Packet received");
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

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }   
    }
    
    public static void main(String[] args) {
        run();
        System.out.println("Program completed.");
    }
}
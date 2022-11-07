import java.util.*;
import java.io.*;
import java.net.*;

public class ForwarderTwo
{
    static final int DEFAULT_PORT = 50001;
    static final int DEFAULT_FOR_PORT = 50002;

    public static void run()
    {
        try
        {
            DatagramSocket socket = new DatagramSocket(DEFAULT_PORT);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);

            DatagramSocket forward = new DatagramSocket();
            byte[] toForward = packet.getData();

            DatagramPacket f = new DatagramPacket(toForward, toForward.length, iNet, 9997);
            forward.send(f);
            
            socket.close();
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

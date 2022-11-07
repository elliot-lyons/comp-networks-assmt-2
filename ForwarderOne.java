import java.util.*;
import java.io.*;
import java.net.*;

public class ForwarderOne 
{
    public static void run()
    {
        try
        {
            String ip = "172.19.0.1";
            DatagramSocket socket = new DatagramSocket(9999);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);

            Inet4Address iNet = (Inet4Address) Inet4Address.getByName(ip);
            DatagramSocket forward = new DatagramSocket();
            byte[] toForward = packet.getData();

            DatagramPacket f = new DatagramPacket(toForward, toForward.length, iNet, 9998);
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


import java.net.*;

public class Forwarder
{
    public static void main(String[] args)
    {
        try
        {
            DatagramSocket socket = new DatagramSocket(9999);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);

            InetAddress iNet = InetAddress.getLocalHost();
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
}
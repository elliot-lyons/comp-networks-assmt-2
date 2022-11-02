package src;
import java.net.*;

public class Receiver 
{
    public static void main(String[] args)
    {
        try
        {
            DatagramSocket socket = new DatagramSocket(9998);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);

            String x = new String(packet.getData());
            System.out.println(x);

        } catch (Exception e)
        {
            e.printStackTrace();
        }   
    }    
}

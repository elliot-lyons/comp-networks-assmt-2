
import java.net.*;

public class Receiver 
{
    public static void main(String[] args)
    {
        System.out.println("Go");
        try
        {
            DatagramSocket socket = new DatagramSocket(9998);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);

            String x = new String(packet.getData());
            System.out.println(x);

            socket.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }   
    }    
}

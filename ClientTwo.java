
import java.net.*;

public class ClientTwo 
{
    static final int DEFAULT_PORT = 50003;

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
        }   catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
       run(); 
    }    
}

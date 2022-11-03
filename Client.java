
import java.net.*;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            DatagramSocket socket = new DatagramSocket();
            String test = "Hello World!";

            byte[] send = test.getBytes();
            InetAddress iNet = InetAddress.getLocalHost();

            DatagramPacket packet = new DatagramPacket(send, send.length, iNet, 9999);
            socket.send(packet);
            socket.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
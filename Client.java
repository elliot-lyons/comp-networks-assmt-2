import java.net.*;

public class Client
{

    public String ip;

    Client(String ip)
    {
        this.ip = ip;
    }

    public synchronized void run()
    {
        try
        {
            DatagramSocket socket = new DatagramSocket();
            String test = "Hello World!";

            byte[] send = test.getBytes();
            Inet4Address iNet = (Inet4Address) Inet4Address.getByName(ip);

            DatagramPacket packet = new DatagramPacket(send, send.length, iNet, 9999);
            socket.send(packet);
            socket.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        
    }
}
import java.util.*;
import java.io.*;
import java.net.*;

public class ClientOne
{
    public static void run()
    {
        try                                             // send to forwarder
        {
            String ip = "172.18.0.1";
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
        run();
    }
}
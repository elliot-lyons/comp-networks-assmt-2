import java.util.*;
import java.io.*;
import java.net.*;

public class ClientOne
{
    static final int DEFAULT_SRC_PORT = 50000;
	static final int DEFAULT_DST_PORT = 50001;
	static final String DEFAULT_DST_NODE = "ForwarderOne";
	InetSocketAddress dstAddress;

    public static void run()
    {
        try                                             // send to forwarder
        {
            InetSocketAddress toForwarderOne = new InetSocketAddress(DEFAULT_DST_NODE, DEFAULT_DST_PORT);
            DatagramSocket socket = new DatagramSocket(DEFAULT_SRC_PORT);

            String test = "Hello World!";

            byte[] beforeSend = test.getBytes();

            byte[] send = new byte[beforeSend.length+1];

            Integer header = 1;
            send[0] = header.byteValue();

            for (int i = 1; i < send.length; i++)
            {
                send[i] = beforeSend[i-1];
            }

            DatagramPacket packet = new DatagramPacket(send, 0, send.length);
            packet.setSocketAddress(toForwarderOne);
            
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
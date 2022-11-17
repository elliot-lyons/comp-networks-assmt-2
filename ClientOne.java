import java.util.*;
import java.io.*;
import java.net.*;

public class ClientOne
{
    static final int DEFAULT_SRC_PORT = 0;
	static final int DEFAULT_DST_PORT = 1;
	static final String DEFAULT_DST_NODE = "ForwarderOne";
	InetSocketAddress dstAddress;

    public static void run()
    {
        try
        {
            InetSocketAddress toForwarderOne = new InetSocketAddress(DEFAULT_DST_NODE, DEFAULT_DST_PORT);
            DatagramSocket socket = new DatagramSocket(DEFAULT_SRC_PORT);
            socket.setSoTimeout(10);

            Scanner scanner = new Scanner(System.in);
            boolean error = false;
            Integer header = 1;
            int toSend = 0;

            System.out.println("Account balance = $10000.");

            do
            {
                System.out.println("" + (error ? "Error. " : "") + "Press 1 to withdraw funds or 2 to add funds to your account.");
                String x = scanner.nextLine();

                try
                {
                    int y = Integer.parseInt(x);
                    
                    if (y == 1)
                    {
                        header = 2;
                        error = false;
                    }

                    else if (y == 2)
                    {
                        header = 1;
                        error = false;
                    }

                    else
                    {
                        error = true;
                    }
                }

                catch (Exception e)
                {
                    error = true;
                }

            } while (error);

            do
            {
                System.out.println("" + (error ? "Error. " : "") + "Input amount to add or withdraw.");
                String x = scanner.nextLine();

                try
                {
                    toSend = Integer.parseInt(x);

                    if (toSend > 10000 && header == 1)
                    {
                        System.out.println("Insufficient funds.");
                        error = true;
                    }

                    else if (header == 2 && toSend > 30000)
                    {
                        System.out.println("Max deposit = $30000.");
                        error = true;
                    }

                    else
                    {
                        error = false;
                    }
                }

                catch (Exception e)
                {
                    error = true;
                }

            } while (error);
            
            byte[] bytes = new byte[Integer.BYTES];
            int length = bytes.length;

            for (int i = 0; i < length; i++) 
            {
                bytes[length - i - 1] = (byte) (toSend & 0xFF);
                toSend >>= 8;
            }

            byte[] send = new byte[bytes.length+1];

            send[0] = header.byteValue();
            
            for (int i = 1; i < send.length; i++)
            {
                send[i] = bytes[i-1];
            }

            DatagramPacket packet = new DatagramPacket(send, 0, send.length);
            packet.setSocketAddress(toForwarderOne);
            
            socket.send(packet);
            System.out.println("Packet sent.");

            byte[] ack = new byte[1024];
            DatagramPacket zPacket = new DatagramPacket(ack, ack.length);
            socket.receive(zPacket);
            byte[] a = zPacket.getData();

            System.out.println("Response received: " + new String(a));
        } catch (SocketTimeoutException exc)
        {
            System.out.println("Timeouts occurred. Restart application.");
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        run();
    }
}
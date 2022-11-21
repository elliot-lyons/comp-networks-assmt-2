import java.net.*;

public class Forwarder
{
    static final int DEFAULT_FOR_PORT = 6;
	static final String DEFAULT_DST_NODE = "Controller";
	InetSocketAddress dstAddress;

    public static void run(int defaultPort)
    {
        System.out.println("Waiting for contact...");
        
        try
        {
            DatagramSocket socket = new DatagramSocket(defaultPort);
            socket.setSoTimeout(20*1000);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);
            SocketAddress original = packet.getSocketAddress();

            System.out.println("Packet received.");
            
            InetSocketAddress toController = new InetSocketAddress(DEFAULT_DST_NODE, DEFAULT_FOR_PORT);
            DatagramSocket controller = new DatagramSocket(DEFAULT_FOR_PORT);
            controller.setSoTimeout(1);

            byte[] control = new byte[1];
            control[0] = sent[0];

            DatagramPacket xPacket = new DatagramPacket(control, 0, control.length);
            
            xPacket.setSocketAddress(toController);
            System.out.println("Sending to control");
            controller.send(xPacket);

            byte[] fromControl = new byte[1024];
            DatagramPacket yPacket = new DatagramPacket(fromControl, fromControl.length);
            controller.receive(yPacket);
            System.out.println("Received from control");
            controller.close();

            byte[] received = yPacket.getData();

            byte[] r = new byte[received.length - 1];

            for (int i = 0; i < r.length; i++)
            {
                r[i] = received[i+1];
            }

            int nextPort = received[0];

            String nextNode = new String(r);
            System.out.println("Next node: " + nextNode);

            System.out.println("Next port: " + nextPort);

            InetSocketAddress toForwarder = new InetSocketAddress(nextNode, nextPort);
            DatagramSocket forward = new DatagramSocket(nextPort);
            forward.setSoTimeout(15*1000);

            packet.setSocketAddress(toForwarder);
            forward.send(packet);

            System.out.println("Forwarding packet.");
            
            byte[] ack = new byte[1024];
            DatagramPacket zPacket = new DatagramPacket(ack, ack.length);
            forward.receive(zPacket);
            byte[] a = zPacket.getData();

            String acknowledge = new String(a);
            System.out.println(acknowledge);
            System.out.println("Sending back to client." );

            zPacket.setSocketAddress(original);
            socket.send(zPacket);

            forward.close();
            socket.close();
        } catch (SocketTimeoutException exc)
        {
            System.out.println("Timeout occurred.");
        } 
        
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Program completed.");
    }
}
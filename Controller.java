import java.util.*;
import java.io.*;
import java.net.*;

public class Controller 
{
    static boolean run = true;
    
    static final int FORWARDER_ONE = 1;
    static final int FORWARDER_TWO = 2;
    static final int FORWARDER_THREE = 4;

    static final int CLIENT_ONE = 0;
    static final int CLIENT_TWO = 3;
    static final int CLIENT_THREE = 5;
    
    static final int CONTROLLER = 6;

    public static void run()
    {
        // while (run)
        // {
            // try
            // {
            //     DatagramSocket socket = new DatagramSocket(ONE);
            //     byte[] sent = new byte[1024];
            //     DatagramPacket packet = new DatagramPacket(sent, sent.length);
            //     socket.receive(packet);
            //     byte[] data = packet.getData();
            //     byte[] forwarder = new byte[1];
            //     forwarder[0] = (byte) ONE;

            //     switch (data[0])
            //     {
            //         case (0):
            //         {
            //             break;
            //         }

            //         case (1):
            //         {
            //             forwarder[0] = (byte) TWO;
            //             break;
            //         }

            //         case (2):
            //         {
            //             forwarder[0] = (byte) THREE;
            //             break;
            //         }

            //         default:
            //         {
            //             System.out.println("Error");
            //         }
            //     }

            //     packet = new DatagramPacket(forwarder, 0, forwarder.length);
            //     socket.send(packet);

            //     socket = new 
                
            // } catch (Exception e)
            // {
            //     e.printStackTrace();
            // }


        for (int i = 0; i < 3; i++)
        {
            receive(i);
        }
    }

    public static void receive(int current)
    {
        try
            {
                System.out.println("Waiting for contact...");
                DatagramSocket socket = new DatagramSocket(CONTROLLER);
                byte[] sent = new byte[1024];
                DatagramPacket fromForwarder = new DatagramPacket(sent, sent.length);
                socket.receive(fromForwarder);

                System.out.println("Receieved.");

                byte[] data = fromForwarder.getData();
                int dest = data[0];
                int nextAddress = -1;
                String nextNode = "";

                switch (current)
                {
                    case (1):
                    {
                        if (dest == 1)
                        {
                            nextAddress = CLIENT_TWO;
                            nextNode = "ClientTwo";
                        }

                        else
                        {
                            nextAddress = FORWARDER_THREE;
                            nextNode = "ForwarderThree";
                        }
                        break;
                    }

                    case (2):
                    {
                        if (dest == 2)
                        {
                            nextAddress = CLIENT_THREE;
                            nextNode = "ClientThree";
                        }

                        else
                        {
                            System.out.println("Error");
                        }

                        break;
                    }

                    default:
                    {
                        nextAddress = FORWARDER_TWO;
                        nextNode = "ForwarderTwo";
                    }
                }

                byte[] x = nextNode.getBytes();
                System.out.println("N: " + (byte) nextAddress);
                
                byte[] forwarder = new byte[x.length  + 1];

                forwarder[0] = (byte) nextAddress;

                int index = 1;
                
                while (index <= x.length)
                {
                    forwarder[index] = x[index - 1];
                    index++;
                }

                DatagramPacket packet = new DatagramPacket(forwarder, 0, forwarder.length);
                packet.setSocketAddress(fromForwarder.getSocketAddress());
                socket.send(packet);

                socket.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void main(String[] args) {
        run();
    }
}
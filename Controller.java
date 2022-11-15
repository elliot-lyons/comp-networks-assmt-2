import java.util.*;
import java.io.*;
import java.net.*;

public class Controller 
{
    static boolean run = true;
    
    static final int FORWARDER_ONE = 50001;
    static final int FORWARDER_TWO = 50002;
    static final int FORWARDER_THREE = 50004;

    static final int CLIENT_ONE = 50000;
    static final int CLIENT_TWO = 50003;
    static final int CLIENT_THREE = 50005;
    
    static final int CONTROLLER = 50006;

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
                Integer nextAddress = 0;
                String nextNode = "";

                switch (data[0])
                {
                    case (0):
                    {
                        if (current == 0)
                        {
                            nextAddress = CLIENT_ONE;
                            nextNode = "ClientOne";
                        }

                        else
                        {
                            nextAddress = FORWARDER_ONE;
                            nextNode = "ForwarderOne";
                        }

                        break;
                    }

                    case (1):
                    {
                        if (current == 1)
                        {
                            nextAddress = CLIENT_TWO;
                            nextNode = "ClientTwo";
                        }

                        else
                        {
                            nextAddress = FORWARDER_TWO;
                            nextNode = "ForwarderTwo";
                        }
                        break;
                    }

                    case (2):
                    {
                        if (current == 2)
                        {
                            nextAddress = CLIENT_THREE;
                            nextNode = "ClientThree";
                        }

                        else
                        {
                            nextAddress = FORWARDER_THREE;
                            nextNode = "ForwarderThree";
                        }

                        break;
                    }

                    default:
                    {
                        System.out.println("Error");
                    }
                }

                byte[] x = nextNode.getBytes();
                String next = "" + nextAddress;
                System.out.println("N: " + Integer.parseInt(next));
                byte[] y = next.getBytes();
                
                byte[] forwarder = new byte[x.length + y.length + 1];

                forwarder[0] = (byte) x.length;

                int index = 1;
                
                while (index <= x.length)
                {
                    forwarder[index] = x[index - 1];
                    index++;
                }

                while (index <= x.length + y.length)
                {
                    forwarder[index] = y[index - x.length - 1];
                    index++;
                }

                System.out.println(forwarder.length);

                System.out.println(forwarder.toString());
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
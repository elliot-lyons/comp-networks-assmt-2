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
    static boolean send = false;

    public static void run()
    {
        for (int i = 0; i < 3 && !send; i++)
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
                socket.setSoTimeout(30*1000);
                byte[] sent = new byte[1024];
                DatagramPacket fromForwarder = new DatagramPacket(sent, sent.length);
                socket.receive(fromForwarder);

                System.out.println("Receieved.");

                byte[] data = fromForwarder.getData();
                int dest = data[0];         // the header determines where the packet needs to go
                int nextAddress = -1;
                String nextNode = "";

                switch (current)        // forwarder determining next step for forwarders
                {
                    case (1):
                    {
                        if (dest == 1)
                        {
                            nextAddress = CLIENT_TWO;
                            nextNode = "ClientTwo";
                            send = true;
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
                            send = true;
                        }

                        else
                        {
                            System.out.println("Error");            // shouldn't reach here on current network
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
            } catch (SocketTimeoutException exc)
            {
                System.out.println("Timeouts occurred. Restart application.");
                send = true;
            } 
            
            catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void main(String[] args) {
        run();
    }
}
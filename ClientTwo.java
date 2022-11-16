import java.net.*;

public class ClientTwo 
{
    static final int DEFAULT_PORT = 3;

    public static void run()
    {
        System.out.println("Waiting for contact...");

        try
        {
            DatagramSocket socket = new DatagramSocket(DEFAULT_PORT);
            socket.setSoTimeout(20*1000);

            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);
            socket.close();

            System.out.println("Packet received.");

            byte[] res = packet.getData();

            byte[] data = new byte[res.length-1];

            for (int i = 0; i < data.length; i++)
            {
                data[i] = res[i+1];
            }

            int value = 0;
            boolean okVal = true;
            
            for (int i = 0; i < data.length && okVal; i++) 
            {
                int prev = value;
                byte b = data[i];
                value = (value << 8) + (b & 0xFF);
                
                if (value > 30000)
                {
                    value = prev;
                    okVal = false;
                }
            }

            int bal = 10000 + value;

            System.out.println("$" + value + " deposited. New balance: $" + bal + ".");
        }   catch (SocketTimeoutException e)
        {
            System.out.println("Timeout occurred. Withdrawal wanted.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Program completed.");
    }

    public static void main(String[] args)
    {
       run(); 
    }    
}
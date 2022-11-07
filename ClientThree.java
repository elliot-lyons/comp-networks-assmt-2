import java.net.*;

public class ClientThree 
{
    static final int DEFAULT_PORT = 50005;

    public static void run()
    {
        try
        {
            DatagramSocket socket = new DatagramSocket(DEFAULT_PORT);
            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);
            socket.close();
            
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
                
                if (value > 10000)
                {
                    value = prev;
                    okVal = false;
                }
            }
            
            int bal = 10000 - value;

            System.out.println("$" + value + " added. New balance: $" + bal);

        }   catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
       run(); 
    }    
}
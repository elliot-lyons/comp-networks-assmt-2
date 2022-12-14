import java.net.*;

public class ClientThree 
{
    static final int DEFAULT_PORT = 5;

    public static void run()
    {
        System.out.println("Waiting for contact...");
    
        try
        {
            DatagramSocket socket = new DatagramSocket(DEFAULT_PORT);
            socket.setSoTimeout(1000*10);

            byte[] sent = new byte[1024];
            DatagramPacket packet = new DatagramPacket(sent, sent.length);
            socket.receive(packet);

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
                
                    if (value > 10000)
                    {
                        value = prev;
                        okVal = false;
                    }
                }

                int bal = 10000 - value;
                
                String response = "$" + value + " withdrawn. New balance: $" + bal + ".";
                System.out.println(response + " Responding to client.");

                byte[] toClient = response.getBytes();      // sednding ack to forwarder
                DatagramPacket toC = new DatagramPacket(toClient, toClient.length);
                toC.setSocketAddress(packet.getSocketAddress());
                socket.send(toC);
                socket.close();
                
        } catch (SocketTimeoutException e)
        {
            System.out.println("Timeout occurred. Deposit wanted.");
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
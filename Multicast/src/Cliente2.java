import java.io.IOException;
import java.net.*;

public class Cliente2 {
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    private NetworkInterface netIf;
    private InetSocketAddress group;

    public Cliente2(int portValue, String strIp) throws IOException {
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp, portValue);
    }

    public void runCliente2() throws IOException {
        DatagramPacket packet;
        byte[] receivedData = new byte[1024];

        socket.joinGroup(group, netIf);
        System.out.printf("Conectado a %s:%d%n", group.getAddress(), group.getPort());

        while (true) {
            packet = new DatagramPacket(receivedData, receivedData.length);
            socket.receive(packet);
            String receivedMessage = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Palabra recibida: " + receivedMessage);
        }
    }

    public static void main(String[] args) throws IOException {
        Cliente2 cliente2 = new Cliente2(49152, "224.0.0.0");
        cliente2.runCliente2();
        System.out.println("Detenido!");
    }
}

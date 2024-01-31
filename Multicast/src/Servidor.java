import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Servidor {
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    private boolean continueRunning = true;
    private Random random = new Random();
    private String[] palabras = {"Phoenix", "Jett", "Viper", "Sova", "Cypher", "Brimstone", "Sage", "Breach","Omen"};
    private Map<String, Integer> contadorPalabras = new HashMap<>();

    public Servidor(int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
    }
    public void contarPalabra(String palabra) {
        contadorPalabras.put(palabra, contadorPalabras.getOrDefault(palabra, 0) + 1);
    }

    public void enviarPalabra(String palabra) throws IOException {
        byte[] sendingData = palabra.getBytes();
        DatagramPacket packet = new DatagramPacket(sendingData, sendingData.length, multicastIP, port);
        socket.send(packet);
    }

    public void runServidor() throws IOException {
        try {
            while (continueRunning) {
                String palabra = palabras[random.nextInt(palabras.length)];

                contarPalabra(palabra);
                enviarPalabra(palabra);

                // Mostrar el contador de palabras
                System.out.println("Contador de palabras: " + contadorPalabras);

                // Envía una palabra cada 2 segundos
                Thread.sleep(2000);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Servidor servidor = new Servidor(49152, "224.0.0.0");
        servidor.runServidor();
        System.out.println("Detenido!");
    }
}

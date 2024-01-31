import java.util.Random;

public class Palabra {
    private int max;
    private Random random;

    public Palabra(int max) {
        this.max = max;
        this.random = new Random();
    }

    public int agarrarPalabra() {
        return random.nextInt(max) + 1;
    }
}

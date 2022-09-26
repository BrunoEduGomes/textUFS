
import java.util.Random;

public class Diploma {
    public boolean local;
    public int aleatorio;       

    public Diploma() {
        local = false;
        aleatorio = criaAleatorio();
    }

    public int getNumber() {
        return aleatorio;
    }
    public boolean qualLocal() {
        return local;
    }

    public void mudalocal() {
        local = true;
        }

    public int criaAleatorio() {
        Random gerador = new Random();
        int k = 0;
        for (int i = 0; i < 1; i++) {
            return k = gerador.nextInt(6) + 1;
        }
        return k;
    }

}

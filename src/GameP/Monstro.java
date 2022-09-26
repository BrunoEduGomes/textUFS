
import java.util.Random;

public class Monstro {
    
    private int lugarInicial;
    public int defMovimento;
    public int lugarMonstro;
    public int vidaOUmorte;

    public Monstro(){
        lugarInicial = criaAleatorio2();
        defMovimento = 0;
        lugarMonstro = lugarInicial;
        vidaOUmorte = 1;
    }


    public int criaAleatorio2() {
        Random gerador = new Random();
        int k = 0;
        for (int i = 0; i < 1; i++) {
            return k = gerador.nextInt(7) + 1;
        }
        return k;
    }
    
    public void criaAleatorio3() {
        Random gerador = new Random();
        int k = 0;
        for (int i = 0; i < 1; i++) {
            k = gerador.nextInt(5) + 1;
        }
        defMovimento = k;
    }

    public int monsterRoom() {
        return lugarMonstro;
    }

    public int morreu() {
        return vidaOUmorte = 0;
    }

    public int nextLugar1(){
        return lugarMonstro = lugarMonstro + 1;
    }

    public int nextLugar2(){
        return lugarMonstro = lugarMonstro -1;
    }

    public int nextLugar3(){
        return lugarMonstro = lugarMonstro + 1 - 1;
    }

}

/*
    Desenvolva um programa em Java composto de N threads para o problema do jantar dos filósofos.
    Cada thread deve representar o comportamento de um filósofo, sendo ele:
        Espera um tempo aleatório (0-1s)
        Pega um garfo adjacente
        Espera um tempo aleatório (0-1s)
        Pega outro garfo adjacente
        Espera um tempo aleatório (0-1s)
        Libera os garfos
    A cada filósofo que conseguiu/liberou os garfos o programa deve exibir a seguinte saída formatada
*/

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

class Filosofo extends Thread {

    private String nome;
    private Semaphore garfoEsquerda;
    private Semaphore garfoDireita;

    public Filosofo(String nome, Semaphore garfoEsquerda, Semaphore garfoDireita) {
        this.nome = nome;
        this.garfoEsquerda = garfoEsquerda;
        this.garfoDireita = garfoDireita;
    }

    private void pegarGarfo(Semaphore garfo) throws InterruptedException {
        garfo.acquire();
        System.out.printf("Filósofo %s pegou um garfo.%n", this.nome);
    }

    private void liberarGarfo(Semaphore garfo) {
        garfo.release();
        System.out.printf("Filósofo %s liberou um garfo.%n", this.nome);
    }

    public void comer() throws InterruptedException {
        this.pegarGarfo(this.garfoEsquerda);
        this.pensar();
        this.pegarGarfo(this.garfoDireita);
        this.pensar();
        System.out.printf("Filoso %s comendo...%n", this.nome);

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        this.liberarGarfo(this.garfoDireita);
        this.liberarGarfo(this.garfoDireita);
        System.out.printf("Filoso %s terminou de comer...%n", this.nome);
    }

    public void pensar() {
        //System.out.printf("Filoso %s pensando...%n", this.nome);
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        while(true){
            this.pensar();
            try {
                this.comer();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Implementacao9 {

    public static void CriarThreads(int numFilosofos, String[] nomesFilosofos) throws InterruptedException {
        Filosofo[] threads = new Filosofo[numFilosofos];
        Semaphore[] garfos = new Semaphore[numFilosofos];

        for (int i = 0; i < numFilosofos; i++) {
            garfos[i] = new Semaphore(1, true);
        }

        for (int i = 0; i < numFilosofos; i++) {
            Semaphore garfoEsquerdo = garfos[i];
            Semaphore garfoDireito = garfos[(i + 1) % numFilosofos];

            // Para evitar deadlock, o último filósofo deve pegar os garfos ao contrario
            if (i == numFilosofos - 1) {
                threads[i] = new Filosofo(nomesFilosofos[i], garfoDireito, garfoEsquerdo);
            } else {
                threads[i] = new Filosofo(nomesFilosofos[i], garfoEsquerdo, garfoDireito);
            }

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String[] nomes = {"Leonardo", "Michelangelo", "Donatello", "Raphael", "Splinter"};
        CriarThreads(nomes.length, nomes);
    }

}

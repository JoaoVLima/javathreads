/*
    Implemente um programa multithread que:
    1. Aloca um vetor double com 200 milhões de entradas
    2. Inicializa o vetor alocado anteriormente com valores aleatórios
    3. Após a inicialização exibe uma mensagem ‘Encerrou inicalizacao’
    4. Computa e exibe a quantidade de valores no vetor que são maiores que 0.25 e menores que 0.75

    Calcule o tempo de execução variando a quantidade de threads de 1 a 4

    Cuidado com problema de memoria.
*/

import java.util.Random;

class MinhaThread4 extends Thread {
    private volatile double[] array;
    private int array_size;
    public int acc;

    public MinhaThread4(int array_size) {
        this.array_size = array_size;
    }

    public void run() {
        this.array = new double[this.array_size];

        Random rand = new Random();
        for (int i = 0; i < this.array_size; i++) {
            this.array[i] = rand.nextDouble();
        }
        System.out.println("Encerrou inicalizacao");

        this.acc = 0;
        for (int i = 0; i < this.array_size; i++) {
            if (this.array[i] > 0.25 & this.array[i] < 0.75){
                this.acc++;
            }
        }

    }
}

public class Implementacao4 {

    public static void CriarThreads(int numThreads, int array_size) throws InterruptedException {
        MinhaThread4[] threads = new MinhaThread4[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new MinhaThread4(array_size/numThreads);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // Soma os valores
        int acc = 0;
        for (MinhaThread4 thread : threads) {
            acc+=thread.acc;
        }
        System.out.println("acc: " + acc);
    }

    public static void main(String[] args) throws InterruptedException {
        long inicio = System.currentTimeMillis();

        CriarThreads(4, 200_000_000);

        long fim = System.currentTimeMillis();
        long duracao = (fim - inicio);
        System.out.println(duracao + "ms");
    }

}
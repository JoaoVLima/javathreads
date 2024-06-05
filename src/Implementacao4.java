/*
    Implemente um programa multithread que:
    1. Aloca um vetor double com 200 milhões de entradas
    2. Inicializa o vetor alocado anteriormente com valores aleatórios
    3. Após a inicialização exibe uma mensagem ‘Encerrou inicalizacao’
    4. Computa e exibe a quantidade de valores no vetor que são maiores que 0.25 e menores que 0.75
*/

class MinhaThread4 extends Thread {

    private String nome;

    public MinhaThread4(String nome) {
        this.nome = nome;
    }

    public void run() {
        System.out.println("Bem Vindo!");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Adeus!");
    }
}

public class Implementacao4 {

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new MinhaThread4("t1");
        Thread t2 = new MinhaThread4("t2");

        t1.start();
        t1.join();
        t2.start();
        t2.join();

        System.out.println("Fim do programa!");

    }

}

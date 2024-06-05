/*
    Implemente uma Thread que realize as seguintes operações no método Run:
    Imprime na tela: Bem vindo!
    Dorme por 5 segundos
    Imprime na tela: Adeus!
    O método main deve instanciar, iniciar e aguardar a execução dessa Thread
    No final do método main, deve ser impresso: “Fim do programa!”
*/

class MinhaThread3 extends Thread {

    private String nome;

    public MinhaThread3(String nome) {
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

public class Implementacao3 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new MinhaThread3("t1");
        Thread t2 = new MinhaThread3("t2");

        t1.start();
        t1.join();
        t2.start();
        t2.join();

        System.out.println("Fim do programa!");

    }

}

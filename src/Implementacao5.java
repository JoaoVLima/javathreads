/*
    Implemente uma classe chamada Contador, que tem um atributo valor e dois métodos:
        Incrementar: incrementa o atributo valor;
        Imprimir: imprime o atributo valor;
        Implemente uma Thread, que recebe por parâmetro um objeto Contador e invoca o método Incrementar 10.000 vezes
    Crie duas instâncias dessa thread e execute 3 vezes.
    Imprima o valor do Contador após a execução das threads

    Tem que usar synchronized pra garantir que o valor final será 20_000.
*/

class Contador implements Runnable {

    private volatile int valor;

    public Contador() {
        this.valor = 0;
    }

    public synchronized void incrementar() {
        this.valor++;
    }

    public void imprimir() {
        System.out.println(this.valor);
    }

    public void run() {
        for (int i = 0; i < 10_000; i++) {
            this.incrementar();
        }
    }
}

public class Implementacao5 {

    public static void main(String[] args) throws InterruptedException {

        Contador runable = new Contador();
        Thread t1 = new Thread(runable);
        Thread t2 = new Thread(runable);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        runable.imprimir();

    }

}

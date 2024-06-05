/*
    Implemente uma classe chamada Conta, que tem um atributo double denominado valor e três métodos:
        Deposito: recebe um valor e o soma ao saldo
        Saque: recebe um valor e o subtrai ao saldo
        Saldo: retorna o saldo
    Implemente uma Thread denominada depósito que efetua depósitos aleatórios a cada 1 segundo
    Implemente uma Thread denominada Saque que efetua saques aleatórios, se houver saldo, a cada 1 segundo
    Execute três threads de saques e duas threads de depósitos, a cada operação você deve exibir o saldo
*/

import java.util.Random;

class Conta {

    private volatile double valor;

    public Conta() {
        this.valor = 0.00;
    }

    public synchronized void deposito(double valor) {
        this.valor += valor;
    }

    public synchronized void saque(double valor) {
        this.valor -= valor;
    }

    public synchronized double get_saldo() {
        return this.valor;
    }

    public void imprimir() {
        System.out.println(this.valor);
    }


}

class Deposito extends Thread {

    private Conta conta;
    private String thread_name;

    public Deposito(Conta conta, String thread_name) {
        this.conta = conta;
        this.thread_name = thread_name;
    }

    public void run() {
        while(true){
            Random rand = new Random();
            double valor_aleatorio = rand.nextDouble();

            System.out.printf("DEPOSITO %s ira depositar %f na conta%n", this.thread_name, valor_aleatorio);

            this.conta.deposito(valor_aleatorio);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

class Saque extends Thread {

    private Conta conta;
    private String thread_name;

    public Saque(Conta conta, String thread_name) {
        this.conta = conta;
        this.thread_name = thread_name;
    }

    public void run() {
        while(true) {
            Random rand = new Random();
            double valor_aleatorio = rand.nextDouble();
            double saldo_conta = this.conta.get_saldo();

            System.out.printf("SAQUE %s ira sacar %f da conta com saldo: %f%n", this.thread_name, valor_aleatorio, saldo_conta);

            if (saldo_conta > 0) {
                this.conta.saque(valor_aleatorio);
                System.out.printf("SAQUE %s consegui sacar, saldo atual: %f%n", this.thread_name, saldo_conta);
            } else {
                System.out.printf("SAQUE %s nao consegui sacar %f%n", this.thread_name, valor_aleatorio);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Implementacao6 {

    public static void main(String[] args) throws InterruptedException {

        Conta conta = new Conta();
        Thread t1 = new Deposito(conta, "Thread 1");
        Thread t2 = new Deposito(conta, "Thread 2");
        Thread t3 = new Saque(conta, "Thread 3");
        Thread t4 = new Saque(conta, "Thread 4");
        Thread t5 = new Saque(conta, "Thread 5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

}

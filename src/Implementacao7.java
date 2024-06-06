/*
    Desenvolva um programa em Java composto de duas threads principais:
        Produtor e Consumidor. O Produtor é responsável por gerar um número aleatório
        a cada X segundos e depositar em uma fila única no programa, a cada depósito
        o número deve ser impresso. Já o consumidor é responsável por retirar a cada Y
        segundos o primeiro elemento da fila, a cada retirada o número deve ser impresso.

    Requisitos:
        A fila deve ser única no programa, ou seja, compartilhada entre as diversas threads;
        A fila tem um tamanho limitado, só aceita no máximo 10 números;
        Caso a fila esteja cheia, o Produtor deve aguardar até que seja possível inserir um número;
        Caso a fila esteja vazia, o Consumidor deve aguardar até que seja possível retirar um número;
        Cada thread deve ter um ID único, que deve ser utilizado quando for imprimir algum valor (retirar/inserir elementos na fila). Por exemplo: Produtor 02 – Inseriu 14 na fila.
        O programa deve executar com 03 threads de produtor e 02 de consumidor.
        O programa deve executar com qualquer valor de X e Y.

        Faça uso do synchronized, wait e notify.
*/

import java.util.Random;

class Fila<T> {
    private volatile T[] fila;
    private volatile int inicio;
    private volatile int fim;
    private volatile boolean vazia;

    public Fila(int tamanho) {
        this.fila = (T[]) new Object[tamanho];
        this.inicio = 0;
        this.fim = 0;
        this.vazia = true;
    }

    public boolean isCheia() {
        return this.inicio == this.fim && !this.vazia;
    }

    public boolean isVazia() {
        return this.vazia;
    }

    public synchronized void adicionar(T valor) {
        if (isCheia()) {
            throw new IllegalStateException("Fila cheia");
        }
        this.fila[this.fim] = valor;
        this.fim = (this.fim + 1) % this.fila.length;
        this.vazia = false;
    }

    public synchronized T remover() {
        if (this.vazia) {
            throw new IllegalStateException("Fila vazia");
        }
        T valor = this.fila[inicio];
        this.inicio = (this.inicio + 1) % this.fila.length;
        this.vazia = this.inicio == this.fim;
        return valor;
    }

    public void imprimir() {
        System.out.print("[");
        int inicio = this.inicio;
        if (this.isCheia()) {
            System.out.print(this.fila[this.inicio]);
            inicio++;
        }
        for (int i = inicio; i != this.fim; i = (i + 1) % this.fila.length) {
            if (i != inicio){
                System.out.print(", ");
            }
            System.out.print(this.fila[i]);
        }
        System.out.println("]");
    }

    public int getTamanho() {
        System.out.println(this.inicio + ", " + this.fim);
        if (this.vazia)
            return 0;
        if (this.isCheia())
            return this.fila.length;
        if (this.fim > this.inicio)
            return this.fim - this.inicio;

        return this.fim + this.fila.length - this.inicio;
    }
}

class Produtor extends Thread {

    private Fila<Integer> fila;
    private String thread_name;

    public Produtor(Fila<Integer> fila, String thread_name) {
        this.fila = fila;
        this.thread_name = thread_name;
    }

    public void run() {
        while(true){
            Random rand = new Random();
            int numero_randomico = rand.nextInt(1000);

            if (this.fila.isCheia()){
                System.out.println("fila cheia, nao pode adicionar");
                synchronized (this.fila) {
                    try {
                        this.fila.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            this.fila.adicionar(numero_randomico);
            synchronized (this.fila) {
                this.fila.notify();
            }
            System.out.printf("Produtor %s produziu %d%n", this.thread_name, numero_randomico);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

class Consumidor extends Thread {

    private Fila<Integer> fila;
    private String thread_name;

    public Consumidor(Fila<Integer> fila, String thread_name) {
        this.fila = fila;
        this.thread_name = thread_name;
    }

    public void run() {
        while(true) {
            if (this.fila.isVazia()){
                System.out.println("fila vazia, nao pode remover");
                synchronized (this.fila) {
                    try {
                        this.fila.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            int numero_consumido = this.fila.remover();
            synchronized (this.fila) {
                this.fila.notify();
            }
            System.out.printf("Consumidor %s consumiu %d%n", this.thread_name, numero_consumido);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Implementacao7 {

    public static void main(String[] args) throws InterruptedException {
        Fila<Integer> fila = new Fila<Integer>(10);

        Thread t1 = new Produtor(fila, "Thread 1");
        Thread t2 = new Consumidor(fila, "Thread 2");

        t1.start();
        t2.start();
    }

}

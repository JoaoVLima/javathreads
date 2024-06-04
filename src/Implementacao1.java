/*
    Implemente uma classe estendida de Thread que receba no construtor um nome
    Instancie dois objetos dessa classe
    A execução de cada thread deve imprimir o nome da thread somado a um contador
    O valor máximo do contador é 1000
    Execute o programa pelo menos 3 vezes
 */

class ContadorThread extends Thread {

    private String nome;

    public ContadorThread(String nome) {
        this.nome = nome;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(this.nome + "\t" + i);
        }
    }
}

public class Implementacao1 {

    public static void main(String[] args) {

        Thread t1 = new ContadorThread("t1");
        Thread t2 = new ContadorThread("t2");

        t1.start();
        t2.start();

    }

}

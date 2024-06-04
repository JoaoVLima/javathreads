/*
    Implemente uma classe que implementa a interface
    Runnable e receba no construtor um nome
    Instancie dois objetos dessa classe
    A execução de cada thread deve imprimir o nome da thread somado a um contador local do objeto
    O valor máximo do contador é 1000
    Execute o programa pelo menos 3 vezes
*/

class ContadorRunable extends Thread implements Runnable {

    private String nome;

    public ContadorRunable(String nome) {
        this.nome = nome;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(this.nome + "\t" + i);
        }
    }
}

public class Implementacao2 {

    public static void main(String[] args) {

        Thread t1 = new ContadorRunable("t1");
        Thread t2 = new ContadorRunable("t2");

        t1.start();
        t2.start();

    }

}

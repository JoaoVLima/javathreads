/*
    Implemente uma classe que implementa a interface
    Runnable e receba no construtor um nome
    Instancie dois objetos dessa classe
    A execução de cada thread deve imprimir o nome da thread somado a um contador local do objeto
    O valor máximo do contador é 1000
    Execute o programa pelo menos 3 vezes
*/

class ContadorRunable implements Runnable {

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

    public static void main(String[] args) throws InterruptedException {

        ContadorRunable t1_runable = new ContadorRunable("t1");
        Thread t1 = new Thread(t1_runable);

        ContadorRunable t2_runable = new ContadorRunable("t2");
        Thread t2 = new Thread(t2_runable);


        t1.start();
        t2.start();
    }

}

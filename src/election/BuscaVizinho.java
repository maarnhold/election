/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package election;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class BuscaVizinho extends Thread {

    private String ip;
    private int PORTA;
    HashMap<String, Integer> hp;

    public BuscaVizinho(String ip, int PORTA, HashMap hp) {
        this.ip = ip;
        this.PORTA = PORTA;
        this.hp = hp;
        this.start();
    }

    public void run() {
        Socket cliente;
        try {
            cliente = new Socket(ip, PORTA);
            System.out.println("Tentando conectar " + ip + " na porta " + PORTA);

            if (cliente.isConnected()) {
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                String mensagem = "Buscando Hosts";
                System.out.println(entrada.readObject().toString());
                saida.writeObject(mensagem);
                saida.flush();
                
                if (!hp.containsKey(ip)) {
                    hp.put(ip, 1);
                    System.out.println("Encontrei o node " + ip);
                }
            }// else {
            //    if (hp.containsKey(ip)) {
            //        hp.remove(ip);
            //    }
            //}
            
            this.sleep(1500);
            this.interrupt();
        } catch (Exception ex) {
        }

    }
}

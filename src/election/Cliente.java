/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package election;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

    public final int PORTA = 10000;

    public Cliente() {

        try {
            Socket cliente = new Socket("127.0.0.1", PORTA);
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

            String mensagem = (String) entrada.readObject();
            System.out.println("O servidor disse: " + mensagem);
            do {
                System.out.print("..: ");
                saida.writeObject(mensagem);
                saida.flush();
                //lendo a mensagem enviada pelo servidor
                mensagem = (String) entrada.readObject();
                System.out.println("Servidor>> " + mensagem);

            } while (!mensagem.equals("fim"));
            saida.close();
            entrada.close();
            cliente.close();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}

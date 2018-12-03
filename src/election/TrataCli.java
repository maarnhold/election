/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package election;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

class TrataCli extends Thread {

    private Socket cliente = null;
    private HashMap<String, Integer> hp = new HashMap();
    String lider = "";
    private int maior = 0;
    ArrayList<Integer> ips = new ArrayList<Integer>();

    public TrataCli(Socket cliente) {
        this.cliente = cliente;
        this.start();
    }

    @Override
    public void run() {
        try {

            String resposta = "";

            String mensagem = "\n Conectado ao Servidor";
            boolean sair = false;
            while (!sair) {

                System.out.println("Cliente : " + cliente.getInetAddress().getHostAddress());
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

                saida.flush();
                saida.writeObject(mensagem);
                do {
                    VerificarTopologia vt = new VerificarTopologia(hp);
                    Thread.sleep(300);

                    ips.clear();
                    //System.out.println("Lista de nós do cluster:");
                    for (HashMap.Entry<String, Integer> entry : hp.entrySet()) {
                        System.out.println(entry.getKey() + " : " + entry.getValue());

                        ips.add(Integer.parseInt(entry.getKey().substring(10)));

                    }
                    if (ips.size() == 1) {
                        maior = ips.get(0);
                    }
                    if (ips.size() > 1) {
                        for (int i = 0; i < (ips.size()-1); i++) {
                            int w = ips.get(i);
                            int z = ips.get(i + 1);
                            if (z > w) {
                                maior = z;
                            } else {
                                maior = w;
                            }
                        }
                    }
                    lider = "192.168.1." + maior;
                    System.out.println(">>>>>>>>>>>O líder é: " + lider);
                    saida.writeObject("O lider é: " + lider);
                    //System.out.println(maior);

                } while (!resposta.equals("fim"));

                String msg = "fim";
                sair = true;
                saida.writeObject(msg);
                System.out.println("Conexão encerrada");
                cliente.close();
                saida.close();
                entrada.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package election;

import java.util.HashMap;

public class VerificarTopologia extends Thread {

    public final int PORTADISCOVER = 12000;
    HashMap<String, Integer> hp;

    public VerificarTopologia(HashMap hp) {
        this.hp = hp;
        this.start();
    }

    public void run() {
        for (int x = 1; x <= 253; x++) {
            //new BuscaVizinho("10.3.6." + x, PORTADISCOVER, hp);
            new BuscaVizinho("192.168.1." + x, PORTADISCOVER, hp);
        }

    }
}

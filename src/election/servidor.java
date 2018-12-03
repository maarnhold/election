package election;

import java.net.ServerSocket;
import java.net.Socket;

class servidor implements Runnable {

    public final int PORTA = 10000;
    public final int PORTADISCOVER = 12000;

    public boolean lider = true;

    public servidor() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(PORTADISCOVER);

            while (true) {
                Socket client = server.accept();
                TrataCli trataCli = new TrataCli(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String argv[]) throws Exception {
        servidor servidor = new servidor();
    }
}

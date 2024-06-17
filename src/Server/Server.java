package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static int port = 8000;
    private static ServerSocket server;
    private static ArrayList<Socket> players = new ArrayList<>();


    public static void main(String[] args) {
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loop();
    }
    public static void loop() {
        ExecutorService service = Executors.newCachedThreadPool();
        while (true) {
            try {
                System.out.println("searching for a player...");
                players.add(server.accept());
                System.out.println("found player!");
                if (players.size() == 2) {
                    service.execute(new Match(players.get(0), players.get(1)));
                    players.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

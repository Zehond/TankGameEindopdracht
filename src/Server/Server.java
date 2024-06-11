package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static int port = 8000;
    private static ServerSocket server;
    private static Socket player1;
    private static Socket player2;
    private static DataInputStream in1;
    private static DataOutputStream out1;
    private static DataInputStream in2;
    private static DataOutputStream out2;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(port);
            System.out.println("searching players");
            player1 = server.accept();
            in1 = new DataInputStream(player1.getInputStream());
            out1 = new DataOutputStream(player1.getOutputStream());
            System.out.println("player1 connected");
            player2 = server.accept();
            in2 = new DataInputStream(player2.getInputStream());
            out2 = new DataOutputStream(player2.getOutputStream());
            System.out.println("player2 connected");
        } catch (Exception e) {
            e.printStackTrace();
        }

        loop();
    }


    public static void loop() {
        System.out.println("start match");
        double player1x;
        double player1y;
        double player1r;
        double player2x;
        double player2y;
        double player2r;
        while(true) {
            try {
                player1x = in1.readDouble();
                player1y = in1.readDouble();
                player1r = in1.readDouble();
                player2x = in2.readDouble();
                player2y = in2.readDouble();
                player2r = in2.readDouble();
                out1.writeDouble(player2x);
                out1.writeDouble(player2y);
                out1.writeDouble(player2r);
                out2.writeDouble(player1x);
                out2.writeDouble(player1y);
                out2.writeDouble(player1r);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

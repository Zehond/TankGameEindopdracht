package Server;

import Data.Map;
import Data.Tank;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Match {
    private Map map;
    private Socket player1;
    private Socket player2;
    ObjectOutputStream outPlayer1;
    ObjectOutputStream outPlayer2;
    ObjectInputStream inPlayer1;
    ObjectInputStream inPlayer2;


    public Match(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
        try {
            outPlayer1 = new ObjectOutputStream(player1.getOutputStream());
            outPlayer2 = new ObjectOutputStream(player2.getOutputStream());
            inPlayer1 = new ObjectInputStream(player1.getInputStream());
            inPlayer2 = new ObjectInputStream(player2.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() throws IOException, ClassNotFoundException {
        System.out.println("update in match");
        Tank player1State = (Tank) inPlayer1.readObject();
        outPlayer2.writeObject(player1State);
        outPlayer2.flush();

        Tank player2State = (Tank) inPlayer2.readObject();
        outPlayer1.writeObject(player2State);
        outPlayer1.flush();
        System.out.println("end of update in Match");
    }


    public void sendData() {

    }
}

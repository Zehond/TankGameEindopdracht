package Server;

import Data.Bullet;
import Data.Map;

import java.awt.geom.Point2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Match implements Runnable {
    private Socket player1;
    private Socket player2;
    private DataInputStream in1;
    private DataOutputStream out1;
    private DataInputStream in2;
    private DataOutputStream out2;
    private ObjectInputStream OIn1;
    private ObjectOutputStream OOut1;
    private ObjectInputStream OIn2;
    private ObjectOutputStream OOut2;
    private int hpP1;
    private int hpP2;
    private Map map;

    public Match(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
        hpP1 = 5;
        hpP2 = 5;
        map = new Map(new Point2D.Double(1920/2.0, 1080/2.0));
        try {
            in1 = new DataInputStream(player1.getInputStream());
            out1 = new DataOutputStream(player1.getOutputStream());
            in2 = new DataInputStream(player2.getInputStream());
            out2 = new DataOutputStream(player2.getOutputStream());

            OOut1 = new ObjectOutputStream(player1.getOutputStream());
            OOut2 = new ObjectOutputStream(player2.getOutputStream());
            OIn1 = new ObjectInputStream(player1.getInputStream());
            OIn2 = new ObjectInputStream(player2.getInputStream());
            System.out.println("connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("started new match!!!");

    }

    @Override
    public void run() {
        try {
            out1.writeDouble(1.1);
            out2.writeDouble(1.1);

            sendSpawns();

            double rotationPlayer1;
            double rotationPlayer2;
            double XPlayer1;
            double YPlayer1;
            double XPlayer2;
            double YPlayer2;

            ArrayList<Bullet> bulletsP1;
            ArrayList<Bullet> bulletsP2;

            while (true) {
                // position and rotation
                rotationPlayer1 = in1.readDouble();
                XPlayer1 = in1.readDouble();
                YPlayer1 = in1.readDouble();
                rotationPlayer2 = in2.readDouble();
                XPlayer2 = in2.readDouble();
                YPlayer2 = in2.readDouble();

                out1.writeDouble(rotationPlayer2);
                out1.writeDouble(XPlayer2);
                out1.writeDouble(YPlayer2);

                out2.writeDouble(rotationPlayer1);
                out2.writeDouble(XPlayer1);
                out2.writeDouble(YPlayer1);

                //bullets
                bulletsP1 = (ArrayList<Bullet>) OIn1.readObject();

                bulletsP2 = (ArrayList<Bullet>) OIn2.readObject();

                bulletsP1.addAll(bulletsP2);

                ArrayList<Bullet> bullets = new ArrayList<>();
                bullets.addAll(bulletsP1);
                bullets.addAll(bulletsP2);

                boolean alreadyInList = false;

                for (Bullet bullet : bulletsP2) {
                    for (Bullet bullet1 : bullets) {
                        if (bullet.getPosition().equals(bullet1.getPosition())&& bullet.getDirection() == bullet1.getDirection()) {
                            alreadyInList = true;
                        }
                    }
                    if (!alreadyInList) {
                        bullets.add(bullet);
                    }
                    alreadyInList = false;
                }

                OOut1.writeObject(bullets);
                OOut2.writeObject(bullets);

                if (in1.readBoolean()) {
                    System.out.println("player 2 hit");
                    hpP2--;
                }

                if (in2.readBoolean()) {
                    System.out.println("player 1 hit");
                    hpP1--;
                }

                if (hpP1 == 0 && hpP2 == 0) {
                    out1.writeDouble(0);
                    out2.writeDouble(0);
                    resetMatch();
                } else if (hpP1 == 0) {
                    out1.writeDouble(-1);
                    out2.writeDouble(1);
                    resetMatch();
                } else if (hpP2 == 0) {
                    out1.writeDouble(1);
                    out2.writeDouble(-1);
                    resetMatch();
                } else {
                    out1.writeDouble(2);
                    out2.writeDouble(2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetMatch() {
        try {
            TimeUnit.SECONDS.sleep(10);

            sendSpawns();

            out1.writeDouble(1.1);
            out2.writeDouble(1.1);
            hpP1 = 5;
            hpP2 = 5;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSpawns() {
        try {
            Point2D spawn = map.getSpawnPoint();
            Point2D spawn1 = map.getSpawnPoint();
            while (spawn == spawn1) {
                spawn1 = map.getSpawnPoint();
            }
            OOut1.writeObject(spawn);
            OOut2.writeObject(spawn1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

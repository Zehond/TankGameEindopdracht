package Client;

import Data.Bullet;
import Data.InputHandler;
import Data.Map;
import Data.Tank;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Game extends Application {
    private Map map;
    private Tank player;
    private Tank enemy;
    private InputHandler inputHandler;
    private ResizableCanvas canvas;
    private ArrayList<Bullet> Bullets;
    private long lastBulletTime;
    private final long BULLET_DELAY = 500;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;




    public Game() throws IOException {
        socket = new Socket("localhost", 1234);
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.lastBulletTime = System.currentTimeMillis();
    }




    public void start(Stage stage) throws Exception {
        init();

        new Thread(() -> {
            try {
                while (true) {
//                    enemy = (Tank) objectInputStream.readObject();
                    Object object = objectInputStream.readObject();
                    if (object instanceof Tank) {
                        System.out.println("received tank as object");
                        enemy = (Tank) object;
                    }
                    objectOutputStream.writeObject(player);
                    objectOutputStream.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("problem in startReadingAndWritingToServerLoop");
            }
        }).start();

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        scene.setOnKeyPressed(e -> inputHandler.keyPressed(e));
        scene.setOnKeyReleased(e -> inputHandler.keyReleased(e));
        stage.setTitle("TONK");
        stage.show();
        draw(g2d);
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        this.map.draw(graphics);

        for (Bullet bullet : Bullets) {
            bullet.draw(graphics);
        }

        this.player.draw(graphics);
        this.enemy.draw(graphics);
    }

    public void update(double deltaTime) {
        player.update();
//        enemy.update();
//        try {
//            Object o = objectInputStream.readObject();
//            if (o instanceof Tank) {
//                enemy = (Tank) o;
//            }
//        }catch (IOException | ClassNotFoundException e){
//            e.printStackTrace();
//        }
        for (Bullet bullet : Bullets) {
            bullet.update();
        }
        inputHandling();
        collision();

    }

    public void init() {
        inputHandler = new InputHandler();
        Bullets = new ArrayList<>();
        this.map = new Map(new Point2D.Double(1920/2.0, 1080/2.0));
        this.player = new Tank(this.map.getSpawnPoint(), true);
        this.enemy = new Tank(this.map.getSpawnPoint(), false);
    }

    private void addBullet(Point2D position, double direction) {
        this.Bullets.add(new Bullet(position, direction));
    }

    private void inputHandling() {
        long currentTime = System.currentTimeMillis();
        if (inputHandler.isPressed(KeyCode.W)){
            player.forward(map.getWalls());
        }else if (inputHandler.isPressed(KeyCode.S)){
            player.backward(map.getWalls());
        }
        if (inputHandler.isPressed(KeyCode.D)){
            player.turnRight(map.getWalls());
        } else if (inputHandler.isPressed(KeyCode.A)){
            player.turnLeft(map.getWalls());
        }
        if (inputHandler.isPressed(KeyCode.SPACE ) && currentTime - lastBulletTime >= BULLET_DELAY){             //TODO zet er een delay op
            this.addBullet(player.getPosition(), player.getRotation());
            lastBulletTime = currentTime;
        }
    }

    public void collision() {
        bulletWallCollision();
    }

    public void bulletWallCollision() {
        ArrayList<Bullet> test = new ArrayList<>();
        boolean hasHitSomething = false;
        for (Bullet bullet : Bullets) {
            hasHitSomething = false;
            for (Shape shape : map.getWalls()) {
                if (shape.contains(bullet.getPosition())) {
                    hasHitSomething = true;
                }
            }
            if (enemy.HitsTank(bullet)) {
                hasHitSomething = true;
                enemy.gotHit();
            }

            if (!hasHitSomething) {
                test.add(bullet);
            }
        }
        this.Bullets = test;
    }

    public void BulletTankCollision() {

    }

    public static void main(String[] args){
        launch(Game.class);
    }
}

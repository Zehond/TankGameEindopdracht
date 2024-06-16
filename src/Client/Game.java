package Client;

import Data.Bullet;
import Data.InputHandler;
import Data.Map;
import Data.Tank;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

import java.awt.geom.Point2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Game extends Application {
    private Map map;
    private Tank player;
    private Tank enemy;
    private InputHandler inputHandler;
    private ResizableCanvas canvas;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> serverBullets;
    private long lastBulletTime;
    private final long BULLET_DELAY = 500;
    private int port = 8000;
    private String host ="localhost";
    private DataInputStream in;
    private DataOutputStream out;
    private ObjectInputStream OIn;
    private ObjectOutputStream OOut;
    private Socket socket;
    private boolean hit;
    private double status = 3;
    private Stage stage;
    private Scene scene;
    private int pointsPlayer = 0;
    private int pointsEnemy = 0;


    public Game() {
        this.lastBulletTime = System.currentTimeMillis();
    }
    public void start(Stage stage) {

        init();

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

        scene = new Scene(mainPane, 1920, 1080);


        BorderPane startPane = new BorderPane();
        startPane.setCenter(new Label("searching for player"));

        Scene startScene = new Scene(startPane, 1920, 1080);

        stage.setScene(startScene);
        this.stage = stage;
        scene.setOnKeyPressed(e -> inputHandler.keyPressed(e));
        scene.setOnKeyReleased(e -> inputHandler.keyReleased(e));
        stage.setTitle("TONK");
        stage.show();
        draw(g2d);

        Thread thread = new Thread(() -> {
            try {
                socket = new Socket(host, port);
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
                OIn = new ObjectInputStream(socket.getInputStream());
                OOut = new ObjectOutputStream(socket.getOutputStream());

                double input = in.readDouble();
                System.out.println(input);

                if (input == 1.1) {
                    status = 2;
                    player.setPosition((Point2D) OIn.readObject());
                    while (true) {

                        out.writeDouble(player.getRotation());
                        out.writeDouble(player.getPosition().getX());
                        out.writeDouble(player.getPosition().getY());
                        double rot = in.readDouble();
                        double x = in.readDouble();
                        double y = in.readDouble();
                        enemy.setRotation(rot);
                        enemy.setPosition(new Point2D.Double(x, y));

                        OOut.writeObject(bullets);
                        serverBullets = (ArrayList<Bullet>) OIn.readObject();

                        if (hit) {
                            out.writeBoolean(true);
                            hit = false;
                        } else {
                            out.writeBoolean(false);
                        }

                        status = in.readDouble();
                        if (status == -1) {
                            pointsEnemy++;
                        } else if (status == 1) {
                            pointsPlayer++;
                        }
                        if (status != 2) {
                            player.setPosition((Point2D) OIn.readObject());
                            player.setRotation(0);
                            if (in.readDouble() == 1.1) {
                                status = 2;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        this.map.draw(graphics);

        bullets.forEach(bullet -> bullet.draw(graphics));
        serverBullets.forEach(bullet -> bullet.draw(graphics));

        this.player.draw(graphics);
        this.enemy.draw(graphics);
    }

    public void update(double deltaTime) {
        sceneChanger();
        for (Bullet bullet : bullets) {
            bullet.update();
        }
        for (Bullet bullet : serverBullets) {
            bullet.update();
        }
        inputHandling();
        bulletWallCollision();
    }

    public void init() {
        inputHandler = new InputHandler();
        bullets = new ArrayList<>();
        serverBullets = new ArrayList<>();
        this.map = new Map(new Point2D.Double(1920/2.0, 1080/2.0));
        this.player = new Tank(this.map.getSpawnPoint(), true);
        this.enemy = new Tank(this.map.getSpawnPoint(), false);
    }

    private void addBullet(Point2D position, double direction) {
        this.bullets.add(new Bullet(position, direction));
    }

    private void sceneChanger() {
        if (status == 2) {
            stage.setScene(scene);
            return;
        }
        if (status == 3) {
            return;
        }
        BorderPane newPane = new BorderPane();
        if (status == -1) {
            newPane.setCenter(new Label("You lost...\n" + pointsPlayer + " - " + pointsEnemy));
        } else if (status == 1) {
            newPane.setCenter(new Label("You won!\n" + pointsPlayer + " - " + pointsEnemy));
        } else if (status == 0) {
            newPane.setCenter(new Label("Its a draw!\n" + pointsPlayer + " - " + pointsEnemy));
        }
        Scene newScene = new Scene(newPane, 500, 500);
        stage.setScene(newScene);
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
        if (inputHandler.isPressed(KeyCode.SPACE ) && currentTime - lastBulletTime >= BULLET_DELAY) { 
            this.addBullet(player.getPosition(), player.getRotation());
            lastBulletTime = currentTime;
        }
    }

    public void bulletWallCollision() {
        bullets = bullets.stream()
                .filter(bullet -> {
                    boolean hasHitSomething = map.getWalls().stream()
                            .anyMatch(shape -> shape.contains(bullet.getPosition()));
                    if (!hasHitSomething && enemy.HitsTank(bullet)) {
                        hasHitSomething = true;
                        hit = true;
                    }
                    return !hasHitSomething;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void main(String[] args){
        launch(Game.class);
    }
}

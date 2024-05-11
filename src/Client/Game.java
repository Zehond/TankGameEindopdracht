package Client;

import Data.InputHandler;
import Data.Map;
import Data.Tank;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

public class Game extends Application {
    private Map map;
    private Tank player;
    private Tank enemy;
    private InputHandler inputHandler;
    private ResizableCanvas canvas;


    public void start(Stage stage) throws Exception {
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

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Let there be light");
        stage.show();
        draw(g2d);
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        this.map.draw(graphics);

//        this.player.draw();
//        this.enemy.draw();
    }

    public void update(double deltaTime) {

    }

    public void init() {
        inputHandler = new InputHandler();
        this.map = new Map();
        this.player = new Tank(this.inputHandler);
        InputHandler bot = new InputHandler();
        this.enemy = new Tank(bot);
    }

    public static void main(String[] args)
    {
        launch(Game.class);
    }
}

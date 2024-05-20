package Data;

import Client.Game;
import Data.Bullet;
import Data.InputHandler;
import javafx.scene.input.KeyCode;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Tank {
    private Point2D position;
    private double size;
    private double rotation;
    private int tankHealth;
    private int speed;
    private InputHandler inputHandeler;
    double angle = Math.PI / 90;
    private BufferedImage image;



    public Tank(Point2D position){
        this.position = position;
        size = 10;
        rotation = 0;
        tankHealth = 100;
        speed = 5;
        try {
            image = ImageIO.read(new File(".idea/res/TONK1.0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){

    }

    public void forward() {
        setPosition(new Point2D.Double(position.getX() + Math.cos(rotation) * speed,
                position.getY() + Math.sin(rotation) * speed));
    }

    public void backward() {
        setPosition(new Point2D.Double(position.getX() - Math.cos(rotation) * speed,
                position.getY() - Math.sin(rotation) * speed));
    }

    public void turnLeft() {
        rotation = (rotation - angle) % (2 * Math.PI);
        if (rotation < 0){
            rotation += 2 * Math.PI;
        }
    }

    public void turnRight() {
        rotation = (rotation + angle) % (2 * Math.PI);
    }


    public void draw(FXGraphics2D graphics) {
        if (image == null) {
            return;
        }

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(position.getX(), position.getY());
        affineTransform.rotate(rotation + Math.PI / -2.0);
        affineTransform.scale(0.5, -0.5);
        affineTransform.translate(-image.getWidth() / 2.0, - image.getHeight() / 2.0);

        graphics.drawImage(image, affineTransform, null);
    }
    public void takeDamageBody() {
        tankHealth %= 5;
    }

    public void pushAwayFrom(Tank other) {
        double dx = position.getX() - other.getPosition().getX();
        double dy = position.getY() - other.getPosition().getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        double nx = dx / distance;
        double ny = dy / distance;

        setPosition(new Point2D.Double(position.getX() + nx * speed,
                position.getY() + ny * speed));
    }


    public void decreaseHealth(){
        //todo if hit by bullet decrease health
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public int getTankHealth() {
        return tankHealth;
    }

    public void setTankHealth(int tankHealth) {
        this.tankHealth = tankHealth;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}
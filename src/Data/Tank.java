package Data;

import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;


public class Tank {
    private Point2D position;
    private double size;
    private double rotation;
    private boolean isPlayer;
    private int speed;
    double angle = Math.PI / 90;
    private BufferedImage image;
    private BufferedImage tankSprite;
    private Shape hitbox;
    private Rectangle2D formHitbox;
    private Shape newHitBox;


    public Tank(Point2D position, boolean player) {
        this.position = position;
        size = 10;
        rotation = 0;
        speed = 1;
        isPlayer = player;
        formHitbox = new Rectangle2D.Double(0, 0, 54, 40);
        try {
            image = ImageIO.read(new File(".idea/res/TONK 2.0.png"));
            if (isPlayer) {
                tankSprite = image.getSubimage(17, 24, 61, 42);
            } else {
                tankSprite = image.getSubimage(116, 23, 61, 42);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forward(ArrayList<Rectangle2D> walls) {
        Point2D newPosition = new Point2D.Double(position.getX() + Math.cos(rotation) * speed,
                position.getY() + Math.sin(rotation) * speed);
        if (collisionDetector(newPosition, rotation, walls)) {
            setPosition(newPosition);
        }
    }

    public void backward(ArrayList<Rectangle2D> walls) {
        Point2D newPosition = new Point2D.Double(position.getX() - Math.cos(rotation) * speed,
                position.getY() - Math.sin(rotation) * speed);
        if (collisionDetector(newPosition, rotation, walls)) {
            setPosition(newPosition);
        }
    }

    public void turnLeft(ArrayList<Rectangle2D> walls) {
        double newRotation = (rotation - angle) % (2 * Math.PI);
        if (newRotation < 0) {
            newRotation += 2 * Math.PI;
        }
        if (collisionDetector(position, newRotation, walls)) {
            rotation = newRotation;
        }
    }

    public void turnRight(ArrayList<Rectangle2D> walls) {
        double newRotation = (rotation + angle) % (2 * Math.PI);
        if (collisionDetector(position, newRotation, walls)) {
            rotation = newRotation;
        }
    }

    public boolean collisionDetector(Point2D newPosition, double newrotation, ArrayList<Rectangle2D> walls) {
        newHitBox = affinetransformTest(newPosition, newrotation);
        boolean drive = true;
        for (Rectangle2D shape : walls) {
            if (newHitBox.intersects(shape)) {
                drive = false;
            }
        }
        return drive;
    }


    public void draw(FXGraphics2D graphics) {
        if (image == null || tankSprite == null) {
            return;
        }

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(position.getX(), position.getY());
        if (isPlayer) {
            affineTransform.rotate(rotation);
        } else {
            affineTransform.rotate(rotation + Math.PI);
        }
        affineTransform.scale(1, -1);
        affineTransform.translate(-tankSprite.getWidth() / 2.0, -tankSprite.getHeight() / 2.0);

        hitbox = affineTransform.createTransformedShape(formHitbox);

        graphics.drawImage(tankSprite, affineTransform, null);
    }

    public Shape affinetransformTest(Point2D newPoint, double newRotation) {
        AffineTransform test = new AffineTransform();
        test.translate(newPoint.getX(), newPoint.getY());
        if (isPlayer) {
            test.rotate(newRotation);
        } else {
            test.rotate(newRotation + Math.PI);
        }
        test.scale(1, -1);
        test.translate(-tankSprite.getWidth() / 2.0, -tankSprite.getHeight() / 2.0);

        return test.createTransformedShape(formHitbox);
    }

    public boolean HitsTank(Bullet bullet) {
        if (hitbox.contains(bullet.getPosition().getX(), bullet.getPosition().getY())) {
            return true;
        }
        return false;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
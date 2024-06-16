package Data;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Bullet implements Serializable {
    private Point2D position;
    private Ellipse2D sprite;
    private double speed;
    private double direction;
    private double size;

    public Bullet(Point2D position, double direction) {
        this.size = 4;
        this.position = new Point2D.Double(position.getX() - size/2, position.getY() - size/2);
        this.direction = direction;
        this.speed = 6.0;
        sprite = new Ellipse2D.Double(position.getX(), position.getY(), size, size);
    }

    public void update() {
        double nx = speed * Math.cos(direction);
        double ny = speed * Math.sin(direction);

        position.setLocation(position.getX() + nx, position.getY() + ny);
    }

    public void draw(FXGraphics2D graphics2D) {
        sprite = new Ellipse2D.Double(position.getX(), position.getY(), size, size);

        graphics2D.setColor(Color.black);
        graphics2D.draw(sprite);
        graphics2D.fill(sprite);
    }

    public Point2D getPosition() {
        return position;
    }

    public double getDirection() {
        return direction;
    }
}

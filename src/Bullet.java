import java.awt.geom.Point2D;

public class Bullet {
    private double size;
    private Point2D position;
    private double speed;
    private double direction;

    public Bullet(Point2D position, double direction) {
        this.position = position;
        this.direction = direction;
        this.speed = 20.0;
        this.size = 1.0;
    }

    public void update() {
        double radians = direction * (Math.PI / 180);

        double nx = speed * Math.cos(radians);
        double ny = speed * Math.sin(radians);

        position.setLocation(position.getX() + nx, position.getY() + ny);
    }
}


package Data;


import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
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
    private List<Bullet> bullets = new ArrayList<>();


    public Tank(InputHandler inputHandeler){
        position = new Point2D.Double(0, 0);
        size = 10;
        rotation = 0;
        tankHealth = 100;
        speed = 5;
        this.inputHandeler = inputHandeler;
    }

    public void update(){
        if (inputHandeler.isPressed(KeyEvent.VK_W)){
            setPosition(new Point2D.Double(position.getX() + Math.cos(rotation) * speed,
                    position.getY() + Math.sin(rotation) * speed));
        }else if (inputHandeler.isPressed(KeyEvent.VK_S)){
            setPosition(new Point2D.Double(position.getX() - Math.cos(rotation) * speed,
                    position.getY() - Math.sin(rotation) * speed));
        }
        if (inputHandeler.isPressed(KeyEvent.VK_D)){
            rotation = (rotation + angle) % (2 * Math.PI);
        } else if (inputHandeler.isPressed(KeyEvent.VK_A)){
            rotation = (rotation - angle) % (2 * Math.PI);
            if (rotation < 0){
                rotation += 2 * Math.PI;
            }
        }
        if (inputHandeler.isPressed(KeyEvent.VK_SPACE)){
            shoot();
        }
    }

//    public void move(KeyEvent event){
//        if (event.getKeyCode() == KeyEvent.VK_W){
//            setPosition(new Point2D.Double(position.getX() + Math.cos(rotation) * speed,
//                    position.getY() + Math.sin(rotation) * speed));
//        } else if (event.getKeyCode() == KeyEvent.VK_S) {
//            setPosition(new Point2D.Double(position.getX() - Math.cos(rotation) * speed,
//                    position.getY() - Math.sin(rotation) * speed));
//        }
//    }

    public void shoot(){
        Bullet bullet = new Bullet(new Point2D.Double(position.getX(), position.getY()), rotation);
        bullets.add(bullet);

    }
    public List<Bullet> getBullets(){
        return bullets;
    }
    public void takeDamage() {
        tankHealth -= 20;
    }

//    public void rotate(KeyEvent event){
//        double angle = Math.PI / 90;
//        if (event.getKeyCode() == KeyEvent.VK_D){
//            rotation = (rotation + angle) % (2 * Math.PI);
//        } else if (event.getKeyCode() == KeyEvent.VK_A) {
//            rotation = (rotation - angle) % (2 * Math.PI);
//            if (rotation < 0) {
//                rotation += 2 * Math.PI;
//            }
//        }
//    }

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
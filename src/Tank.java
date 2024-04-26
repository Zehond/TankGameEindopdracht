
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Tank implements KeyListener {

    private Point2D position;
    private double size;
    private double rotation;
    private int tankHealth;
    private int speed;

    public Tank(){


    }

    public void update(){

    }

    public void move(KeyEvent event){
        if (event.getKeyCode() == KeyEvent.VK_W){
            setPosition(new Point2D.Double(position.getX() + Math.cos(rotation) * speed,
                    position.getY() + Math.sin(rotation) * speed));
        } else if (event.getKeyCode() == KeyEvent.VK_S) {
            setPosition(new Point2D.Double(position.getX() - Math.cos(rotation) * speed,
                    position.getY() - Math.sin(rotation) * speed));
        }
    }

    public void shoot(KeyEvent event){
        if (event.getKeyCode() == KeyEvent.VK_SPACE){
            //todo shoot
        }
    }

    public void rotate(KeyEvent event){
        double angle = Math.PI / 90;
        if (event.getKeyCode() == KeyEvent.VK_A){
            rotation = (rotation + angle) % (2 * Math.PI);
        } else if (event.getKeyCode() == KeyEvent.VK_D) {
            rotation = (rotation - angle) % (2 * Math.PI);
            if (rotation < 0) {
                rotation += 2 * Math.PI;
            }
        }
    }

    public void decreaseHealth(){
        //todo if hit by bullet decrease health
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //of doe het zo wanneer er w wordt ingedrukt dat die dan een boolean omzet, en wanneer je dan los laat wordt de boolean naar null gezet
        //want ik weet niet of deze methode ook werkt met ingedrukt houden
        move(e);
        rotate(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

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

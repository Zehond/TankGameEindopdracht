package Data;

import Server.Match;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class Map {
    private double size;
    private Point2D middlePoint;
    private ArrayList<Shape> walls;
    private ArrayList<Point2D> spawnPoints;
    private double xOffset;
    private double yOffset;

    public Map(Point2D middlePoint) {
        this.middlePoint = middlePoint;
        xOffset = middlePoint.getX() - size;
        yOffset = middlePoint.getY() - size;
        size = 800;
        walls = new ArrayList<>();
        spawnPoints = new ArrayList<>();
        init();
    }

    public void init() {   //Gaat weg maar bestaat om te testen met een map
        walls.add(new Rectangle2D.Double(middlePoint.getX() - size/2 , middlePoint.getY() - size/2 , size, size));
        walls.add(new Line2D.Double(100 + xOffset, 0 + yOffset, 100 + xOffset, 200 + yOffset));
        walls.add(new Line2D.Double(200 + xOffset, 0 + yOffset, 200 + xOffset, 100 + yOffset));
        walls.add(new Line2D.Double(800 + xOffset, 200 + yOffset, 600 + xOffset, 200 + yOffset));
        walls.add(new Line2D.Double(650 + xOffset, 800 + yOffset, 650 + xOffset, 400 + yOffset));
        walls.add(new Line2D.Double(100 + xOffset, 0 + yOffset, 100 + xOffset, 200 + yOffset));
        walls.add(new Line2D.Double(100 + xOffset, 0 + yOffset, 100 + xOffset, 200 + yOffset));
        walls.add(new Line2D.Double(100 + xOffset, 0 + yOffset, 100 + xOffset, 200 + yOffset));
        walls.add(new Line2D.Double(100 + xOffset, 0 + yOffset, 100 + xOffset, 200 + yOffset));
        spawnPoints.add(new Point2D.Double(50 + xOffset, 50 + yOffset));
        spawnPoints.add(new Point2D.Double(750 + xOffset, 50 + yOffset));
        spawnPoints.add(new Point2D.Double(50 + xOffset, 750 + yOffset));
        spawnPoints.add(new Point2D.Double(750 + xOffset, 750 + yOffset));
    }

    private Random random = new Random();
    public Point2D getSpawnPoint() {
        return spawnPoints.get(random.nextInt(spawnPoints.size()));
    }

    public ArrayList<Shape> getWalls() {
        return walls;
    }


    public void draw(FXGraphics2D graphics2D) {

        for (Shape shape : walls) {
            graphics2D.draw(shape);
        }
    }
}



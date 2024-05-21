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
        size = 800;
        xOffset = middlePoint.getX() - size/2;
        yOffset = middlePoint.getY() - size/2;
        walls = new ArrayList<>();
        spawnPoints = new ArrayList<>();
        init();
    }

    public void init() {   //Gaat weg maar bestaat om te testen met een map
        walls.add(new Rectangle2D.Double(0 + xOffset - 6, 0 + yOffset - 6, 806, 6));
        walls.add(new Rectangle2D.Double(800 + xOffset, 0 + yOffset - 6, 6, 806));
        walls.add(new Rectangle2D.Double(0 + xOffset, 800 + yOffset, 806, 6));
        walls.add(new Rectangle2D.Double(0 + xOffset - 6, 0 + yOffset, 6, 806));

        walls.add(new Rectangle2D.Double(0 + xOffset - 6, 0 + yOffset, 6, 806));

//        walls.add(new Line2D.Double(200 + xOffset, 0 + yOffset, 200 + xOffset, 300 + yOffset));
        walls.add(new Rectangle2D.Double(200 + xOffset - 3, 0 + yOffset, 6, 300));
//        walls.add(new Line2D.Double(200 + xOffset, 200 + yOffset, 300 + xOffset, 200 + yOffset));
        walls.add(new Rectangle2D.Double(200 + xOffset, 200 + yOffset - 3, 100, 6));

//        walls.add(new Line2D.Double(300 + xOffset, 0 + yOffset, 300 + xOffset, 100 + yOffset));
        walls.add(new Rectangle2D.Double(300 + xOffset - 3, 0 + yOffset, 6, 102));
//        walls.add(new Line2D.Double(300 + xOffset, 100 + yOffset, 400 + xOffset, 100 + yOffset));
        walls.add(new Rectangle2D.Double(300 + xOffset, 100 + yOffset - 3, 100, 6));
//        walls.add(new Line2D.Double(400 + xOffset, 100 + yOffset, 400 + xOffset, 200 + yOffset));
        walls.add(new Rectangle2D.Double(400 + xOffset - 3, 96 + yOffset, 6, 106));

//        walls.add(new Line2D.Double(500 + xOffset, 200 + yOffset, 800 + xOffset, 200 + yOffset));
        walls.add(new Rectangle2D.Double(500 + xOffset, 200 + yOffset - 3, 300, 6));
//        walls.add(new Line2D.Double(500 + xOffset, 100 + yOffset, 600 + xOffset, 100 + yOffset));
        walls.add(new Rectangle2D.Double(500 + xOffset, 100 + yOffset - 3, 100, 6));
//        walls.add(new Line2D.Double(600 + xOffset, 100 + yOffset, 600 + xOffset, 300 + yOffset));
        walls.add(new Rectangle2D.Double(600 + xOffset - 3, 96 + yOffset, 6, 204));

//        walls.add(new Line2D.Double(600 + xOffset, 800 + yOffset, 600 + xOffset, 400 + yOffset));
        walls.add(new Rectangle2D.Double(600 + xOffset - 3, 400 + yOffset, 6, 400));
//        walls.add(new Line2D.Double(600 + xOffset, 500 + yOffset, 500 + xOffset, 500 + yOffset));
        walls.add(new Rectangle2D.Double(500 + xOffset, 500 + yOffset - 3, 100, 6));
//        walls.add(new Line2D.Double(600 + xOffset, 600 + yOffset, 700 + xOffset, 600 + yOffset));
        walls.add(new Rectangle2D.Double(600 + xOffset, 600 + yOffset - 3, 100, 6));

//        walls.add(new Line2D.Double(800 + xOffset, 500 + yOffset, 700 + xOffset, 500 + yOffset));
        walls.add(new Rectangle2D.Double(700 + xOffset, 500 + yOffset - 3, 100, 6));

//        walls.add(new Line2D.Double(400 + xOffset, 700 + yOffset, 400 + xOffset, 300 + yOffset));
        walls.add(new Rectangle2D.Double(400 + xOffset - 3, 300 + yOffset, 6, 400));
//        walls.add(new Line2D.Double(400 + xOffset, 600 + yOffset, 500 + xOffset, 600 + yOffset));
        walls.add(new Rectangle2D.Double(400 + xOffset, 600 + yOffset - 3, 100, 6));

//        walls.add(new Line2D.Double(300 + xOffset, 800 + yOffset, 300 + xOffset, 700 + yOffset));
        walls.add(new Rectangle2D.Double(300 + xOffset - 3, 700 + yOffset, 6, 100));

//        walls.add(new Line2D.Double(200 + xOffset, 800 + yOffset, 200 + xOffset, 500 + yOffset));
        walls.add(new Rectangle2D.Double(200 + xOffset - 3, 500 + yOffset, 6, 300));
//        walls.add(new Line2D.Double(200 + xOffset, 600 + yOffset, 300 + xOffset, 600 + yOffset));
        walls.add(new Rectangle2D.Double(200 + xOffset, 600 + yOffset - 3, 100, 6));

//        walls.add(new Line2D.Double(0 + xOffset, 600 + yOffset, 100 + xOffset, 600 + yOffset));
        walls.add(new Rectangle2D.Double(0 + xOffset, 600 + yOffset - 3, 100, 6));
//        walls.add(new Line2D.Double(100 + xOffset, 600 + yOffset, 100 + xOffset, 500 + yOffset));
        walls.add(new Rectangle2D.Double(100 + xOffset - 3, 500 + yOffset, 6, 102));

//        walls.add(new Line2D.Double(0 + xOffset, 400 + yOffset, 300 + xOffset, 400 + yOffset));
        walls.add(new Rectangle2D.Double(0 + xOffset, 400 + yOffset - 3, 302, 6));
//        walls.add(new Line2D.Double(100 + xOffset, 400 + yOffset, 100 + xOffset, 300 + yOffset));
        walls.add(new Rectangle2D.Double(100 + xOffset - 3, 300 + yOffset, 6, 100));
//        walls.add(new Line2D.Double(300 + xOffset, 400 + yOffset, 300 + xOffset, 500 + yOffset));
        walls.add(new Rectangle2D.Double(300 + xOffset - 3, 400 + yOffset, 6, 100));


        spawnPoints.add(new Point2D.Double(100 + xOffset, 100 + yOffset));
        spawnPoints.add(new Point2D.Double(700 + xOffset, 100 + yOffset));
        spawnPoints.add(new Point2D.Double(100 + xOffset, 700 + yOffset));
        spawnPoints.add(new Point2D.Double(700 + xOffset, 700 + yOffset));
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
            graphics2D.fill(shape);
            graphics2D.draw(shape);
        }
    }
}



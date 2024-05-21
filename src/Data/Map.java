package Data;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Map {
    private double size;
    private Point2D middlePoint;
    private ArrayList<Rectangle2D> walls;
    private ArrayList<Point2D> spawnPoints;

    public Map(Point2D middlePoint) {
        this.middlePoint = middlePoint;
        size = 800;
        walls = new ArrayList<>();
        spawnPoints = new ArrayList<>();
        init();
    }

    private void init() {   //Gaat weg maar bestaat om te testen met een map
        walls.add(new Rectangle2D.Double(middlePoint.getX() - size/2 , middlePoint.getY() - size/2 , size, size));
    }

    private Point2D getSpawnPoint() {
        return spawnPoints.get(1);
    }


    public void draw(FXGraphics2D graphics2D) {

        for (Rectangle2D rectangle : walls) {
            graphics2D.draw(rectangle);
        }
    }
}



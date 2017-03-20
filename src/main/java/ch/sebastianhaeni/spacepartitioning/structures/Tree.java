package ch.sebastianhaeni.spacepartitioning.structures;

import ch.sebastianhaeni.spacepartitioning.entities.Point;
import ch.sebastianhaeni.spacepartitioning.entities.Line;

import java.util.List;

public interface Tree {

    void addPoint(int x, int y);

    List<Point> getPoints();

    List<Line> getLines();

    void setSize(int width, int height);

}

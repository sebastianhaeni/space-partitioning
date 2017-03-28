package ch.sebastianhaeni.spacepartitioning;


import ch.sebastianhaeni.spacepartitioning.entities.Point;
import ch.sebastianhaeni.spacepartitioning.structures.kdtree.KdTree;
import ch.sebastianhaeni.spacepartitioning.structures.kdtree.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PointCloud extends JPanel {
  private static final Color BACKGROUND_COLOR = Color.WHITE;
  private static final Color POINT_COLOR = Color.BLACK;

  private KdTree tree = new KdTree();
  private List<Point> points = new ArrayList<>();

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    Graphics2D g2 = (Graphics2D) g;

    int width = getSize().width;
    int height = getSize().height;

    g2.setColor(BACKGROUND_COLOR);
    g2.fillRect(0, 0, width, height);

    g2.setColor(POINT_COLOR);
    g2.setStroke(new BasicStroke(1));

    points.forEach(point -> point.draw(g2));
    drawTree(g2, tree.getRoot());
  }

  private void drawTree(Graphics2D g2, Node node) {
    if (node == null) {
      return;
    }

    node.getLine().draw(g2);

    drawTree(g2, node.getBelow());
    drawTree(g2, node.getAbove());
  }

  void insert(int x, int y) {
    Point point = new Point(x, y);
    points.add(point);
    tree.insert(point);

    // Restructure tree
    //    tree = new KdTree();
    //    points.stream()
    //      .sorted(comparing(Point::getX).thenComparing(Point::getY))
    //      .forEach(p -> tree.insert(p));
  }
}

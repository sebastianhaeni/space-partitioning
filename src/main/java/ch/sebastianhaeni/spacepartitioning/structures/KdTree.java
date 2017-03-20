package ch.sebastianhaeni.spacepartitioning.structures;

import ch.sebastianhaeni.spacepartitioning.entities.Line;
import ch.sebastianhaeni.spacepartitioning.entities.Point;

import java.util.ArrayList;
import java.util.List;

public class KdTree implements Tree {
    private List<Point> points = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private int width;
    private int height;

    @Override
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
        recalculate();
    }

    @Override
    public List<Point> getPoints() {
        return points;
    }

    @Override
    public List<Line> getLines() {
        return lines;
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void recalculate() {

        Node root = new Node(points.get(0));
        Node current = root;

        for (int i = 1; i < points.size(); i++) {
            Point p = points.get(i);
            Node leaf = current;
            Node parent = current;
            boolean left = false;

            while (leaf != null) {
                parent = current;
                if (i % 2 == 0) {
                    if (p.getX() <= current.point.getX()) {
                        leaf = current.left;
                        left = true;
                    } else {
                        leaf = current.right;
                        left = false;
                    }
                } else {
                    if (p.getY() <= current.point.getY()) {
                        leaf = current.left;
                        left = true;
                    } else {
                        leaf = current.right;
                        left = false;
                    }
                }
                current = leaf;
            }

            if (left) {
                parent.left = new Node(p);
            } else {
                parent.right = new Node(p);
            }
            current = parent;
        }

        lines.clear();
        buildLines(root, false, 0, 0, width, height);
    }

    private void buildLines(Node node, boolean horizontal, int x, int y, int width, int height) {
        if (node == null) {
            return;
        }

        int x1 = horizontal ? x : node.point.getX();
        int x2 = horizontal ? width : node.point.getX();
        int y1 = horizontal ? node.point.getY() : y;
        int y2 = horizontal ? node.point.getY() : height;

        lines.add(new Line(x1, y1, x2, y2));
        buildLines(node.left, !horizontal, x1, y1, width, height);
        buildLines(node.right, !horizontal, x1, y1, width, height);
    }

    class Node {
        Point point;
        Node left;
        Node right;

        Node(Point point) {
            this.point = point;
        }
    }
}

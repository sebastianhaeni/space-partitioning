package ch.sebastianhaeni.spacepartitioning;


import ch.sebastianhaeni.spacepartitioning.structures.Tree;

import javax.swing.*;
import java.awt.*;

public class Space extends JPanel {
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color POINT_COLOR = Color.BLACK;
    private static final int POINT_SIZE = 3;

    private int width;
    private int height;
    private Tree tree;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        width = getSize().width;
        height = getSize().height;

        g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0, 0, width, height);

        g2.setColor(POINT_COLOR);
        g2.setStroke(new BasicStroke(1));

        tree.getPoints().forEach(point -> g2.drawOval(point.getX(), point.getY(), POINT_SIZE, POINT_SIZE));
        tree.getLines().forEach(line -> g2.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2()));
    }

    void setTree(Tree tree) {
        this.tree = tree;
    }

    public Tree getTree() {
        return tree;
    }
}

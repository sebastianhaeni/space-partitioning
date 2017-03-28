package ch.sebastianhaeni.spacepartitioning.structures.kdtree;

import ch.sebastianhaeni.spacepartitioning.entities.Line;
import ch.sebastianhaeni.spacepartitioning.entities.Point;

import static ch.sebastianhaeni.spacepartitioning.Main.WINDOW_WIDTH;

class NodeHorizontal extends Node {

  NodeHorizontal(Node parent, Point point) {
    super(parent, point.getX(), point);
  }

  @Override
  boolean isBelow(Point point) {
    return point.getY() < getCoordinate();
  }

  @Override
  Node construct(Point point) {
    return new NodeVertical(this, point);
  }

  @Override
  public Line getLine() {
    int x1 = getParent().getAbove() == this ? getParent().getPoint().getX() : getLowerBound(0);
    int x2 = getParent().getAbove() == this ? getUpperBound(WINDOW_WIDTH) : getParent().getPoint().getX();

    return new Line(x1, getPoint().getY(), x2, getPoint().getY());
  }

  @Override
  boolean isHorizontal() {
    return true;
  }
}

package ch.sebastianhaeni.spacepartitioning.structures.kdtree;


import ch.sebastianhaeni.spacepartitioning.entities.Line;
import ch.sebastianhaeni.spacepartitioning.entities.Point;

import static ch.sebastianhaeni.spacepartitioning.Main.WINDOW_HEIGHT;

class NodeVertical extends Node {
  NodeVertical(int coordinate, Point point) {
    super(null, coordinate, point);
  }

  NodeVertical(Node parent, Point point) {
    super(parent, point.getY(), point);
  }

  @Override
  boolean isBelow(Point point) {
    return point.getX() < getCoordinate();
  }

  @Override
  Node construct(Point point) {
    return new NodeHorizontal(this, point);
  }

  @Override
  public Line getLine() {
    int y1 = getParent() != null && getParent().getAbove() == this ?
      getParent().getPoint().getY() :
      getLowerBound( 0);

    int y2 = getParent() == null || getParent().getAbove() == this ?
      getUpperBound( WINDOW_HEIGHT) :
      getParent().getPoint().getY();

    return new Line(getPoint().getX(), y1, getPoint().getX(), y2);
  }

  @Override
  boolean isHorizontal() {
    return false;
  }
}

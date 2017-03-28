package ch.sebastianhaeni.spacepartitioning.structures.kdtree;

import ch.sebastianhaeni.spacepartitioning.entities.Line;
import ch.sebastianhaeni.spacepartitioning.entities.Point;
import com.google.common.base.MoreObjects;

import java.util.function.Function;

public abstract class Node {
  private final static Function<Node, Integer> GET_X = node -> node.getPoint().getX();
  private final static Function<Node, Integer> GET_Y = node -> node.getPoint().getY();

  private final int coordinate;
  private final Point point;
  private final Node parent;
  private Node below;
  private Node above;


  Node(Node parent, int coordinate, Point point) {
    this.parent = parent;
    this.coordinate = coordinate;
    this.point = point;
  }

  abstract boolean isBelow(Point point);

  abstract Node construct(Point point);

  public abstract Line getLine();

  abstract boolean isHorizontal();

  int getUpperBound(int initial) {
    Function<Node, Integer> primaryGetter = isHorizontal() ? GET_X : GET_Y;
    Function<Node, Integer> secondaryGetter = isHorizontal() ? GET_Y : GET_X;
    return getBound(primaryGetter, secondaryGetter, true, initial);
  }

  int getLowerBound(int initial) {
    Function<Node, Integer> primaryGetter = isHorizontal() ? GET_X : GET_Y;
    Function<Node, Integer> secondaryGetter = isHorizontal() ? GET_Y : GET_X;
    return getBound(primaryGetter, secondaryGetter, false, initial);
  }

  private int getBound(Function<Node, Integer> primaryGetter, Function<Node, Integer> secondaryGetter, boolean upper, int initial) {
    Node next = this;
    int bound = initial;

    while (next.getParent() != null) {
      int lowerBound = next.getParent().getLowerBound(0);
      int upperBound = next.getParent().getUpperBound(initial);

      Integer currentPosition = secondaryGetter.apply(this);
      if (currentPosition < lowerBound && currentPosition >= upperBound) {
        continue;
      }

      int coordinate = primaryGetter.apply(next.getParent());

      if (upper && coordinate > primaryGetter.apply(this) && coordinate < bound) {
        bound = coordinate;
      } else if (!upper && coordinate < primaryGetter.apply(this) && coordinate > bound) {
        bound = coordinate;
      }

      next = next.getParent();
    }

    return bound;
  }

  int getCoordinate() {
    return coordinate;
  }

  public Node getBelow() {
    return below;
  }

  public Node getAbove() {
    return above;
  }

  void setBelow(Point point) {
    this.below = construct(point);
  }

  void setAbove(Point point) {
    this.above = construct(point);
  }

  Point getPoint() {
    return point;
  }

  Node getParent() {
    return parent;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("coordinate", coordinate)
      .add("point", point)
      .add("below", below)
      .add("above", above)
      .toString();
  }
}

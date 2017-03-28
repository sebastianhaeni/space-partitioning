package ch.sebastianhaeni.spacepartitioning.entities;

import com.google.common.base.MoreObjects;

import java.awt.*;

public class Point {
  private static final int POINT_SIZE = 4;
  private static final int HALF_POINT_SIZE = POINT_SIZE / 2;

  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void draw(Graphics2D g2) {
    g2.drawOval(
      x - HALF_POINT_SIZE,
      y - HALF_POINT_SIZE,
      POINT_SIZE,
      POINT_SIZE
    );
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("x", x)
      .add("y", y)
      .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Point point = (Point) o;

    return x == point.x && y == point.y;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }

}

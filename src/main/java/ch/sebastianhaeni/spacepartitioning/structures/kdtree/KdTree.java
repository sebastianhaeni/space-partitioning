package ch.sebastianhaeni.spacepartitioning.structures.kdtree;

import ch.sebastianhaeni.spacepartitioning.entities.Point;

import java.util.function.BiConsumer;

public class KdTree {
  private Node root;

  public void insert(Point point) {
    if (root == null) {
      root = new NodeVertical(1, point);
      return;
    }

    Node node = root;

    while (node != null) {
      boolean isBelow = node.isBelow(point);
      Node next = isBelow ? node.getBelow() : node.getAbove();

      if (next == null) {
        BiConsumer<Node, Point> setter = isBelow ? Node::setBelow : Node::setAbove;
        setter.accept(node, point);
      }

      node = next;
    }
  }

  public Node getRoot() {
    return root;
  }
}

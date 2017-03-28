package ch.sebastianhaeni.spacepartitioning.structures.kdtree;

import ch.sebastianhaeni.spacepartitioning.entities.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class KdTreeTest {

  private KdTree kdTree;

  @BeforeEach
  void setUp() {
    kdTree = new KdTree();
  }

  @Test
  void givenRootPoint_returnRootWithoutLeafs() {
    kdTree.insert(new Point(0, 0));

    Node node = kdTree.getRoot();
    assertThat(node, is(instanceOf(NodeVertical.class)));
    assertThat(node.getBelow(), is(nullValue()));
    assertThat(node.getAbove(), is(nullValue()));
  }

  @Test
  void givenSecondPoint_isHorizontal() {
    kdTree.insert(new Point(0,0));
    kdTree.insert(new Point(0,10));

    Node node = kdTree.getRoot();
    assertThat(node.getAbove(), is(nullValue()));

    assertThat(node.getBelow(), is(not(nullValue())));
    assertThat(node.getBelow(), is(instanceOf(NodeHorizontal.class)));
  }
}

import Node from './Node.js';
import Split from './Split.js';

export default class KdTree {
  static generate(points, depth, splitCount = 0) {
    if (points.length === 0) {
      return undefined;
    }

    if (points.length === 1) {
      return new Node(points[0]);
    }

    const even = depth % 2 === 0;
    const extractor = even ? p => p.x : p => p.y;
    splitCount++;
    const {split, above, below} = KdTree.split(points, extractor, even, splitCount);
    const aboveNode = KdTree.generate(above, depth + 1, splitCount);
    const belowNode = KdTree.generate(below, depth + 1, splitCount);

    return new Node(split, aboveNode, belowNode);
  }

  static split(points, extractor, even, num) {
    const median = KdTree.getMedian(points, extractor);

    const above = points.filter(p => extractor(p) > median);
    const below = points.filter(p => extractor(p) <= median);
    const split = new Split(median, even, num);

    return {above, below, split};
  }

  static getMedian(points, extractor) {
    points.sort((a, b) => extractor(a) - extractor(b));
    const half = Math.floor(points.length / 2);

    if (points.length % 2 === 1) {
      return extractor(points[half]);
    }

    const lowMiddle = Math.floor((points.length - 1) / 2);
    return extractor(points[lowMiddle]);
  }
}

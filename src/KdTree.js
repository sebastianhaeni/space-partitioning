class KdTree {
  static generate(points, depth) {
    if (points.length === 0) {
      return undefined;
    }

    if (points.length === 1) {
      return new Node(points[0]);
    }

    const even = depth % 2 === 0;
    const extractor = even ? p => p.x : p => p.y;
    const {split, above, below} = KdTree.split(points, extractor, even);
    const aboveNode = KdTree.generate(above, depth + 1);
    const belowNode = KdTree.generate(below, depth + 1);

    return new Node(split, aboveNode, belowNode);
  }

  static split(points, extractor, even) {
    const median = KdTree.getMedian(points, extractor);

    const above = points.filter(p => extractor(p) > median);
    const below = points.filter(p => extractor(p) <= median);
    const split = new Split(median, even);

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

class Split {
  constructor(coordinate, even) {
    this.coordinate = coordinate;
    this.even = even;
  }
}

class Node {
  constructor(locator, above, below) {
    this.locator = locator;
    this.above = above;
    this.below = below;
  }
}

class Point {
  constructor(label, x, y) {
    this.label = label;
    this.x = x;
    this.y = y;
  }
}

class Line {
  constructor(x1, y1, x2, y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }
}

class Region {
  constructor(x1, y1, x2, y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  getLineFromSplit(split) {
    if (split.even) {
      const x1 = split.coordinate;
      const x2 = split.coordinate;
      const y1 = this.y1;
      const y2 = this.y2;

      return new Line(x1, y1, x2, y2);
    }

    const x1 = this.x1;
    const x2 = this.x2;
    const y1 = split.coordinate;
    const y2 = split.coordinate;

    return new Line(x1, y1, x2, y2);
  }

  split(split) {
    let x1, x2, y1, y2, above, below;

    if (split.even) {
      x1 = split.coordinate;
      x2 = this.x2;
      y1 = this.y1;
      y2 = this.y2;

      above = new Region(x1, y1, x2, y2);

      x1 = this.x1;
      x2 = split.coordinate;
      y1 = this.y1;
      y2 = this.y2;

      below = new Region(x1, y1, x2, y2);
    } else {
      x1 = this.x1;
      x2 = this.x2;
      y1 = this.y1;
      y2 = split.coordinate;

      above = new Region(x1, y1, x2, y2);

      x1 = this.x1;
      x2 = this.x2;
      y1 = split.coordinate;
      y2 = this.y2;

      below = new Region(x1, y1, x2, y2);
    }

    return {above, below};
  }
}

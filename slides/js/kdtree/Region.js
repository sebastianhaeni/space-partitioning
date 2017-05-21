import Line from './Line.js';

export default class Region {
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
      y1 = split.coordinate;
      y2 = this.y2;

      above = new Region(x1, y1, x2, y2);

      x1 = this.x1;
      x2 = this.x2;
      y1 = this.y1;
      y2 = split.coordinate;

      below = new Region(x1, y1, x2, y2);
    }

    return {above, below};
  }
}

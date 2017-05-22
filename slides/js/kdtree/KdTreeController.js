import KdTree from "./KdTree.js";
import Point from "./Point.js";
import Region from "./Region.js";
import Split from "./Split.js";

export default class KdTreeController {

  static get spaceCanvas() {
    return document.getElementById("kdtree-space");
  }

  static get treeCanvas() {
    return document.getElementById("kdtree-tree");
  }

  static get spaceCtx() {
    return KdTreeController.spaceCanvas.getContext("2d");
  }

  static get treeCtx() {
    return KdTreeController.treeCanvas.getContext("2d");
  }

  static get nodeOffsets() {
    return {
      0: 0,
      1: 110,
      2: 60,
      3: 30,
      4: 40,
      5: 20,
      6: 10
    };
  }

  constructor() {
    this.points = [];
    this.draw(KdTree.generate(this.points, 0));
  }

  addPoint(x, y) {
    if (this.points.some(p => p.x === x && p.y === y)) {
      // already exists
      return;
    }

    this.points.push(new Point('p' + (this.points.length + 1), x, y));

    // regenerate tree
    const root = KdTree.generate(this.points, 0);
    this.draw(root);
  }

  removePoint(x, y) {
    this.points = this.points.filter(p => p.x !== x || p.y !== y);

    // regenerate tree
    const root = KdTree.generate(this.points, 0);
    this.draw(root);
  }

  draw(root) {
    const {width: spaceWidth, height: spaceHeight} = KdTreeController.spaceCanvas;
    const {width: treeWidth, height: treeHeight} = KdTreeController.treeCanvas;

    KdTreeController.spaceCtx.clearRect(0, 0, spaceWidth, spaceHeight);
    KdTreeController.treeCtx.clearRect(0, 0, treeWidth, treeHeight);
    Split.splitCounter = 0;

    this.drawPoints();
    this.drawTree(root);
    this.drawSplitters(root, new Region(0, 0, spaceWidth, spaceHeight));
  }

  drawPoints() {
    KdTreeController.spaceCtx.strokeStyle = '#ddd';
      KdTreeController.spaceCtx.fillStyle = '#ddd';

    this.points.forEach(function (point) {
      KdTreeController.spaceCtx.beginPath();
      KdTreeController.spaceCtx.arc(point.x, point.y, 2, 0, 2 * Math.PI);
      KdTreeController.spaceCtx.stroke();
      KdTreeController.spaceCtx.fillText(point.label, point.x + 5, point.y - 3);
    });
  }

  drawTree(node, depth = 0, offset = 0, origin) {
    if (!node) {
      return;
    }

    const x = (KdTreeController.treeCanvas.width / 2) + offset;
    const y = (depth + 1) * 50;

    if (origin) {
      KdTreeController.treeCtx.moveTo(origin.x, origin.y);
      KdTreeController.treeCtx.lineTo(x, y);
      KdTreeController.treeCtx.stroke();
    }

    const oldPosition = {x, y};
    const nodeOffset = KdTreeController.nodeOffsets[depth + 1];

    this.drawTree(node.above, depth + 1, offset + nodeOffset, oldPosition);
    this.drawTree(node.below, depth + 1, offset - nodeOffset, oldPosition);

    KdTreeController.treeCtx.beginPath();
    KdTreeController.treeCtx.arc(x, y, 15, 0, 2 * Math.PI);
    KdTreeController.treeCtx.stroke();

    if (node.locator instanceof Point) {
      KdTreeController.treeCtx.fillStyle = 'green';
    } else {
      KdTreeController.treeCtx.fillStyle = '#fff';
    }

    KdTreeController.treeCtx.fill();
    KdTreeController.treeCtx.fillStyle = '#000';
    KdTreeController.treeCtx.textAlign = 'center';
    KdTreeController.treeCtx.textBaseline = 'middle';
    KdTreeController.treeCtx.fillText(node.locator.label, x, y);
  }

  drawSplitters(node, parentRegion) {
    if (!node || node.locator instanceof Point) {
      return;
    }

    const line = parentRegion.getLineFromSplit(node.locator);

    KdTreeController.spaceCtx.moveTo(line.x1, line.y1);
    KdTreeController.spaceCtx.lineTo(line.x2, line.y2);
    KdTreeController.spaceCtx.stroke();
    KdTreeController.spaceCtx.fillText(node.locator.label, line.x1 + 5, line.y1 + 11);

    const {above, below} = parentRegion.split(node.locator, parentRegion);

    this.drawSplitters(node.above, above);
    this.drawSplitters(node.below, below);
  }
}

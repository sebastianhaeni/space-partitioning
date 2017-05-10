const spaceCanvas = document.getElementById("space");
const treeCanvas = document.getElementById("tree");
const spaceCtx = spaceCanvas.getContext("2d");
const treeCtx = treeCanvas.getContext("2d");
const nodeOffsets = {
  0: 0,
  1: 235,
  2: 120,
  3: 60,
  4: 40,
  5: 20,
  6: 10
};
const points = [
  // new Point("p1", 136, 383),
  // new Point("p2", 388, 311),
  // new Point("p3", 245, 101),
  // new Point("p4", 538, 490),
  // new Point("p5", 298, 210),
];

draw(KdTree.generate(points, 0));

spaceCanvas.addEventListener('click', function (event) {
  const {offsetX, offsetY} = event;

  if (points.some(p => p.x === offsetX && p.y === offsetY)) {
    // already exists
    return;
  }

  points.push(new Point('p' + (points.length + 1), offsetX, offsetY));

  // regenerate tree
  const root = KdTree.generate(points, 0);
  draw(root);
});

function drawPoints() {
  points.forEach(function (point) {
    spaceCtx.beginPath();
    spaceCtx.arc(point.x, point.y, 2, 0, 2 * Math.PI);
    spaceCtx.stroke();
    spaceCtx.fillText(point.label, point.x + 5, point.y - 3);
  });
}

function draw(root) {
  spaceCtx.clearRect(0, 0, spaceCanvas.width, spaceCanvas.height);
  treeCtx.clearRect(0, 0, treeCanvas.width, treeCanvas.height);
  splitCounter = 0;

  drawPoints();
  drawTree(root);
  drawSplitters(root, new Region(0, 0, spaceCanvas.width, spaceCanvas.height));
}

function drawTree(node, depth = 0, offset = 0, origin) {
  if (!node) {
    return;
  }

  const x = (treeCanvas.width / 2) + offset;
  const y = (depth + 1) * 50;

  if (origin) {
    treeCtx.moveTo(origin.x, origin.y);
    treeCtx.lineTo(x, y);
    treeCtx.stroke();
  }

  const oldPosition = {x, y};

  drawTree(node.above, depth + 1, offset + nodeOffsets[depth + 1], oldPosition);
  drawTree(node.below, depth + 1, offset - nodeOffsets[depth + 1], oldPosition);

  treeCtx.beginPath();
  treeCtx.arc(x, y, 15, 0, 2 * Math.PI);
  treeCtx.stroke();

  if (node.locator instanceof Point) {
    treeCtx.fillStyle = '#ccc';
  } else {
    treeCtx.fillStyle = '#fff';
  }

  treeCtx.fill();
  treeCtx.fillStyle = '#000';
  treeCtx.fillText(node.locator.label, x - 6, y + 3);
}

function drawSplitters(node, parentRegion) {
  if (!node || node.locator instanceof Point) {
    return;
  }

  const line = parentRegion.getLineFromSplit(node.locator);

  spaceCtx.moveTo(line.x1, line.y1);
  spaceCtx.lineTo(line.x2, line.y2);
  spaceCtx.stroke();
  spaceCtx.fillText(node.locator.label, line.x1 + 5, line.y1 + 11);

  const {above, below} = parentRegion.split(node.locator, parentRegion);

  drawSplitters(node.above, above);
  drawSplitters(node.below, below);
}
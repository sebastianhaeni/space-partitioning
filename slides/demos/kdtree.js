import KdTreeController from "../js/kdtree/KdTreeController.js";

const ctrl = new KdTreeController();
const spaceCanvas = document.getElementById("kdtree-space");

spaceCanvas.addEventListener('click', function (event) {
  const {offsetX, offsetY} = event;
  ctrl.addPoint(offsetX, offsetY);
});

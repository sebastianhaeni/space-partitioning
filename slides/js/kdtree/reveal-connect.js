import KdTreeController from "./KdTreeController.js";

const ctrl = new KdTreeController();

Reveal.addEventListener('fragmentshown', (data) => {
  const fragment = data.fragment;
  if (fragment.attributes['data-point']) {
    const x = parseInt(fragment.attributes['data-x'].value, 10);
    const y = parseInt(fragment.attributes['data-y'].value, 10);
    ctrl.addPoint(x, y);
  }
}, false);

Reveal.addEventListener('fragmenthidden', (data) => {
  const fragment = data.fragment;
  if (fragment.attributes['data-point']) {
    const x = parseInt(fragment.attributes['data-x'].value, 10);
    const y = parseInt(fragment.attributes['data-y'].value, 10);
    ctrl.removePoint(x, y);
  }
}, false);

KdTreeController.spaceCanvas.addEventListener('click', function (event) {
  const {offsetX, offsetY} = event;
  ctrl.addPoint(offsetX, offsetY);
});

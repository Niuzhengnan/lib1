# -*- coding: utf-8 -*-
"""
Created on Fri Jan  5 21:37:53 2018

@author: nixian
"""

import numpy as np
import matplotlib.pyplot as plt
import matplotlib.transforms as pbox
import matplotlib.path as ppath
fig = plt.figure()
rect = plt.Rectangle((-1, -1), 2, 2, facecolor="#aaaaaa")
plt.gca().add_patch(rect)
bbox = pbox.Bbox.from_extents(-1, -1, 1, 1)
plt.xlim(-2, 2)
plt.ylim(-3,3)
linedata = []
lines =[]
for i in range(6):
    verts = (np.random.random((2, 2)) - 0.5) * 3.0
    linedata.append(verts)
    path = ppath.Path(verts)
    line, = plt.plot(verts[:, 0], verts[:, 1], color='b')
    lines.append(line)
## 用于保存到本地图片
from matplotlib.backends.backend_agg import FigureCanvasAgg as FigureCanvas
canvas = FigureCanvas(fig)
canvas.print_figure("test1.png", dpi=80)
data = zip(linedata, lines)
for dt in data:
    verts = dt[0]
    path = ppath.Path(verts)
    if path.intersects_bbox(bbox, False):
        dt[1].set_color('red')
        
canvas = FigureCanvas(fig)
canvas.print_figure("test2.png", dpi=80)
fig.clear()
lines = []
    
fig = plt.figure()
rect = plt.Rectangle((-1, -1), 2, 2, facecolor="#aaaaaa")
plt.gca().add_patch(rect)
bbox = pbox.Bbox.from_extents(-1, -1, 1, 1)
plt.xlim(-2, 2)
plt.ylim(-3,3)
for verts in linedata:
    line, = plt.plot(verts[:, 0], verts[:, 1], color='b')
    lines.append(line)
    
    
data = zip(linedata, lines)
for dt in data:
    verts = dt[0]
    path = ppath.Path(verts)
    if path.intersects_bbox(bbox):
        dt[1].set_color('red')
    
    
canvas = FigureCanvas(fig)
canvas.print_figure("test3.png", dpi=80)
plt.show()
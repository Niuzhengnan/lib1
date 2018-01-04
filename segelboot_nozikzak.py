# -*- coding: utf-8 -*-
"""
Created on Thu Jan  4 23:04:50 2018

@author: nixian
"""

import numpy as np
from matplotlib.path import Path
#from matplotlib.patches import PathPatch-
import matplotlib.patches as patches
import matplotlib.pyplot as plt
from scipy.integrate import quad, dblquad, nquad
import math as mt
import matplotlib.animation as animation
from scipy.interpolate import UnivariateSpline
import random

def vel(phi,phi_w,v_max,v_w): # possible degree between -180 and 180

    # angle starts from x-Axis and is in counterclock direction positiv
    # and with cllock direction negativ
    #velocity polar diagram (numbers can be ignored)
    v30 = 0.756
    v45 = 0.837
    v60 = 0.895
    v75 = 0.93
    v90 = 0.988
    v105 = 1
    v120 = 0.965
    v135 = 0.93
    v150 = 0.907
    v165 = 0.884

    x=np.array([0,15,30,45,60,75,90,105,120,135,150,165,180])
    y=np.array([0,v165, v150,v135,v120,v105,v90,v75,v60, v45,v30,0,0])
    w=np.array([100,100,50,50,50,50,50,50,50,50,50,100,100])
    
    spl = UnivariateSpline(x, y, w,bbox=[None, None], k=5)
    xs = np.linspace(0, 180, 10000)
    spl.set_smoothing_factor(0.5)
    
    # phi_b is the angle between boat angle and wind angle
    phi_b = phi - phi_w
    
    # narrowed angle range from -180 till 180
    if phi_b <= -180:
        phi_b = phi_b + 360
    if phi_b > 180:
        phi_b = phi_b - 360

    # velocity of boat is either v_max or v_wind
    if v_max < v_w:
        v_akt = v_max
    else:
        v_akt = v_w
	
	# interpolating the velocity depending of phi_b
    if 0 <= abs(phi_b) <= 15:
        vel = 0
    elif abs(phi_b) > 150:
        vel = 0
    else:
        vel = spl(abs(phi_b))+0
        

    return vel * v_akt

ori_x = 0
ori_y = 0
ziel_x = 100 
ziel_y = 100
obstacles = []
phi_w = 180
v_max = 10
v_w = 5

def fitFunc(x,y):
    theta1 = mt.atan2((y - ori_y),(x - ori_x)) /np.pi*180
    theta2 = mt.atan2((ziel_y - y),(ziel_x - x)) /np.pi*180
    v1 = vel(theta1,phi_w,v_max,v_w)
    v2 = vel(theta2,phi_w,v_max,v_w)
    if v1 == 0 or v2 == 0:
        return np.inf
    else:
        t1 = ((x - ori_x) ** 2 + (y - ori_y) ** 2) ** 0.5 / v1
        t2 = ((ziel_x - x) ** 2 + (ziel_y - y) ** 2) ** 0.5 / v2
        return t1+t2







class bird:
    """
    speed:速度
    position:位置
    fit:适应度
    lbestposition:经历的最佳位置
    lbestfit:经历的最佳的适应度值
    """
    def __init__(self, speed, position, fit, lBestPosition, lBestFit):
        self.speed = speed
        self.position = position
        self.fit = fit
        self.lBestFit = lBestPosition
        self.lBestPosition = lBestFit



class PSO:
    """
    fitFunc:适应度函数
    birdNum:种群规模
    w:惯性权重
    c1,c2:个体学习因子，社会学习因子
    solutionSpace:解空间，列表类型：[最小值，最大值]
    """
    def __init__(self, fitFunc, birdNum, w, c1, c2, solutionSpace):
        self.fitFunc = fitFunc
        self.w = 0.3
        self.c1 = 3
        self.c2 = 1
        self.birds, self.best = self.initbirds(birdNum, solutionSpace)

    def initbirds(self, size, solutionSpace):
        birds = []
        for i in range(size):
            position = random.uniform(solutionSpace[0], solutionSpace[1])
            speed = 0
            fit = self.fitFunc(position)
            birds.append(bird(speed, position, fit, position, fit))
        best = birds[0]
        for bird in birds:
            if bird.fit > best.fit:
                best = bird
        return birds,best

    def updateBirds(self):
        for bird in self.birds:
            # 更新速度
            bird.speed = self.w * bird.speed + self.c1 * random.random() * (bird.lBestPosition - bird.position) + self.c2 * random.random() * (self.best.position - bird.position)
            # 更新位置
            bird.position = bird.position + bird.speed
            # 更新适应度
            bird.fit = self.fitFunc(bird.position)
            # 查看是否需要更新经验最优
            if bird.fit > bird.lBestFit:
                bird.lBestFit = bird.fit
                bird.lBestPosition = bird.position

    def solve(self, maxIter):
        # 只考虑了最大迭代次数，如需考虑阈值，添加判断语句就好
        for i in range(maxIter):
            # 更新粒子
            self.updateBirds()
            for bird in self.birds:
                # 查看是否需要更新全局最优
                if bird.fit > self.best.fit:
                    self.best = bird














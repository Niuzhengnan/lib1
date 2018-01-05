# -*- coding: utf-8 -*-
"""
Created on Thu Jan  4 23:04:50 2018
@author: nixian
"""

import numpy as np
from matplotlib.path import Path
# from matplotlib.patches import PathPatch-
import matplotlib.patches as patches
import matplotlib.pyplot as plt
from scipy.integrate import quad, dblquad, nquad
import math as mt
import matplotlib.animation as animation
from scipy.interpolate import UnivariateSpline
import random


def vel(phi, phi_w, v_max, v_w):  # possible degree between -180 and 180

    # angle starts from x-Axis and is in counterclock direction positiv
    # and with cllock direction negativ
    # velocity polar diagram (numbers can be ignored)
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

    x = np.array([0, 15, 30, 45, 60, 75, 90, 105, 120, 135, 150, 165, 180])
    y = np.array([0, v165, v150, v135, v120, v105, v90, v75, v60, v45, v30, 0, 0])
    w = np.array([100, 100, 50, 50, 50, 50, 50, 50, 50, 50, 50, 100, 100])

    spl = UnivariateSpline(x, y, w, bbox=[None, None], k=5)
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
        vel = spl(abs(phi_b)) + 0

    return vel * v_akt


ori_x = 0
ori_y = 0
ziel_x = 100
ziel_y = 100
obstacles = []
phi_w = -135
v_max = 5
v_w = 3

def add_obstacle(obs_x,obs_y, width, v_o, phi_o): # phi can be from 0 to 360
    obstacles.append([obs_x,obs_y,width, v_o, phi_o/180*np.pi])
    print('Obstacle ' + '[' + str(obs_x) + ',' + str(obs_y) + '] added. width = ' + str(width))

def remove_obstacle():
    popped_obstacle = obstacles.pop()
    print('Obstacle ' + str(popped_obstacle) + 'removed.')

def fitFunc(x, y):
    theta1 = mt.atan2((y - ori_y), (x - ori_x)) / np.pi * 180
    theta2 = mt.atan2((ziel_y - y), (ziel_x - x)) / np.pi * 180
    v1 = vel(theta1, phi_w, v_max, v_w)
    v2 = vel(theta2, phi_w, v_max, v_w)
    if v1 == 0 or v2 == 0:
        return np.inf,np.inf
    else:
        t1 = ((x - ori_x) ** 2 + (y - ori_y) ** 2) ** 0.5 / v1
        t2 = ((ziel_x - x) ** 2 + (ziel_y - y) ** 2) ** 0.5 / v2
        return t1, t2

class PSO:
    """
    fitFunc:适应度函数
    birdNum:种群规模
    w:惯性权重
    c1,c2:个体学习因子，社会学习因子
    solutionSpace:解空间，列表类型：[最小值，最大值]
    """

    class Bird:
        """
        speed:速度
        position:位置
        fit:适应度
        lbestposition:经历的最佳位置
        lbestfit:经历的最佳的适应度值
        """

        def __init__(self, speed_x, speed_y, position_x, position_y, fit1, fit2, lBestPosition_x, lBestPosition_y, lBestFit1, lBestFit2):
            self.speed_x = speed_x
            self.speed_y = speed_y
            self.position_x = position_x
            self.position_y = position_y
            self.fit1 = fit1
            self.fit2 = fit2
            self.lBestPosition_x = lBestPosition_x
            self.lBestPosition_y = lBestPosition_y
            self.lBestFit1 = lBestFit1
            self.lBestFit2 = lBestFit2

    def __init__(self, fitFunc, check_obstacles, birdNum, w, c1, c2, maxIter):
        self.fitFunc = fitFunc
        self.check_obstacles = check_obstacles
        self.w = w
        self.c1 = c1
        self.c2 = c2
        self.birdNum = birdNum
        self.maxIter = maxIter
        self.solutionSpace_x = [ori_x, ziel_x]
        self.solutionSpace_y = [ori_y, ziel_y]
        self.birds, self.best = self.initbirds()

    def initbirds(self):
        birds = []
        for i in range(self.birdNum):
            #position_x = random.uniform(self.solutionSpace_x[0], self.solutionSpace_x[1])
            position_x = random.uniform(*self.solutionSpace_x)
            #position_y = random.uniform(self.solutionSpace_y[0], self.solutionSpace_y[1])
            position_y = random.uniform(*self.solutionSpace_y)
            speed_x = 0
            speed_y = 0
            fit1, fit2 = self.fitFunc(position_x, position_y)
            birds.append(PSO.Bird(speed_x, speed_y, position_x, position_y, fit1, fit2, position_x, position_y, fit1,fit2))
        best = birds[0]
        # for bird in birds:
        #    if bird.fit > best.fit:
        #        best = bird
        best = min(birds, key=lambda x: x.fit1 + x.fit2)
        return birds, best

    def updateBirds(self):
        for bird in self.birds:
            # bird.speed = self.w * bird.speed + self.c1 * random.random() * (bird.lBestPosition - bird.position) + self.c2 * random.random() * (self.best.position - bird.position)
            # bird.speed = self.w * bird.speed + \
            # self.c1 * random.random() * (((bird.lBestPosition_x - bird.position_x) ** 2 + (bird.lBestPosition_y - bird.position_y) ** 2) ** 0.5) + \
            # self.c2 * random.random() * (((self.best.position_x - bird.position_x) ** 2 + (self.best.position_y - bird.position_y) ** 2) ** 0.5)
            # 更新速度
            bird.speed_x = self.w * bird.speed_x + self.c1 * random.random() * (
                    bird.lBestPosition_x - bird.position_x) + self.c2 * random.random() * (
                                   self.best.position_x - bird.position_x)
            bird.speed_y = self.w * bird.speed_y + self.c1 * random.random() * (
                    bird.lBestPosition_y - bird.position_y) + self.c2 * random.random() * (
                                   self.best.position_y - bird.position_y)
            
            
            w_local, w_global = random.random(), random.random()
            bird.speed_x = self.w * bird.speed_x + self.c1 * w_local * (
                    bird.lBestPosition_x - bird.position_x) + self.c2 * w_global * (
                            self.best.position_x - bird.position_x)
            bird.speed_y = self.w * bird.speed_y + self.c1 * w_local * (
                    bird.lBestPosition_y - bird.position_y) + self.c2 * w_global * (
                            self.best.position_y - bird.position_y)
            # 更新位置
            bird.position_x = bird.position_x + bird.speed_x
            if bird.position_x < ori_x:
                bird.position_x = ori_x
            elif bird.position_x > ziel_x:
                bird.position_x = ziel_x

            bird.position_y = bird.position_y + bird.speed_y
            if bird.position_y < ori_y:
                bird.position_y = ori_y
            elif bird.position_y > ziel_y:
                bird.position_y = ziel_y
            # 更新适应度
            bird.fit1, bird.fit2 = self.fitFunc(bird.position_x, bird.position_y)
            # 查看是否需要更新经验最优
            if bird.fit1 + bird.fit2 < bird.lBestFit1 + bird.lBestFit2 and self.check_obstacles(obstacles,bird.position_x, bird.position_y, bird.fit1, bird.fit2):
                bird.lBestFit = bird.fit1 + bird.fit2
                bird.lBestPosition_x = bird.position_x
                bird.lBestPosition_y = bird.position_y

    def solve(self):
        # 只考虑了最大迭代次数，如需考虑阈值，添加判断语句就好
        for i in range(self.maxIter):
            # 更新粒子
            self.updateBirds()
            for bird in self.birds:
                # 查看是否需要更新全局最优
                if bird.fit1 + bird.fit2 < self.best.fit1 + self.best.fit2:
                    self.best = bird
            #print("=======" + str(i) + "=======" + "x:" + str(self.best.lBestPosition_x) + "y:" + str(self.best.lBestPosition_y) + "t:" + str(self.best.fit))


def check_obstacles(obstacles,pos_x,pos_y, t1, t2):
    return True # no obstacle detected


            
if __name__ == "__main__":
    a = PSO(fitFunc,check_obstacles,100,0.3,3,1,100)
    a.initbirds()
    a.solve()
    print(a.best.lBestPosition_x,a.best.lBestPosition_y,a.best.lBestFit)
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
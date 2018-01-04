# -*- coding: utf-8 -*-
"""
Created on Thu Dec  7 16:26:42 2017

@author: Niu
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

obstacles = []
ori_x = 0
ori_y = 0
ziel_x = 100 
ziel_y = 100
check_radius = 10
T = 0.1
 # all the time that it takes
xx = [ori_x]
yy = [ori_y]


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

def add_obstacle(obs_x,obs_y, width, v_o, phi_o): # phi can be from 0 to 360
    obstacles.append([obs_x,obs_y,width, v_o, phi_o/180*np.pi])
    print('Obstacle ' + '[' + str(obs_x) + ',' + str(obs_y) + '] added. width = ' + str(width))

def remove_obstacle():
    popped_obstacle = obstacles.pop()
    print('Obstacle ' + str(popped_obstacle) + 'removed.')

def absw(pos_x, pos_y):
    absw =  (mt.atan2((ziel_y - pos_y),(ziel_x - pos_x))) / np.pi * 180
    return absw

def distance(x1,y1,x2,y2):
    return ((x1-x2) ** 2 + (y1 - y2) ** 2) ** 0.5

def b_calc(a, c, alpha):
    return (a ** 2 + c ** 2 - 2 * a * np.cos(alpha)) ** 0.5
#Calculation of b from a , c and alpha
def beta_calc(a, alpha, b):
    return np.arcsin(a * np.sin(alpha) / b)
#Calculation of β from a , alpha and b
def J1_calc(a, b, v1, v2):
    if v1 == 0 or v2 == 0:
        return 10000000000000
    return (a / v1 + b / v2) * 15    

def J2_calc(pos_x, pos_y, alpha, a, v1, theta, lenkwinkel,t): #Here alpha is some certain element in list of alpha
    checkedout_obstacles = check(pos_x, pos_y, obstacles, t)
    if len(checkedout_obstacles) == 0:
        return 0
    else:
        for obstacle in checkedout_obstacles:        
    #k is the angle between x-axis and the line from current position pointing to some certain obstacle 
    #(in rad, can be minus)
            k = mt.atan2(((obstacle[1] + t * obstacle[3] * np.sin(obstacle[4])) - pos_y),\
                         ((obstacle[0] + t * obstacle[3] * np.cos(obstacle[4])) - pos_x))
            w = absw(pos_x, pos_y) / 180 * np.pi
            d = distance(pos_x, pos_y , (obstacle[0] + t * obstacle[3] * np.cos(obstacle[4])), (obstacle[1] + t * obstacle[3] * np.sin(obstacle[4])))
            if 0 < abs(d * np.sin(k - alpha - w)) < obstacle[2]:
                return 200000000000*(1/abs(d * np.sin(k - alpha - w)) - 1/obstacle[2])
            elif abs(d * np.sin(k - alpha - w)) == 0:
                return 100000000000
        for obstacle in checkedout_obstacles:
            edge1 = abs(a - v1 * T) 
            edge2 = distance((obstacle[0] + t * obstacle[3] * np.cos(obstacle[4])), (obstacle[1] + t * obstacle[3] * np.sin(obstacle[4])) , v1 * T * np.cos(theta/180*np.pi) , v1 * T * np.sin(theta/180*np.pi))
            edge3 = distance((obstacle[0] + t * obstacle[3] * np.cos(obstacle[4])), (obstacle[1] + t * obstacle[3] * np.sin(obstacle[4])) , a * np.cos(theta/180*np.pi), a * np.sin(theta/180*np.pi))
            angle = np.arccos((edge1 ** 2 + edge2 ** 2 - edge3 ** 2) / (2 * edge1 * edge2)) - lenkwinkel/180*np.pi
            if abs(edge2 * np.sin(angle)) < obstacle[2]:
                return 2000000000000*(1/abs(edge2 * np.sin(angle)) - 1/obstacle[2])
        return 0
            
        
def J3_calc(lenkwinkel):
    phi_krit = 75
    if lenkwinkel > phi_krit:
        return (lenkwinkel - phi_krit) * 10
    else:
        return 0

def check(pos_x, pos_y, obstacles, t, checkedout_obstacles = None):
    if checkedout_obstacles is None:
        checkedout_obstacles = list()
    for obstacle in obstacles:
        d = ((pos_x - (obstacle[0] + t * obstacle[3] * np.cos(obstacle[4])))**2\
             +(pos_y - (obstacle[1] + t * obstacle[3] * np.sin(obstacle[4])))**2) ** 0.5        
        if d - obstacle[2] < check_radius:
            checkedout_obstacles.append(obstacle)   
    return checkedout_obstacles

def maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w, t, division = 180):   #every step goes about 1
    #T Abtastzeit 0,1
    theta = np.linspace(absw(pos_x,pos_y) - 90, absw(pos_x,pos_y) + 90, division)
    v1 = list(map(vel, theta, [phi_w] * division, [v_max] * division, [v_w] * division))
    #a = [5 * T * vi  for vi in v1]
    a = [1/2*distance(pos_x, pos_y, ziel_x, ziel_y)]*division
    c = [((ziel_x - pos_x) ** 2 + (ziel_y - pos_y) ** 2) ** 0.5] * division
    alpha = [i / 180 * np.pi for i in np.linspace(-90,90,division)]
    b = list(map(b_calc, a, c, alpha))
    beta = list(map(beta_calc, a, alpha, b))
    v2 = list(map(vel, [absw(pos_x, pos_y)/180*np.pi - beta_i for beta_i in beta], [phi_w] * division, [v_max] * division, [v_w] * division))
    J1 = list(map(J1_calc, a, b, v1, v2))
    lenkwinkel = [(abs(a) + abs(b)) / np.pi * 180 for a,b in zip(alpha,beta)] 
    #Obstacle avoidance
    J2 = list(map(J2_calc,[pos_x] * division, [pos_y] * division, alpha, a, v1, theta, lenkwinkel,[t] * division))
    
    #Turning penalty
    J3 = list(map(J3_calc,lenkwinkel))
    
    J = [a + b + c for a, b, c in zip(J1,J2,J3)]
    maxv = v1[J.index(min(J))]
    max_v_winkel = theta[J.index(min(J))]
    maxv1 = v2[J.index(min(J))]
    if J.index(min(J)) < division / 2:
        max_v_winkel1 = theta[J.index(min(J))] + lenkwinkel[J.index(min(J))]
    else:
        max_v_winkel1 = theta[J.index(min(J))] - lenkwinkel[J.index(min(J))]#Error here
    ratio = b[J.index(min(J))] / a[J.index(min(J))]
    return maxv, max_v_winkel, maxv1, max_v_winkel1, ratio
    
def move(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t):
    new_x = pos_x + maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w, t)[0] * np.cos(maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)[1]/180*np.pi) * T
    new_y = pos_y + maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)[0] * np.sin(maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)[1]/180*np.pi) * T
    xx.append(new_x)
    yy.append(new_y)
    new_x += maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)[2] * T * np.cos(maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)[3]/180*np.pi)
    new_y += maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)[2] * T * np.sin(maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)[3]/180*np.pi)
    xx.append(new_x)
    yy.append(new_y)    
    return new_x, new_y    

def yacht_simulation(pos_x, pos_y, phi_w, v_max, v_w, t = 0):
    while ((pos_x < ziel_x - 5) or (pos_y < ziel_y - 5)) and (maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)[0] != 0):
        pos_x, pos_y = move(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)
        t += 2 * T
        print('Das Segelboat fährt nach winkel ', maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w,t)[1])
        print(' x ist jetzt ',pos_x, '\n', 'y ist jetzt ', pos_y)
        print('t = ', str(t))
    if (pos_x > ziel_x + 5) or (pos_y > ziel_y + 5):
        print('Das Segelboat fährt über der Grezen')
        print(pos_x,pos_y)
    elif (pos_x <= ziel_x + 5 and pos_x >= ziel_x - 5) and (pos_y <= ziel_y + 5 and pos_y >= ziel_y -5):
        print('Das Segelboat erreicht den Ziel.\n')
        print(pos_x,pos_y)
        print('Die Zeit ist ', t, '.')
    else:      
        print('Das Segelboat hat eine kleine Abweichung von Zielvektor.')
        print(pos_x,pos_y)
        print(t)
    plt.axis([ori_x-10, ziel_x+10, ori_y-10, ziel_y+10])
    plt.grid()
    plt.plot([ori_x,ziel_x], [ori_y, ziel_y], 'rx')
    plt.legend(bbox_to_anchor=(1, 1),
          bbox_transform=plt.gcf().transFigure)
    
    plt.annotate(r'Start',
             xy=(ori_x, ori_y), xycoords='data',
             xytext=(-40, -30), textcoords='offset points', fontsize=10,
             arrowprops=dict(arrowstyle="->", connectionstyle="arc3, rad=0"))
    
    plt.annotate(r'Goal',
             xy=(ziel_x, ziel_y), xycoords='data',
             xytext=(+10, +20), textcoords='offset points', fontsize=10,
             arrowprops=dict(arrowstyle="->", connectionstyle="arc3, rad=0")) 
    x1 = [ziel_x - 5, ziel_x + 5]
    x2 = [ziel_x - 5, ziel_x - 5]
    x3 = [ziel_x + 5, ziel_x + 5]
    y1 = [ziel_y - 5, ziel_y - 5]
    y2 = [ziel_y + 5, ziel_y + 5]
    y3 = [ziel_y - 5, ziel_y + 5]
    plt.plot(x2,y3)
    plt.plot(x1,y2)
    plt.plot(x3,y3)
    plt.plot(x1,y1)
#   line, = ax.plot(xt,yt) 
    #ax.set_autoscale_on(False)
    #ani = animation.FuncAnimation(fig, animate, np.arange(1, 200))
    plt.plot(xx,yy)
    plt.show()
    return xx, yy

def simData():
    xt = 0
    yt = 0
    assert len(xx) == len(yy) and len(xx) > 0
    for i in np.arange(0,len(xx)):
        xt = xx[i]
        yt = yy[i]
        yield xt, yt
def simPoints(simData):
    xt,yt = simData[0], simData[1]
    line.set_data(xt,yt)
    return line


fig = plt.figure()
ax = fig.add_subplot(111)
line, = ax.plot([], [], 'bo', ms=5)
ax.set_ylim(0, 1)
ax.set_xlim(0, 1)
ani = animation.FuncAnimation(fig, simPoints, simData, blit=False, interval=50)
plt.show()

if __name__ == "__main__":
    #add_obstacle(80-54,60,10,1,0)
    #add_obstacle(80,50,10,0,0)
    yacht_simulation(0,0,-135,4,2)
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
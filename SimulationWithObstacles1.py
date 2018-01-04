# -*- coding: utf-8 -*-
"""
Created on Sun Nov 26 17:36:33 2017

@author: Niu
"""

import numpy as np
from matplotlib.path import Path
#from matplotlib.patches import PathPatch
import matplotlib.patches as patches
import matplotlib.pyplot as plt
from scipy.integrate import quad, dblquad, nquad
import math as mt
import matplotlib.animation as animation

xx = [0]
yy = [0]

ori_x = 0
ori_y = 0

ori = [ori_x - 1, ori_y - 1]# [position x, position y, potentiall]

ziel_x = 100
ziel_y = 100

ziel = [ziel_x + 1 , ziel_y + 1]# [position x, position y, potentiall]

obstacles = []

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
    v180 = 0.872

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
        vel = (v180 + ((v165-v180) / (15-0)) * (abs(phi_b)-0))*v_akt
    if 15 <= abs(phi_b) <= 30:
        vel = (v165 + ((v150-v165) / (30-15)) * (abs(phi_b)-15))*v_akt
    if 30 <= abs(phi_b) <= 45:
        vel = (v150 + ((v135-v150) / (45-30)) * (abs(phi_b)-30))*v_akt
    if 45 <= abs(phi_b) <= 60:
        vel = (v135 + ((v120-v135) / (60-45)) * (abs(phi_b)-45))*v_akt
    if 60 <= abs(phi_b) <= 75:
        vel = (v120 + ((v105-v120) / (75-60)) * (abs(phi_b)-60))*v_akt
    if 75 <= abs(phi_b) <= 90:
        vel = (v105 + ((v90-v105) / (90-75)) * (abs(phi_b)-75))*v_akt
    if 90 <= abs(phi_b) <= 105:
        vel = (v90 + ((v75-v90) / (105-90)) * (abs(phi_b)-90))*v_akt
    if 105 <= abs(phi_b) <= 120:
        vel = (v75 + ((v60-v75) / (120-105)) * (abs(phi_b)-105))*v_akt
    if 120 <= abs(phi_b) <= 135:
        vel = (v60 + ((v45-v60) / (135-120)) * (abs(phi_b)-120))*v_akt
    if 135 <= abs(phi_b) <= 150:
        vel = (v45 + ((v30-v45) / (150-135)) * (abs(phi_b)-135))*v_akt
    if abs(phi_b) > 150:
        vel = 0
    return vel


def add_obstacle(obs_x,obs_y, width, v_o = 0, phi_o = 0):
    obstacles.append([obs_x,obs_y,width])
    print('Obstacle ' + '[' + str(obs_x) + ',' + str(obs_y) + '] added. width = ' + str(width))
    
    circ=plt.Circle((obs_x,obs_y),radius=width,color='r',fill=True)
    ax.add_patch(circ)
    '''
    ax.add_patch(
    patches.Rectangle((obs_x - width/2, obs_y - width/2), width, width,facecolor="red"))
    plt.plot([obs_x,obs_x], [obs_y, obs_y], 'bx')
    '''
    plt.annotate(r'Obstacle',
             xy=(obs_x, obs_y), xycoords='data',
             xytext=(-40, -30), textcoords='offset points', fontsize=10,
             arrowprops=dict(arrowstyle="->", connectionstyle="arc3, rad=0"))
    plt.show()

def remove_obstacle():
    popped_obstacle = obstacles.pop()
    print('Obstacle ' + str(popped_obstacle) + 'removed.')
    
def E_Feld(x,y):
    k0 = 10.833953266776
    #variable x,y
    Ex = k0 * ((x - ori[0]) / ((x - ori[0]) ** 2 + (y - ori[1]) ** 2) + (ziel[0] - x) / ((ziel[0] - x) ** 2 + (ziel[1] - y) ** 2)  )
    Ey = k0 * ((y - ori[1]) / ((x - ori[0]) ** 2 + (y - ori[1]) ** 2) + (ziel[1] - y) / ((ziel[0] - x) ** 2 + (ziel[1] - y) ** 2))
    return [Ex, Ey]

#def map_p(pos_x, pos_y, ori = [ori_x - 1, ori_y - 1], ziel = [ziel_x + 1, ziel_y + 1]):
    # position, original position, goalposition
#    p1 = -quad(lambda x: E_Feld(x,ziel_y)[0], ziel_x , pos_x)[0]
#    p2 = -quad(lambda y: E_Feld(pos_x,y)[1], ziel_y , pos_y)[0]
#    p_gesamt = p1 + p2
    ##error = quad(lambda x: E_Feld(x,ziel[1])[0], ziel[0] + 0.01 , pos_x)[1] + quad(lambda y: E_Feld(pos_x,y)[1], ziel[1] , pos_y)[1]
#    return p_gesamt



def map_p(pos_x, pos_y):
    gesamt = ((ziel_x - ori_x) ** 2 + (ziel_y - ori_y) ** 2) ** 0.5
    s = ((pos_x - ziel_x) ** 2 + (pos_y - ziel_y) ** 2) ** 0.5
    return s / gesamt * 100

start = [ori_x, ori_y, map_p(ori_x, ori_y)]
goal = [ziel_x, ziel_y, map_p(ziel_x, ziel_y)]
t = 0


def check(pos_x, pos_y, obstacles, i = -1): #return the index of which obstacle is met, if no obstacle met, return -1
    #while obstacles:
        #current_obstacle = obstacles.pop()
        #obstacles.append(current_obstacle)
    #for obstacle in obstacles:
    #    if (pos_x - obstacle[0]) ** 2 + (pos_y - obstacle[1]) ** 2 <= obstacle[2] ** 2:
    #        return False
    
    for obstacle in obstacles:
        d = ((pos_x - obstacle[0])**2+(pos_y - obstacle[1])**2) ** 0.5
        i += 1
        if d < obstacle[2]:
            return i   
    return -1


def p(pos_x, pos_y, obstacles):
    if check(pos_x, pos_y, obstacles) > -1:
        index = check(pos_x, pos_y, obstacles)
        d = ((pos_x - obstacles[index][0])**2+(pos_y - obstacles[index][1])**2) ** 0.5
        if d == 0 :
            return 10000000000000
        return map_p(pos_x, pos_y) + 200*(1/d - 1/obstacles[index][2])
    else:
        return map_p(pos_x, pos_y)
#       return 300



def absw(pos_x, pos_y):
    absw =  (mt.atan2((ziel_y - pos_y),(ziel_x - pos_x))) / np.pi * 180
    return absw

def maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w, radius_division = 180, v = [], delta_p = []):
    if check(pos_x, pos_y, obstacles):
        theta = np.linspace(absw(pos_x,pos_y) - 90, absw(pos_x,pos_y) + 90, radius_division)
        #test-radius 1
        test_x = [a + b for a, b in zip(np.linspace(pos_x, pos_x, radius_division), 1 * np.cos(theta * np.pi / 180))]
        test_y = [a + b for a, b in zip(np.linspace(pos_y, pos_y, radius_division), 1 * np.sin(theta * np.pi / 180))]
        
        neu_p = list(map(p, test_x, test_y, [obstacles] * radius_division))
        alt_p = list(map(p, np.linspace(pos_x, pos_x, radius_division), np.linspace(pos_y, pos_y, radius_division), [obstacles] * radius_division))
        delta_p = [a - b for a, b in zip(alt_p, neu_p)]
        
       #v = vel(theta, phi_w, v_max, v_w)
        v = list(map(vel, theta, [phi_w] * radius_division, [v_max] * radius_division, [v_w] * radius_division))
        
        #Kostfunktion, J = delta_p * v
        J = [a * b  for a, b in zip(delta_p, v)]
        
        max_v = v[J.index(max(J))]
        max_v_winkel = theta[J.index(max(J))]
        if max_v == 0 and phi_w > absw(pos_x,pos_y):
            max_v = vel(phi_w - 150, phi_w, v_max, v_w)
            max_v_winkel = phi_w - 150
        elif max_v == 0 and phi_w < absw(pos_x,pos_y):
            max_v = vel(phi_w + 150, phi_w, v_max, v_w)
            max_v_winkel = phi_w + 150
        return max_v, max_v_winkel, test_x[J.index(max(J))], test_y[J.index(max(J))], 1 / max_v       
    
    else:
        print('Das boot ist auf ein Riff aufgelaufen!')
        return 0

def move(pos_x, pos_y, obstacles, phi_w, v_max, v_w):
    new_x = maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w)[2]
    new_y = maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w)[3]
    xx.append(pos_x)
    yy.append(pos_y)
    return new_x, new_y

def yacht_simulation(pos_x, pos_y, phi_w, v_max, v_w, t = 0):
    while ((pos_x < ziel_x - 5) or (pos_y < ziel_y - 5)) and (maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w)[0] != 0):
        pos_x, pos_y = move(pos_x, pos_y, obstacles, phi_w, v_max, v_w)
        t += maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w)[4]
        print('Das Segelboat fährt nach winkel ', maxv(pos_x, pos_y, obstacles, phi_w, v_max, v_w)[1])
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
    
    ori_x = 0
    ori_y = 0
    ziel_x= 100
    ziel_y= 100
    v_w = 20
    v_max = 40
    phi_w = -90
    add_obstacle(40,40,6)
    add_obstacle(20,20,5)
    add_obstacle(50,20,10)
    yacht_simulation(ori_x, ori_y, phi_w, v_max, v_w)
    plt.arrow(x=0 , y=ziel_y, dx= 5 * np.cos(phi_w*np.pi/180) ,dy= 5 * np.sin(phi_w*np.pi/180), width=0.5)
    font = {'family': 'serif',
        'color':  'darkred',
        'weight': 'normal',
        'size': 8,
        }
    plt.text(10, 95, r'Windrichtung: ' + str(phi_w) + ' Grad', fontdict=font)
    plt.show()
#      4.560858173
#      2.03557211759  


#(5.4999187417504523, 2.3793238422665128)
#        yacht_simulation(4.560858173,2.03557211759,90,40,20)
    
    

    
    
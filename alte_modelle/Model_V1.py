#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Nov  4 14:57:13 2017

"""
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
import math as mt
import matplotlib.pyplot as plt
import matplotlib.patches as patches

v_max = 100
ori_x = 0
ori_y = 0 
pos_x=0.000000000000000
pos_y=0.000000000000000
ziel_x= 100
ziel_y= 100
#delta_t=0.02
t=0
v30 = 0.8787
xx = [0]
yy = [0]
#phi_w wind Winkel
#v_max wind Geschwindigkeit
def vel(phi,phi_w,v_max,v_w): # possible degree between -180 and 180
    # angle starts from x-Axis and is in counterclock direction positiv
    # and with cllock direction negativ
    #velocity polar diagram (numbers can be ignored)
    v30 = 0.8787
    v55 = 1
    v90 = 0.9696
    v130 = 1
    v145 = 0.8787
    v155 = 0.7575
    v180 = 0.7272

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
    if 0 <= abs(phi_b) <= 25:
        vel = (v180 + ((v155-v180) / (25-0)) * (abs(phi_b)-0))*v_akt
    if 25 <= abs(phi_b) <= 35:
        vel = (v155 + ((v145-v155) / (35-25)) * (abs(phi_b)-25))*v_akt
    if 35 <= abs(phi_b) <= 50:
        vel = (v145 + ((v130-v145) / (50-35)) * (abs(phi_b)-35))*v_akt
    if 50 <= abs(phi_b) <= 90:
        vel = (v130 + ((v90-v130) / (90-50)) * (abs(phi_b)-50))*v_akt
    if 90 <= abs(phi_b) <= 125:
        vel = (v90 + ((v55-v90) / (125-90)) * (abs(phi_b)-90))*v_akt
    if 125 <= abs(phi_b) <= 150:
        vel = (v55 + ((v30-v55) / (150-125)) * (abs(phi_b)-125))*v_akt
    if abs(phi_b) > 150:
        vel = 0
    
    return vel, phi_b

def absw(pos_x, pos_y):
    absw =  (mt.atan2((ziel_y - pos_y),(ziel_x - pos_x))) / np.pi * 180
    return absw
    #absolute Winkel des Segelboat in °

    
def maxv(phi_w, v_w,pos_x,pos_y):
    #maxvel = 0
    #maxv_winkel = 0
    winkel = abs(vel(absw(pos_x,pos_y), phi_w, v_max, v_w)[1])
    real_winkel = vel(absw(pos_x,pos_y), phi_w, v_max, v_w)[1]#in °
    #if (winkel <= 180) and (winkel > 155):
    if (winkel <= 180) and (winkel > 150):
        if real_winkel < 0:
            maxv_winkel = absw(pos_x,pos_y) + abs(real_winkel + 150)
            maxvel = min(v_w,v_max) * v30
        elif real_winkel >= 0:
            maxv_winkel = absw(pos_x,pos_y) - abs(real_winkel - 150)
            maxvel = min(v_w,v_max) * v30
        #maxvel = 0
        #maxv_winkel = absw(pos_x, pos_y)
    #elif (winkel <= 155) and (winkel > 130):
    elif (winkel <= 150) and (winkel > 130):
        maxv_winkel = absw(pos_x, pos_y) - 5
        maxvel = vel(maxv_winkel, phi_w, v_max, v_w)[0]
    elif (winkel <= 130) and (winkel > 120):
        maxv_winkel = absw(pos_x, pos_y) + 5
        #maxv_winkel = phi_w + 125
        maxvel = vel(phi_w + 125, phi_w, v_max, v_w)[0]
    elif (winkel <= 120) and (winkel > 95):
        maxv_winkel = absw(pos_x, pos_y) + 5
        maxvel = vel(maxv_winkel, phi_w, v_max, v_w)[0]
    elif (winkel <= 95) and (winkel > 85):        
        maxvel = max(vel(absw(pos_x,pos_y) - 5, phi_w, v_max, v_w)[0], vel(absw(pos_x,pos_y) + 5, phi_w, v_max, v_w)[0])
        if vel(absw(pos_x,pos_y) + 5, phi_w, v_max, v_w)[0] > vel(absw(pos_x,pos_y) - 5, phi_w, v_max, v_w)[0]:
            maxv_winkel = absw(pos_x,pos_y) + 5
        else:
            maxv_winkel = absw(pos_x,pos_y) - 5
    elif (winkel <= 85) and (winkel > 55):
        maxv_winkel = absw(pos_x, pos_y) - 5
        maxvel = vel(maxv_winkel, phi_w, v_max, v_w)[0]
    elif (winkel <= 55) and (winkel > 45):
#        maxv_winkel = phi_w + 50
        maxv_winkel = phi_w - 50
        maxvel = vel(phi_w + 50, phi_w, v_max, v_w)[0]
    elif (winkel <= 45) and (winkel > 0):
        maxv_winkel = absw(pos_x,pos_y) + 5
        maxvel = vel(maxv_winkel, phi_w, v_max, v_w)[0]
    return maxvel,maxv_winkel,winkel


    
    
    

def move(pos_x, pos_y, v_w, phi_w, v_max, delta_t = 0.08):
    print('Das Segelboat fährt nach winkel ', maxv(phi_w, v_w, pos_x, pos_y)[1])
    print(' x ist jetzt ',pos_x, '\n', 'y ist jetzt ', pos_y)
    vr = maxv(phi_w, v_w, pos_x, pos_y)[0]
    vrwinkel = maxv(phi_w, v_w, pos_x, pos_y)[1]/ 180 * np.pi
    pos_x += delta_t * vr * np.cos(vrwinkel)
    pos_y += delta_t * vr * np.sin(vrwinkel)
    xx.append(pos_x)
    yy.append(pos_y)
    return pos_x, pos_y
    
def yacht(pos_x, pos_y, v_w, phi_w, v_max, delta_t = 0.08, t = 0):
    while ((pos_x < ziel_x - 5) or (pos_y < ziel_y - 5)) and (maxv(phi_w, v_w, pos_x, pos_y)[0] != 0):
        pos_x, pos_y = move(pos_x, pos_y, v_w, phi_w, v_max, delta_t = 0.08)
        t += delta_t
        print(t)
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
#def animate(i):
#   """change the divisor of i to get a faster (but less precise) animation """
#    x = (xx[i],xx[i+1],xx[i+2],xx[i+3],xx[i+4],xx[i+5],xx[i+6],xx[i+7],xx[i+8],xx[i+9])
#    y = (yy[i],yy[i+1],yy[i+2],yy[i+3],yy[i+4],yy[i+5],yy[i+6],yy[i+7],yy[i+8],yy[i+9])
#    line.set_data(x, y)
#    return line,           
#def d():
#    yacht(0,0,20,40,30)
    
#    fig, ax = plt.subplots()
#    xt = [xx[0],xx[0+1],xx[0+2],xx[0+3],xx[0+4],xx[0+5],xx[0+6],xx[0+7],xx[0+8],xx[0+9]]
#    yt = [yy[0],yy[0+1],yy[0+2],yy[0+3],yy[0+4],yy[0+5],yy[0+6],yy[0+7],yy[0+8],yy[0+9]]
#    line, = ax.plot(xt, yt) 
    
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
       # yacht(0,0,20,-30,40)
if __name__ == "__main__":
    
    
     phi_w = 120
     phi = phi_w / 180 * np.pi  
     xx,yy = yacht(0,0,20,phi_w,40)
     
     plt.arrow(x=0 , y=ziel_y, dx= 5 * np.cos(phi) ,dy= 5 * np.sin(phi), width=0.5)
     plt.show()
    #plt.plot(xx,yy)
    
# -*- coding: utf-8 -*-
"""
Created on Mon Nov 13 11:10:19 2017

@author: Mohamed
"""
import numpy as np
import matplotlib.pyplot as plt
from scipy.interpolate import UnivariateSpline

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
        

    return vel
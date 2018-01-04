# -*- coding: utf-8 -*-
"""
Created on Mon Nov 13 11:10:19 2017

@author: Mohamed
"""

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

    # # interpolating the velocity depending of phi_b
    # if 0 <= abs(phi_b) <= 25:
        # vel = (v180 + ((v155-v180) / (25-0)) * (abs(phi_b)-0))*v_akt
    # if 25 <= abs(phi_b) <= 35:
        # vel = (v155 + ((v145-v155) / (35-25)) * (abs(phi_b)-25))*v_akt
    # if 35 <= abs(phi_b) <= 50:
        # vel = (v145 + ((v130-v145) / (50-35)) * (abs(phi_b)-35))*v_akt
    # if 50 <= abs(phi_b) <= 90:
        # vel = (v130 + ((v90-v130) / (90-50)) * (abs(phi_b)-50))*v_akt
    # if 90 <= abs(phi_b) <= 125:
        # vel = (v90 + ((v55-v90) / (125-90)) * (abs(phi_b)-90))*v_akt
    # if 125 <= abs(phi_b) <= 150:
        # vel = (v55 + ((v30-v55) / (150-125)) * (abs(phi_b)-125))*v_akt
    # if abs(phi_b) > 150:
        # vel = 0
    
    return vel
import socket
import sys

def reading(sensor):
	import time
	import RPi.GPIO as GPIO
	
	GPIO.setwarnings(False)

	GPIO.setmode (GPIO.BCM)

	if sensor == 0:
		#set up in/outs
		GPIO.setup(23, GPIO.OUT)
		GPIO.setup(24, GPIO.IN)
		GPIO.setup(23, GPIO.LOW)
		#If it breaks, increase this
		time.sleep(0.5)
		#pulses :3
		GPIO.output(23, True)
		time.sleep(0.00001)
		GPIO.output(23, False)

		#print GPIO.input(24)
		#what's happening? 0 = nothing
		while GPIO.input(24) == 0:
			signaloff = time.time()
		#	print "False"
		#1 = a thing :3 
		while GPIO.input(24) == 1:
			signalon = time.time() 
		#	print "True"
		#differences in times
		timePassed = signalon - signaloff
		#just a simple calculation...
		distance = int(timePassed * 17000)
		#and we can finish up here :)
		return distance
		
		GPIO.cleanup()
	else:
		print "Incorrect usonic() function variable."




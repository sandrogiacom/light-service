import RPi.GPIO as GPIO
from time import sleep

GPIO.setwarnings(False)

print(GPIO.RPI_INFO['P1_REVISION'])

GPIO.setmode(GPIO.BCM)
GPIO.setup(18, GPIO.OUT)
GPIO.output(18, 1)

print("light on")

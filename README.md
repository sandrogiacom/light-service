# light-service
Control lights with Java, Python and Raspberry pi


## Start Java app on boot

```
crontab -e
```

Add the line below

```
@reboot java -jar /home/pi/light-service.jar > log.txt &
```

# Research IR Sensor Compatibility
Investigate how to connect and read input from an IR sensor on the Raspberry Pi.
The IR Sensor Module has only three Pins: VCC, GND and Data. Connect the VCC and GND pins of the IR Sensor to +5V and GND pins of the Raspberry Pi.
Then connect the Data pin of the IR Sensor to GPIO23 i.e. Physical Pin 16 of the Raspberry Pi.

On a Raspberry Pi, the GPIO pins can be used to both send and receive IR signals.

 Receiving IR Signals (IR Sensor)
 -
You can use any GPIO pin to receive IR signals from an IR sensor (e.g., TSOP4838), but commonly used GPIO pins for receiving IR signals are:
GPIO 18 (Physical Pin 12): This is commonly used because it has hardware PWM support, which may help in decoding the IR signals.
GPIO 17 (Physical Pin 11)
GPIO 23 (Physical Pin 16)

# Write IR Sensor Input Handling Code
Implement code to capture input from the IR remote.

Installing Necessary Software
The software package ir-keytable is installed to handle parsing and mapping raw IR signals to user-defined keys. This will help detect the IR signals from your remote control and map those to control actions (play, pause, next, etc.).
Additionally, the article recommends installing LIRC (Linux Infrared Remote Control), which allows receiving, decoding, and sending IR commands. This is crucial for both sending and receiving commands in your Media Player project.

irw is a utility that comes with LIRC (Linux Infrared Remote Control). It is used to monitor and display IR signals received by the IR receiver connected to your Raspberry Pi. When you run irw, it listens for signals from the IR receiver and prints the decoded key presses to the terminal.
Start LIRC Service: Ensure that the LIRC daemon (lircd) is running. If it’s not running, you can start it with:
```
ps aux
sudo systemctl start lircd
sudo irw
```
Output: When you press a button on your remote control, irw will show something like:
```
0000000000000001 KEY_PLAY
```
# Map Remote Control to GUI Functions
Map IR remote buttons to media control actions (play, pause, next track, etc.).

Creating Remote Control Configuration
The irrecord command allows you to map raw IR signals to meaningful names (such as "play" or "pause"). This is key to mapping the remote control buttons to the appropriate media player actions.

# Test Remote Control Integration
Test the remote control with the media player GUI.

https://www.digikey.pl/pl/maker/tutorials/2021/how-to-send-and-receive-ir-signals-with-a-raspberry-pi

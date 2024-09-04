# Music-Player-Application-

Project Overview
-
This project involves developing a Music Player application controlled by a remote control. The application is an Embedded Linux Java application with a GUI that features control buttons for next track, previous track, pause, play, stop, and seek operations. The project will be deployed on a Raspberry Pi 4 using a custom Yocto-built image.

# 1. Platform Setup
1.1 Yocto Project Setup
-
Clone the Yocto Project sources and set up the Raspberry Pi layers:
```
git clone git://git.yoctoproject.org/poky
cd poky
git clone git://git.openembedded.org/meta-openembedded
git clone git://git.yoctoproject.org/meta-raspberrypi
```
1.2 Configure the Build
-
Set up the Yocto build environment:
```
source oe-init-build-env
```
In conf/local.conf, set the machine to Raspberry Pi 4:
```
MACHINE = "raspberrypi4-64"
```
Add the necessary layers in bblayers.conf:

```
BBLAYERS += "path/to/meta-raspberrypi"
BBLAYERS += "path/to/meta-openembedded/meta-oe"
```
# 2. Software Installation
2.1 Java Runtime Environment
-
Add OpenJDK 8 JRE to your Yocto image:
```
IMAGE_INSTALL:append = " openjdk-8-jre" // this has a problem
```
Why OpenJDK 8?
-
Provides a stable and efficient Java Runtime Environment.
Ensures compatibility with JavaFX or Swing-based GUI applications.
Suitable for embedded systems due to its balance between performance and resource usage.

2.2 Media Playback Support
-
Install GStreamer for media playback:
```
IMAGE_INSTALL_append = " gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good gstreamer1.0-plugins-bad" // this has a problem
```
Install ALSA for audio management:
```
IMAGE_INSTALL_append = " alsa-utils"
```
# 3. Remote Control Integration
3.1 LIRC for IR Remote Control
-
Install LIRC to handle IR remote control signals:

To interact with your JavaFX GUI using a remote control with an IR sensor, you can map the IR remote's signals to key events, similar to how you would handle keyboard inputs

You'll need to interface the IR sensor with your embedded Linux system to capture the remote control signals.

This can be done using an IR receiver module and a library like LIRC (Linux Infrared Remote Control) to decode the signals into recognizable inputs.

Configure LIRC to map specific IR signals to keyboard key events. This allows you to send keypresses to your JavaFX application when specific buttons on the remote are pressed.
```
IMAGE_INSTALL_append = " lirc"
```
Configure LIRC for your specific remote control:
Create LIRC configuration files mapping remote control buttons to corresponding key presses .
Ensure these configurations are included in your Yocto image.

3.2 Hardware Setup
-
Connect the IR receiver to the Raspberry Pi’s GPIO pins.
Configure the GPIO pins in Yocto if necessary.

# 4. Application Deployment
4.1 Java Application Deployment
-
Write a Yocto recipe (my-music-player.bb) for your Music Player application:
```
SUMMARY = "Music Player Application"
LICENSE = "CLOSED"
SRC_URI = "file://my-music-player.jar"

do_install() {
    install -d ${D}/usr/share/my-music-player
    install -m 0755 ${WORKDIR}/my-music-player.jar ${D}/usr/share/my-music-player/
}
```
Add the application to your image:
```
IMAGE_INSTALL_append = " my-music-player"
```
4.2 Autostart Configuration
-
Create a systemd service to start your application on boot:
```
[Unit]
Description=Music Player Application
After=network.target

[Service]
ExecStart=/usr/bin/java -jar /usr/share/my-music-player/my-music-player.jar
Restart=on-failure

[Install]
WantedBy=multi-user.target
```
Include this service in your Yocto image:
```
IMAGE_INSTALL_append = " my-music-player-service"
```
# 5. Building the Image
Build the custom Yocto image for your Raspberry Pi 4:
```
bitbake core-image-sato
```














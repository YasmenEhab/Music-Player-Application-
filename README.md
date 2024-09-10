# Music-Player-Application-

Project Overview
-
This project involves developing a Music Player application controlled by a remote control. The application is an Embedded Linux Java application with a GUI that features control buttons for next track, previous track, pause, play, stop, and seek operations. The project will be deployed on a Raspberry Pi 4 using a custom Yocto-built image.

# 1. Platform Setup
1.1 Yocto Project Setup
-
Clone the Yocto Project sources and set up the Raspberry Pi layers:
```
mkdir yocto
cd yocto
git clone -b kirkstone https://git.yoctoproject.org/git/poky
cd poky
git clone -b kirkstone https://github.com/agherzan/meta-raspberrypi.git
git clone -b kirkstone https://github.com/openembedded/meta-openembedded.git
git clone -b kirkstone https://github.com/meta-java/meta-java.git


```
1.2 Configure the Build
-
Set up the Yocto build environment:
```
source oe-init-build-env
```
In conf/local.conf, set the machine to Raspberry Pi 4:
```
MACHINE = "raspberrypi4"
```
Add the necessary layers in bblayers.conf:

```
BBLAYERS ?= " \
  /home/yasmen/yocto/poky/meta \
  /home/yasmen/yocto/poky/meta-poky \
  /home/yasmen/yocto/poky/meta-yocto-bsp \
  /home/yasmen/yocto/poky/meta-raspberrypi \
  /home/yasmen/yocto/poky/meta-openembedded/meta-oe \
  /home/yasmen/yocto/poky/meta-java \
  /home/yasmen/yocto/poky/meta-openembedded/meta-multimedia \
  /home/yasmen/yocto/poky/meta-openembedded/meta-python \
  /home/yasmen/yocto/poky/meta-openembedded/meta-networking \
  /home/yasmen/yocto/poky/meta-openembedded/meta-gnome \
  /home/yasmen/yocto/poky/meta-openembedded/meta-xfce \	
  /home/yasmen/yocto/meta-custom-rpi4 \
  "
```
# 2. Software Installation
2.1 Java Runtime Environment
-
Add OpenJDK 8 JRE to your Yocto image:
```
IMAGE_INSTALL:append = " openjdk-8" // this has many warnings 31
```
https://community.nxp.com/t5/i-MX-Processors-Knowledge-Base/How-to-add-openjdk-to-Yocto-Layers/ta-p/1128283
```
bitbake -k myimage
cd tmp
cd deploy/
cd images/
cd raspberrypi4/
ls | grep sdi
du -sh myimage-raspberrypi4-20240905143444.rootfs.rpi-sdimg
sudo dd if=myimage-raspberrypi4-20240905143444.rootfs.rpi-sdimg of=/dev/sdb bs=4M status=progress
```
![image](https://github.com/user-attachments/assets/f85e3322-b174-4838-970a-890e4da7b96b)


Why OpenJDK 8?
-
Provides a stable and efficient Java Runtime Environment.
Ensures compatibility with JavaFX or Swing-based GUI applications.
Suitable for embedded systems due to its balance between performance and resource usage.

The error you're encountering is likely due to missing JavaFX libraries in your environment. Starting from JDK 11, JavaFX is no longer bundled with the JDK, and needs to be included separately. However, since you're using JDK 8, JavaFX should be bundled, but it seems that the runtime environment isn't correctly locating the JavaFX classes

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
---
## 3.1 Configuring LIRC for JavaFX Interaction
---
To enable interaction with your JavaFX GUI using an IR remote, you need to map the IR remote's signals to key events, similar to handling keyboard inputs.

1. **Interface the IR Sensor:** Connect the IR sensor with your embedded Linux system to capture remote control signals. This requires an IR receiver module and a library like LIRC (Linux Infrared Remote Control) to decode the signals into recognizable inputs.

2. **Configure LIRC:** Set up LIRC to map specific IR signals to keyboard key events. This allows you to send keypresses to your JavaFX application when specific buttons on the remote are pressed.

## 3.2 Hardware Setup
---
- Connect the three pins of the IR sensor: VCC, GND, and the S pin to GPIO pin 18.
- Navigate to `/boot` and create a `config.txt` file. Define the GPIO pin as an IR pin by adding the following line:

```bash
dtoverlay=gpio-ir,gpio_pin=18
```

## 3.3 LIRC Configuration
---
Include the LIRC device driver in your image configuration:

```bash
IMAGE_INSTALL:append = " lirc"
MACHINE_FEATURES:append = " lirc"
KERNEL_MODULE_AUTOLOAD += "lirc_dev lirc_rpi"
LIRC_CONF_PATH = "/etc/lirc/lirc.conf"
ENABLE_IR = "1"
```

These commands in a Yocto Project build configuration enable support for the Linux Infrared Remote Control (LIRC) subsystem:

1. `IMAGE_INSTALL:append = " lirc"`: Adds the LIRC package to the list of software to be included in the image.
2. `MACHINE_FEATURES:append = " lirc"`: Adds LIRC support to the machine configuration, indicating the requirement for LIRC hardware or software.
3. `KERNEL_MODULE_AUTOLOAD += "lirc_dev lirc_rpi"`: Ensures that the LIRC kernel modules (`lirc_dev` and `lirc_rpi`) are automatically loaded at boot.
4. `LIRC_CONF_PATH = "/etc/lirc/lirc.conf"`: Specifies the path to the LIRC configuration file.
5. `ENABLE_IR = "1"`: Enables infrared (IR) functionality, likely within the build or runtime configuration.

These settings configure and enable infrared remote control support for your system.

After building your image, it will support IR devices.

1. **Check IR Pulses:** Use the following command to observe IR pulses:

```bash
mode2 -m -d /dev/lirc1
```
![Screenshot from 2024-09-10 18-32-41](https://github.com/user-attachments/assets/90db761d-9cdd-42f5-8636-4d340e7f22a9)

2. **Record IR Signals:** Capture the signals from each button on your IR remote using:

```bash
irrecord -d /dev/lirc1
```
![Screenshot from 2024-09-10 18-38-00](https://github.com/user-attachments/assets/e9ad14f6-f17c-453d-930e-79403ccd80f7)

3. **Place Configuration File:** Move the generated configuration file to:

```bash
/etc/lirc
```
Ensure the file is named `lircd.conf`.

![Screenshot from 2024-09-10 18-41-02](https://github.com/user-attachments/assets/006785ee-1831-4f4c-8ab7-a225b9244925)

4. **Modify LIRC Configuration:** Edit the `lirc_options.conf` file:

- Set the driver to `default`.
- Set the device to `/dev/lirc1`.

![Screenshot from 2024-09-10 18-43-58](https://github.com/user-attachments/assets/31cdee8d-8394-48fb-958d-f0fd416eb8f2)

5. **Test IR Signals:** Verify that the IR signals are being read with:

```bash
irw
```

![Screenshot from 2024-09-10 18-42-25](https://github.com/user-attachments/assets/057bb9c0-29f2-40ee-86c4-3692a02c7cb7)

---
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














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
//git clone -b kirkstone https://github.com/meta-java/meta-java.git --> got problem


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
IMAGE_INSTALL:append = " openjdk-8" // this has many warnings 31 --> got a problem , look  into yocto folder to know the details
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

**problem:** While attempting to capture frames from the IR remote, none of the frames were detected, and the configuration file generated had a value of 0x00 for all keys. We resolved this issue by defining the sbin for the IR to gpio-ir during boot. Additionally, we ensured that the GPIO IR overlays were loaded in /boot/overlays at startup.

**Problem:** After the configuration file is generated, it contains frames with two parts, for example, `0x0864F5987 0xFFFFFFFF`. This is incorrect as only the first part should be present. Therefore, we manually remove the second part, which in my case was `0xFFFFFFFF`.

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

# local.conf
```
MACHINE ??= "raspberrypi4-64"
DISTRO ?= "mediaplayer"
DL_DIR ?= "/home/yasmen/yocto/poky/build/downloads"
SSTATE_DIR ?= "/home/yasmen/yocto/poky/build/sstate-cache"

BB_NUMBER_THREADS ?= "8"
PARALLEL_MAKE ?= "-j 8"
INHERIT += "rm_work"

# Configure LIRC support
IMAGE_INSTALL:append = " lirc"
#MACHINE_FEATURES:append = " lirc"
#KERNEL_MODULE_AUTOLOAD += "lirc_dev lirc_rpi"
#LIRC_CONF_PATH = "/etc/lirc/lirc.conf"
ENABLE_IR = "1"

```

# Hierarchy

![image](https://github.com/user-attachments/assets/465d796f-d481-4b03-882e-446912414fd5)

![image](https://github.com/user-attachments/assets/4c1e266d-69e3-482c-b8da-d3ab994bd702)

![image](https://github.com/user-attachments/assets/63a4881e-a071-42a1-9e8f-fa6fa4144c68)

mediaplayer.conf
-
```
DISTRO_FEATURES:append = " systemd x11 wayland opengl gtk3 gnome dbus"
DISTRO_FEATURES_BACKFILL_CONSIDERED += "sysvinit"
VIRTUAL-RUNTIME_init_manager = "systemd"
VIRTUAL-RUNTIME_initscripts = "systemd-compat-units"
#VIRTUAL-RUNTIME_initscripts = ""
VIRTUAL-RUNTIME_dev_manager = "systemd"
```
![image](https://github.com/user-attachments/assets/89221680-7fbf-4a0f-9bbb-faf917535740)

![image](https://github.com/user-attachments/assets/f6110e43-d35c-4ad1-be0e-71c081e6d2a4)

myimage.bb
-

```
DESCRIPTION = "Image with Sato, a mobile environment and visual style for \
mobile devices. The image supports X11 with a Sato theme, Pimlico \
applications, and contains terminal, editor, and file manager."
HOMEPAGE = "https://www.yoctoproject.org/"

IMAGE_FEATURES += "splash package-management x11-base x11-sato hwcodecs"

LICENSE = "MIT"

inherit core-image


TOOLCHAIN_HOST_TASK:append = " nativesdk-intltool nativesdk-glib-2.0"
TOOLCHAIN_HOST_TASK:remove:task-populate-sdk-ext = " nativesdk-intltool nativesdk-glib-2.0"

QB_MEM = '${@bb.utils.contains("DISTRO_FEATURES", "opengl", "-m 512", "-m 256", d)}'
QB_MEM:qemuarmv5 = "-m 256"
QB_MEM:qemumips = "-m 256"


PACKAGE_CLASSES ?= "package_rpm opkg"
IMAGE_INSTALL:append = " opkg"

#DISTRO_FEATURES:append = " systemd x11 wayland opengl gtk3 gnome dbus"
#DISTRO_FEATURES_BACKFILL_CONSIDERED += "sysvinit"
#VIRTUAL-RUNTIME_init_manager = "systemd"
#VIRTUAL-RUNTIME_initscripts = "systemd-compat-units"
#VIRTUAL-RUNTIME_initscripts = ""
#VIRTUAL-RUNTIME_dev_manager = "systemd"
IMAGE_INSTALL += " packagegroup-core-boot"
IMAGE_ROOTFS_EXTRA_SPACE = "5242880"
IMAGE_INSTALL:append = " x11vnc"


#MP3 Config
IMAGE_INSTALL:append = " gstreamer1.0-plugins-good gstreamer1.0-plugins-base gstreamer1.0-plugins-ugly mpg123"
LICENSE_FLAGS_ACCEPTED:append = " commercial commercial_gstreamer1.0-plugins-ugly"
PACKAGECONFIG:pn-qtmultimedia = " gstreamer alsa" 

#stream mp3 by blutooth
DISTRO_FEATURES:append = " pulseaudio"
KERNEL_MODULE_AUTOLOAD:rpi = "snd-bcm2835"
MACHINE_FEATURES:append = " sound"
IMAGE_INSTALL:append = " pulseaudio pulseaudio-module-dbus-protocol pulseaudio-server pulseaudio-module-bluetooth-discover pulseaudio-module-bluetooth-policy pulseaudio-module-bluez5-device pulseaudio-module-bluez5-discover alsa-utils alsa-lib alsa-plugins mpg123 alsa-tools alsa-state dbus"

#for debugging 
IMAGE_INSTALL:append = " strace"
#IMAGE_INSTALL:remove = "packagegroup-core-ssh-dropbear"
#EXTRA_IMAGE_FEATURES += "allow-empty-password allow-root-login ssh-server-openssh"






#for blutooth and Wifi

IMAGE_INSTALL:append = " \
    python3 \
    bluez5 \
    i2c-tools \
    bridge-utils \
    hostapd \
    iptables \
    wpa-supplicant \
    pi-bluetooth \
    bluez5-testtools \
    udev-rules-rpi \
    linux-firmware \
    iw \
    kernel-modules \
    linux-firmware-ralink \
    linux-firmware-rtl8192ce \
    linux-firmware-rtl8192cu \
    linux-firmware-rtl8192su \
    linux-firmware-rpidistro-bcm43430 \
    linux-firmware-bcm43430 \
    connman \
    connman-client \
    dhcpcd \
    openssh \
    psplash \
    psplash-raspberrypi \
"


DISTRO_FEATURES:append = " \
    bluez5 \
    bluetooth \
    wifi \
    pi-bluetooth \
    linux-firmware-bcm43430 \
    systemd \
    usrmerge \
    ipv4 \
"
MACHINE_FEATURES:append = " \
    bluetooth \
    wifi \
"

IMAGE_FEATURES:append = " \
    splash \
"
IMAGE_INSTALL:append = " xserver-xorg xinit xf86-video-fbdev xf86-input-evdev xterm matchbox-wm"

EXTRA_IMAGE_FEATURES += "tools-debug"
IMAGE_INSTALL:append = " libavcodec libavformat libavutil libswscale"
IMAGE_INSTALL:append = " ffmpeg"



IMAGE_FSTYPES = "tar.bz2 ext4 rpi-sdimg"
```



















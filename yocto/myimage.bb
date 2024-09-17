DESCRIPTION = "Image with Sato, a mobile environment and visual style for \
mobile devices. The image supports X11 with a Sato theme, Pimlico \
applications, and contains terminal, editor, and file manager."
HOMEPAGE = "https://www.yoctoproject.org/"

IMAGE_FEATURES += "splash package-management x11-base x11-sato ssh-server-dropbear hwcodecs"

LICENSE = "MIT"

inherit core-image


TOOLCHAIN_HOST_TASK:append = " nativesdk-intltool nativesdk-glib-2.0"
TOOLCHAIN_HOST_TASK:remove:task-populate-sdk-ext = " nativesdk-intltool nativesdk-glib-2.0"

QB_MEM = '${@bb.utils.contains("DISTRO_FEATURES", "opengl", "-m 512", "-m 256", d)}'
QB_MEM:qemuarmv5 = "-m 256"
QB_MEM:qemumips = "-m 256"



PACKAGE_CLASSES ?= "package_rpm opkg"
IMAGE_INSTALL:append = " opkg"

DISTRO_FEATURES:append = " systemd"
VIRTUAL-RUNTIME_init_manager = " systemd"
VIRTUAL-RUNTIME_initscripts = "systemd-compat-units"

IMAGE_INSTALL += " packagegroup-core-boot"
IMAGE_ROOTFS_EXTRA_SPACE = "5242880"
IMAGE_INSTALL:append = " x11vnc"


#MP3 Configuration
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
IMAGE_INSTALL:append = " xserver-xorg xf86-video-fbdev xf86-input-evdev xterm matchbox-wm"

EXTRA_IMAGE_FEATURES += "tools-debug"


IMAGE_FSTYPES = "tar.bz2 ext4 rpi-sdimg"
































# # Add apt and package management
# IMAGE_INSTALL:append = " apt"
# EXTRA_IMAGE_FEATURES += "package-management"
# IMAGE_INSTALL:append=" packagegroup-core-boot"
# #IMAGE_INSTALL:append = " mybsp"


# # Enable MP3 support via GStreamer and other necessary packages
# IMAGE_INSTALL:append = " gstreamer1.0-plugins-good gstreamer1.0-plugins-base gstreamer1.0-plugins-ugly"
# LICENSE_FLAGS_ACCEPTED:append = " commercial  commercial_gstreamer1.0-plugins-ugly"
# PACKAGECONFIG:pn-qtmultimedia = " gstreamer alsa"


# # Streaming MP3 via Bluetooth configuration
# KERNEL_MODULE_AUTOLOAD:rpi = "snd-bcm2835"
# MACHINE_FEATURES:append = "sound"

# IMAGE_INSTALL:append = "\
#     pulseaudio pulseaudio-module-dbus-protocol pulseaudio-server \
#     pulseaudio-module-bluetooth-discover pulseaudio-module-bluetooth-policy \
#     pulseaudio-module-bluez5-device pulseaudio-module-bluez5-discover \
#     alsa-utils alsa-lib alsa-plugins mpg123 alsa-tools alsa-state dbus"

# DISTRO_FEATURES:append = "pulseaudio"

# # For debugging purposes
# #IMAGE_INSTALL:append = " strace"
# #IMAGE_INSTALL:append = " qtsvg"

# # Bluetooth and Wi-Fi setup
# IMAGE_INSTALL:append = " \
#     python3 util-linux bluez5 i2c-tools bridge-utils hostapd iptables \
#     wpa-supplicant pi-bluetooth bluez5-testtools udev-rules-rpi \
#     linux-firmware iw kernel-modules linux-firmware-ralink \
#     linux-firmware-rtl8192ce linux-firmware-rtl8192cu linux-firmware-rtl8192su \
#     linux-firmware-rpidistro-bcm43430 linux-firmware-bcm43430 connman \
#     connman-client dhcpcd openssh psplash psplash-raspberrypi coreutils"

# # Bluetooth and Wi-Fi related features
# DISTRO_FEATURES:append = "bluez5 bluetooth wifi pi-bluetooth systemd usrmerge ipv4"

# MACHINE_FEATURES:append = "bluetooth wifi"

# # Graphical environment setup
# IMAGE_INSTALL:append = " xserver-xorg xf86-video-fbdev xf86-input-evdev xterm matchbox-wm"


# DESCRIPTION = "Custom Board Support Package for Raspberry Pi with multimedia, Bluetooth, and Wi-Fi support."
LICENSE = "MIT"

# Enable MP3 support via GStreamer and other necessary packages
IMAGE_INSTALL:append = " gstreamer1.0-plugins-good gstreamer1.0-plugins-base gstreamer1.0-plugins-ugly mpg123"

LICENSE_FLAGS_ACCEPTED:append = " commercial commercial_gstreamer1.0-plugins-ugly"
PACKAGECONFIG:pn-qtmultimedia = "gstreamer alsa"

# Streaming MP3 via Bluetooth configuration
KERNEL_MODULE_AUTOLOAD:rpi = "snd-bcm2835"
MACHINE_FEATURES:append = "sound"

IMAGE_INSTALL:append = "\
    pulseaudio pulseaudio-module-dbus-protocol pulseaudio-server \
    pulseaudio-module-bluetooth-discover pulseaudio-module-bluetooth-policy \
    pulseaudio-module-bluez5-device pulseaudio-module-bluez5-discover \
    alsa-utils alsa-lib alsa-plugins mpg123 alsa-tools alsa-state dbus"

DISTRO_FEATURES:append = "pulseaudio"

# For debugging purposes
IMAGE_INSTALL:append = " strace"
IMAGE_INSTALL:append = " qtsvg"

# Bluetooth and Wi-Fi setup
IMAGE_INSTALL:append = " \
    python3 util-linux bluez5 i2c-tools bridge-utils hostapd iptables \
    wpa-supplicant pi-bluetooth bluez5-testtools udev-rules-rpi \
    linux-firmware iw kernel-modules linux-firmware-ralink \
    linux-firmware-rtl8192ce linux-firmware-rtl8192cu linux-firmware-rtl8192su \
    linux-firmware-rpidistro-bcm43430 linux-firmware-bcm43430 connman \
    connman-client dhcpcd openssh psplash psplash-raspberrypi coreutils"

# Bluetooth and Wi-Fi related features
DISTRO_FEATURES:append = "bluez5 bluetooth wifi pi-bluetooth systemd usrmerge ipv4"

MACHINE_FEATURES:append = "bluetooth wifi"

# Graphical environment setup
IMAGE_INSTALL:append = " xserver-xorg xf86-video-fbdev xf86-input-evdev xterm matchbox-wm"

# Extra debugging tools and package management
EXTRA_IMAGE_FEATURES += "tools-debug"
IMAGE_FEATURES:append = "splash"

# Optional dependencies (if you need to add other layers or recipes)
DEPENDS = "virtual/kernel"

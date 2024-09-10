# 01-Layers  Archtecture 
## our image supported these features:
-
   - Audio
   - wifi
   - bluetooth
   - video
   - ir sensor for remote controllLaers 

### LAYERS:
```bash
"/ABSOLUTE/PATH/poky/meta \
 /ABSOLUTE/PATH/poky/meta-poky \
 /ABSOLUTE/PATH/poky/meta-yocto-bsp \
 /ABSOLUTE/PATH/meta-raspberrypi \
 /ABSOLUTE/PATH/meta-openembedded/meta-oe \
 /ABSOLUTE/PATH/meta-openembedded/meta-gnome \
 /ABSOLUTE/PATH/meta-openembedded/meta-xfce \
 /ABSOLUTE/PATH/meta-openembedded/meta-python \
 /ABSOLUTE/PATH/meta-openembedded/meta-networking \
 /ABSOLUTE/PATH/meta-openembedded/meta-multimedia \
 "
```
# PROBLEMS

To integrate Java with Yocto for your media player application:
-
Architecture Compatibility: Since JavaFX is only available for 64-bit ARM and not 32-bit, switch your Yocto target architecture to ARM64 (e.g., raspberrypi4-64).
Java Version: meta-java supports OpenJDK, but for JDK 17, you'll need to manually add or modify recipes in your Yocto build.
JavaFX: Ensure your image has the necessary dependencies (OpenGL, X11/Wayland) for JavaFX support.


  
     
     



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

# Integrating Java and JavaFX with Yocto (Raspberry Pi 4 - ARM 64-bit)

## 1. 32-bit ARM Architecture Limitation
- The 32-bit ARM architecture (`arm32`) for Raspberry Pi 4 allows for Java integration using available Yocto layers such as `meta-java`.
- **JavaFX Limitation**: JavaFX is not compatible with `arm32` in the Yocto environment, so it cannot be directly included in a 32-bit image.

## 2. Switching to 64-bit ARM Architecture
- To resolve the JavaFX compatibility issue, rebuild the Yocto image targeting the 64-bit ARM architecture (`arm64` or `aarch64`).
- This allows the integration of a compatible version of both Java and JavaFX in the image.

## 3. Java and JavaFX Integration as Packages
- Once the 64-bit Yocto image is deployed, Java and JavaFX can be integrated as packages.
  - This includes adding **JDK 17** and **JavaFX SDK 22** to the image.
- These components can either be installed as built-in packages or post-deployment as plugin-style add-ons.

## 4. Versions
- For the 64-bit image, use the following versions for compatibility:
  - **JDK**: Version 17
  - **JavaFX SDK**: Version 22

---

Switching to the 64-bit architecture resolves the JavaFX compatibility issue and enables full integration of the JavaFX GUI for media player applications or other uses.

# 02- Layer Hirarchy (our meta-custom-raspi4)
![Screenshot from 2024-09-11 01-42-54](https://github.com/user-attachments/assets/d78e13cf-884a-4d3f-b210-1c45fb227cc6)

  ![Screenshot from 2024-09-11 01-27-48](https://github.com/user-attachments/assets/77dcdf34-1ada-4311-a17b-8d37810f497d)

 #### -distro.conf
 
![Screenshot from 2024-09-11 01-50-44](https://github.com/user-attachments/assets/18f712a3-1c48-4770-9a04-03e89ff27bd7)
### -bsp.bb

![Screenshot from 2024-09-11 01-50-44](https://github.com/user-attachments/assets/057ec51d-646d-41ae-b465-97b02db55744)

### -myimage.bb

  ![Screenshot from 2024-09-11 02-02-07](https://github.com/user-attachments/assets/7a83473d-61bf-4182-a772-302604d786a9)


  and  after add the machine in local.conf in the building directorty we bitbake our image and starting our journey
     to configure it.

# 03-Configure the wifi and developing its service



# 04-Configure the bluetooth and connecting it with our speaker
  ## Step 1: Enable Bluetooth Service

   Ensure that the Bluetooth service starts automatically on boot by running the following command:

      ```bash
      systemctl enable bluetooth
      ```

# 05-Testing the Audoi in our image 


     
     



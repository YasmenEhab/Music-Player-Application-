# Directory Contents Description

### lircd.conf

This is the configuration file for the IR remote. It contains all the buttons and their signal frames. It must be located under `/etc/lircd`. 

- **Important note**: Do not modify this file, change its name, or move it from this path. Any of these actions will cause the IR remote to malfunction.

### keyboard-ir-(deprecated).service  
### keyboard-ir-binding-(deprecated).py

Initially, we implemented IR signal mapping with a Python script named `keyboard-ir-binding-(deprecated).py` and developed a service `keyboard-ir-(deprecated).service`to run it continuously as a daemon process at boot. However, this method is now deprecated, as we've shifted to a more efficient solution. These files are no longer needed for the project, but you can still review them for reference.

### RemoteControlMapper.java

This is the new implementation that replaces the previous Python-based method. We developed a Java class that implements `Runnable` to be run as a thread. It listens for IR signals and maps them to keyboard presses using the `Robot` class in Java. This method is more efficient since our application is already developed in Java. As a result, we don't need a separate service to run the code during every reboot or have it running continuously when the application isn't open. Additionally, there is no need for a daemon process—when the media player app starts, the thread will also start and terminate when finished.

- **Note**: You don’t need to take any action with this file or place it in a specific location. The code is already integrated into our media player application, so when you run the application, the IR functionality will work automatically.

### config.txt

This file contains the GPIO pin configuration for the IR setup. You must take the content of this file and append it to the existing `config.txt` file located in the `boot` directory of your image. **Do not delete the original content** in the `config.txt` file, as replacing it could lead to unexpected behavior. Instead, append the content to avoid any issues. To do this, navigate to the `boot` partition of your image and find the existing `config.txt` file, then append the content of our file to it.

---



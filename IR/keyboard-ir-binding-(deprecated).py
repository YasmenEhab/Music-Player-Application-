#!/usr/bin/python3
import subprocess
import keyboard
import time

# Define the command to run irw
command = ["irw"]

# Start irw as a subprocess
process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)

# Define dictionary to map remote keys to scripts
key_ir_map = {
    "KEY_PREVIOUS": "p",
    "KEY_FORWARD": "n",
    "KEY_PLAY": "o",
    "KEY_VOLUMEDOWN": "down",
    "KEY_VOLUMEUP": "up",
    "KEY_UNDO": "s",
    "KEY_7": "left",
    "KEY_9": "right",
    "KEY_STOP": "q",                               
    # Add more key-script mappings as needed
}

# Time window for debouncing (in seconds)
DEBOUNCE_TIME = 0.5

# Track the last time a key action was processed
last_action_time = {}

try:
    # Continuously read the output of irw
    for line in iter(process.stdout.readline, b''):
        line = line.decode("utf-8").strip()
        # Split the line into tokens
        tokens = line.split()
        if len(tokens) >= 3:
            key = tokens[-2]  # Extract the key from the third token from the end
            if key in key_ir_map:
                current_time = time.time()
                if key not in last_action_time or (current_time - last_action_time[key]) >= DEBOUNCE_TIME:
                    keyboard_key = key_ir_map[key]
                    keyboard.press_and_release(keyboard_key)
                    last_action_time[key] = current_time
        else:
            continue
except KeyboardInterrupt:
    # Handle KeyboardInterrupt
    print("KeyboardInterrupt: Terminating the process...")
finally:
    # Ensure the subprocess is terminated
    process.terminate()


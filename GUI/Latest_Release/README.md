Media Player Application
----
This is a JavaFX-based Media Player application that allows users to navigate through different scenes to select and play media files. The application is built with three main scenes: Start Scene, Intro Scene, and Media Player Scene. It provides an intuitive interface for users to choose and play their favorite songs or videos.

## Key Features
#### Three-Scene Navigation:

###### Start Scene:
The entry point of the application. From here, users can navigate to the Intro Scene.
###### Intro Scene:
In this scene, users can browse and select a specific song or video file to play. Once a selection is made, they can proceed to the Media Player Scene.
###### Media Player Scene:
The main interface where the selected song or video is displayed and played. Users have access to media controls such as play, pause, stop, skip, and volume adjustments.
#### Media Selection and Playback:

Users can select specific songs or videos in the Intro Scene. After selection, the application navigates to the Media Player Scene, where the chosen media is displayed and played.
User-Friendly Controls:

The Media Player Scene includes standard playback controls like play, pause, stop, skip forward, skip backward, and volume control for an enhanced user experience.
Seamless Scene Transitions:

The application ensures smooth transitions between the scenes, making it easy for users to navigate and interact with the media player.
Responsive Design:

The application adapts to different screen sizes and resolutions, ensuring an optimal viewing experience across various devices.

## MediaPlayerController
### Key Features
##### Media Management:
Supports .mp3 and .mp4 media files.
Loads media files from a specified directory into an ArrayList<File>.
Sorts media files alphabetically to maintain a consistent playback order.
###### Image Management:
Loads associated images from a specified directory for display during audio playback.
Sorts images alphabetically to align with media files.
###### Playback Controls:
Methods to play, pause, stop, skip forward, rewind, play next, and play previous media files.
Automatically loads and plays the next media file when the current one ends.
Adjusts media volume and provides a visual slider for volume control.
###### UI Components:
Uses JavaFX components such as MediaView, Slider, Label, ImageView, and StackPane.
Dynamically binds media and image view sizes to the window size to ensure responsive design.
###### Keyboard Shortcuts:
Supports keyboard controls for play (O), pause (Q), stop (S), next (N), previous (P), and volume adjustments (UP/DOWN).
###### Mouse and Touch Controls:
Implements MouseEvent and TouchEvent handlers for UI buttons to control playback and navigation.


import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

private class RemoteControlRunnable implements Runnable {

     private static final double DEBOUNCE_TIME = 500; // milliseconds
     private final Map<String, Long> lastActionTime = new HashMap<>();
     private final Map<String, Integer> keyIrMap = new HashMap<>();

     {
         // Define the key mappings
         keyIrMap.put("KEY_PREVIOUS", KeyEvent.VK_P);
         keyIrMap.put("KEY_FORWARD", KeyEvent.VK_N);
         keyIrMap.put("KEY_PLAY", KeyEvent.VK_O);
         keyIrMap.put("KEY_VOLUMEDOWN", KeyEvent.VK_DOWN);
         keyIrMap.put("KEY_VOLUMEUP", KeyEvent.VK_UP);
         keyIrMap.put("KEY_UNDO", KeyEvent.VK_S);
         keyIrMap.put("KEY_7", KeyEvent.VK_LEFT);
         keyIrMap.put("KEY_9", KeyEvent.VK_RIGHT);
         keyIrMap.put("KEY_STOP", KeyEvent.VK_Q);
         keyIrMap.put("KEY_1", KeyEvent.VK_1);
         keyIrMap.put("KEY_2", KeyEvent.VK_2);
         keyIrMap.put("KEY_3", KeyEvent.VK_3);
         keyIrMap.put("KEY_4", KeyEvent.VK_4);
     }

     @Override
     public void run() {
         ProcessBuilder processBuilder = new ProcessBuilder("irw");
         Process process;
         try {
             process = processBuilder.start();
         } catch (Exception e) {
             e.printStackTrace();
             return;
         }

         BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
         Robot robot;
         try {
             robot = new Robot();
         } catch (Exception e) {
             e.printStackTrace();
             return;
         }

         String line;
         try {
             while ((line = reader.readLine()) != null) {
                 if (Thread.currentThread().isInterrupted()) {
                     break; // Exit if interrupted
                 }
                 line = line.trim();
                 String[] tokens = line.split("\\s+");
                 if (tokens.length >= 3) {
                     String key = tokens[tokens.length - 2];
                     if (keyIrMap.containsKey(key)) {
                         long currentTime = System.currentTimeMillis();
                         Long lastTime = lastActionTime.get(key);
                         if (lastTime == null || (currentTime - lastTime) >= DEBOUNCE_TIME) {
                             int keyCode = keyIrMap.get(key);
                             robot.keyPress(keyCode);
                             robot.keyRelease(keyCode);
                             lastActionTime.put(key, currentTime);
                         }
                     }
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             process.destroy();
         }
     }
 }




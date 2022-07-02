import sun.audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;

public class KeyUtil {
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void pressKongGe(){
        robot.keyPress(KeyEvent.VK_SPACE);
    }

}

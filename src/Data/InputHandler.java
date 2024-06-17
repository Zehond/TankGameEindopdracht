package Data;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class InputHandler {

    private Set<Integer> pressedKeys = new HashSet<>();

    public InputHandler() {

    }

//    @Override
//    public void keyTyped(KeyEvent e) {
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//       pressedKeys.add(e.getKeyCode());
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        pressedKeys.remove(e.getKeyCode());
//    }

    public boolean isPressed(javafx.scene.input.KeyCode keyCode) {
        return pressedKeys.contains(keyCode.ordinal());
    }

    public void keyPressed(javafx.scene.input.KeyEvent e) {
        pressedKeys.add(e.getCode().ordinal());
    }

    public void keyReleased(javafx.scene.input.KeyEvent e) {
        pressedKeys.remove(e.getCode().ordinal());
    }
}

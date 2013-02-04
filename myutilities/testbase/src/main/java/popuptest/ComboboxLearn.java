package popuptest;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * 
 * @author Moon Zang
 * 
 */

public class ComboboxLearn {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.addAWTEventListener(new AWTEventListener() {

            @Override
            public void eventDispatched(AWTEvent event) {
                // TODO Auto-generated method stub
                System.out.println("asdfasdf");
                if (!(event instanceof MouseEvent)) {
                    return;
                }
                MouseEvent me = (MouseEvent) event;
                Component src = me.getComponent();
                switch (me.getID()) {
                case MouseEvent.MOUSE_PRESSED:

                    break;
                }
            }
        }, AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        JComboBox box = new JComboBox(
                new Object[] { "asdf", "asdfasdf", "asdf" });

        box.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                // TODO Auto-generated method stub
                System.out.println();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // TODO Auto-generated method stub
                System.out.println();
            }

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                // TODO Auto-generated method stub
                System.out.println();
            }
        });

        JFrame f = new JFrame();
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(box);
        f.getContentPane().add(new JLabel("asdfasdfasdf"));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(new Dimension(100, 100));

        f.setVisible(true);
    }
}

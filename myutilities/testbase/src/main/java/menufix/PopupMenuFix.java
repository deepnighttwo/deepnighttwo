package menufix;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.MenuSelectionManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicPopupMenuUI;

/**
 * This class fixes a bug in Java 6 that causes all popup menus do not stay
 * open. This occurs on {@link JMenu} and {@link JPopupMenu}, and
 * {@link JComboBox} components. It is caused by a sun.awt.UngrabEvent being
 * fired as soon as the popup menu appears and then immediately gets hidden. The
 * class grabber class is called MouseGrabber and is inside the
 * {@link BasicPopupMenuUI} class.
 * 
 * @see BasicPopupMenuUI
 * @author Chris Callendar
 * @date 4-May-07
 */
public class PopupMenuFix implements ChangeListener {

    private static PopupMenuFix singleton;
    private static HashSet<ChangeListener> grabbers;

    private PopupMenuFix() {
        grabbers = new HashSet<ChangeListener>();

        // @tag Creole.Java6.MouseGrabber : add our menu change listener
        MenuSelectionManager mgr = MenuSelectionManager.defaultManager();
        mgr.addChangeListener(this);
    }

    public static PopupMenuFix getGrabber() {
        if (singleton == null) {
            singleton = new PopupMenuFix();
        }
        return singleton;
    }

    /**
     * In Java 6 the {@link BasicPopupMenuUI} adds the MouseGrabber class as a
     * {@link ChangeListener} for when menus are selected. This listener then
     * adds itself as an AWTEventListener which causes the popup menu to
     * immediately be hidden because an sun.awt.UngrabEvent gets fired. This is
     * a bug in Java 6. The workaround/hack is to remove the MouseGrabber as an
     * {@link AWTEventListener} and add it back with a different mask that
     * doesn't listen for Grab events.
     * 
     * @tag Creole.Java6.MouseGrabber
     */
    public static void removeMouseGrabber() {
        String version = System
                .getProperty("java.specification.version", "1.5");
        if ("1.6".equals(version)) {
            MenuSelectionManager mgr = MenuSelectionManager.defaultManager();
            ChangeListener[] listeners = mgr.getChangeListeners();
            grabbers = new HashSet<ChangeListener>();
            for (int i = 0; i < listeners.length; i++) {
                ChangeListener cl = listeners[i];
                String name = cl.getClass().getName();
                if (name.indexOf("MouseGrabber") != -1) {
                    if (cl instanceof AWTEventListener) {
                        grabbers.add(cl);
                    }
                    // make sure it is the last one in the list
                    // when the MenuSelectionManager.fireStateChanged() method
                    // runs
                    // it calls the listeners in reverse order, LAST first
                    mgr.removeChangeListener(cl);
                    mgr.addChangeListener(cl);
                }
            }
        }
    }

    /**
     * This method removes the MouseGrabber as an {@link AWTEventListener} and
     * then adds it back without the sun.awt.SunToolkit.GRAB_EVENT_MASK. This
     * code is copied from the MouseGrabber.ungrabWindow() and grabWindow()
     * methods.
     */
    public void stateChanged(ChangeEvent e) {
        final Toolkit tk = Toolkit.getDefaultToolkit();
        java.security.AccessController
                .doPrivileged(new java.security.PrivilegedAction<Object>() {
                    public Object run() {
                        for (Iterator<ChangeListener> iter = grabbers.iterator(); iter
                                .hasNext();) {
                            AWTEventListener al = (AWTEventListener) iter
                                    .next();
                            tk.removeAWTEventListener(al);
                            // removed the sun.awt.SunToolkit.GRAB_EVENT_MASK !
                            tk.addAWTEventListener(al,
                                    AWTEvent.MOUSE_EVENT_MASK
                                            | AWTEvent.MOUSE_MOTION_EVENT_MASK
                                            | AWTEvent.MOUSE_WHEEL_EVENT_MASK
                                            | AWTEvent.WINDOW_EVENT_MASK);
                        }
                        return null;
                    }
                });
    }
}

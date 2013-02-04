package popuptest;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

/**
 * @author Adrian BER (beradrian@yahoo.com)
 */
public class TestPopupWindow extends JPanel {

    private JToggleButton invokePopupButton;
    private JFrame popupWindow;

    /**
     * Constructor.
     */
    public TestPopupWindow() {
        init();
    }

    /**
     * Initialiser.
     */
    private void init() {

        invokePopupButton = new JToggleButton("Show popup");
        invokePopupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // set popup window visibility
                if (!popupWindow.isVisible()) {
                    // set location relative to button
                    Point location = invokePopupButton.getLocation();
                    SwingUtilities.convertPointToScreen(location,
                            invokePopupButton.getParent());
                    location
                            .translate(
                                    0,
                                    invokePopupButton.getHeight()
                                            + (invokePopupButton.getBorder() == null ? 0
                                                    : invokePopupButton
                                                            .getBorder()
                                                            .getBorderInsets(
                                                                    invokePopupButton).bottom));
                    popupWindow.setLocation(location);

                    // show the popup if not visible
                    invokePopupButton.setText("Hide popup");
                    popupWindow.setVisible(true);
                    popupWindow.requestFocus();
                } else {
                    // hide it otherwise
                    invokePopupButton.setText("Show popup");
                    popupWindow.setVisible(false);
                }
            }
        });

        // add components to main panel
        this.setLayout(new BorderLayout());
        this.add(invokePopupButton, BorderLayout.CENTER);

        // use frame
        popupWindow = new JFrame();
        popupWindow.setUndecorated(true);

        popupWindow.addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {

            }

            public void windowLostFocus(WindowEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (popupWindow.isVisible())
                            invokePopupButton.doClick();
                    }
                });
            }
        });

        // add some components to window
        popupWindow.getContentPane().setLayout(new BorderLayout());
        ((JComponent) popupWindow.getContentPane()).setBorder(BorderFactory
                .createEtchedBorder());
        JTextField aTextField = new JTextField(10);
        popupWindow.getContentPane()
                .add(new JLabel("Text:"), BorderLayout.WEST);
        popupWindow.getContentPane().add(aTextField);
        popupWindow.pack();
    }

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("TestPopupWindow");
        mainFrame.getContentPane().add(new TestPopupWindow());
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}

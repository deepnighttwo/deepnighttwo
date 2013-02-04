package popuptest;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Popup;
import javax.swing.PopupFactory;

public class ButtonPopupSample {

    // Define Show Popup ActionListener
    static class ShowPopupActionListener implements ActionListener {
        private Component component;

        ShowPopupActionListener(Component component) {
            this.component = component;
        }

        public synchronized void actionPerformed(ActionEvent actionEvent) {
            JButton button = new JButton("Hello, World");
            button.setPreferredSize(new Dimension(200, 200));
            PopupFactory factory = PopupFactory.getSharedInstance();
            Random random = new Random();
            int x = random.nextInt(200);
            int y = random.nextInt(200);
            final Popup popup = factory.getPopup(component, button, x, y);
            popup.show();
            button.requestFocus();
            button.addFocusListener(new FocusListener() {

                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void focusLost(FocusEvent e) {
                    popup.hide();

                }

            });
            // ActionListener hider = new ActionListener() {
            // public void actionPerformed(ActionEvent e) {
            // popup.hide();
            // }
            // };
            // Hide popup in 3 seconds
            // Timer timer = new Timer(3000, hider);
            // timer.start();
        }
    }

    public static void main(final String args[]) {
        JFrame frame = new JFrame("Button Popup Sample");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener actionListener = new ShowPopupActionListener(frame);

        JButton start = new JButton("Pick Me for Popup");
        start.addActionListener(actionListener);
        frame.add(start);
        frame.setSize(350, 250);
        frame.setVisible(true);
    }
}
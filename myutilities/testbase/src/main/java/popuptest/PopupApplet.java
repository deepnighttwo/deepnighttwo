package popuptest;

import javax.swing.JApplet;

public class PopupApplet extends JApplet {

    @Override
    public void init() {
        TestPopupWindow w = new TestPopupWindow();
        this.getContentPane().add(w);
        super.init();
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        super.start();
    }

}

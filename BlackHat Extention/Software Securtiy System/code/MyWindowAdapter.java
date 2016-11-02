// Source File Name:   Sss.java

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

class MyWindowAdapter extends WindowAdapter
{

    public MyWindowAdapter(JFrame jframe)
    {
        frame = jframe;
    }

    public void windowClosing(WindowEvent windowevent)
    {
        frame.setVisible(false);
    }

    JFrame frame;
}
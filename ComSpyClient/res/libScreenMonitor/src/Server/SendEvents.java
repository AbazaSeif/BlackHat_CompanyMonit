/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JPanel;

/**
 *
 * @author abaza
 */
class SendEvents implements KeyListener, MouseMotionListener, MouseListener  {

    private Socket cSocket = null;
    private JPanel cPanel = null;
    private PrintWriter writer = null;
    String width = "", height = "";
    double w;
    double h;

    public SendEvents(Socket cSocket, JPanel Panel, String width, String height) {
        this.cSocket = cSocket;
        this.cPanel = Panel;
        this.width = width;
        this.height = height;
        w = Double.valueOf(width);
        h = Double.valueOf(height);

        this.cPanel.addKeyListener(this);
        this.cPanel.addMouseListener(this);
        this.cPanel.addMouseMotionListener(this);

        try {
            writer = new PrintWriter(this.cSocket.getOutputStream());
        } catch (IOException ex) {
        }
        this.cPanel.updateUI();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        writer.println(Commands.KEY_PRICING.getAbbrev());
        writer.println(e.getKeyCode());
        writer.flush();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double xScale = (double) w / cPanel.getWidth();
        double yScale = (double) h / cPanel.getHeight();
        writer.println(Commands.MOVE_MOUSE.getAbbrev());
        writer.println((int) (e.getX() * xScale));
        writer.println((int) (e.getY() * yScale));
        writer.flush();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        writer.println(Commands.PRESS_MOUSE.getAbbrev());
        int button = e.getButton();
        int xButton = 16;
        if (button == 3) {
            xButton = 4;
        }
        writer.println(xButton);
        writer.flush();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        writer.println(Commands.RELEASE_MOUSE.getAbbrev());
        int button = e.getButton();
        int xButton = 16;
        if (button == 3) {
            xButton = 4;
        }
        writer.println(xButton);
        writer.flush();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}

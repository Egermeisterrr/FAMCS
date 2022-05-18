package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MyRunnable());
    }

    public Main() {
        setBounds(150, 150, WIDTH, HEIGHT);
        setTitle("Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel cont = new JPanel();
        JPanel additionalPanel = new JPanel();
        JPanel additionalPanel1 = new JPanel();
        Color c1 = new Color(0x101EE3); // штука с поиском
        Color c2 = new Color(0x006AFF); // левый поток
        Color c3 = new Color(0x66ACF3); // правый поток
        CommonPanel cp = new CommonPanel(c1);

        EmptyBorder contentBorder = new EmptyBorder(7, 7, 7, 7);
        BoxLayout contentLayout = new BoxLayout(cont, BoxLayout.X_AXIS);
        BoxLayout additionalLayout = new BoxLayout(additionalPanel, BoxLayout.X_AXIS);
        BoxLayout additionalLayout1 = new BoxLayout(additionalPanel1, BoxLayout.X_AXIS);
        cont.setBorder(contentBorder);
        cont.setLayout(contentLayout);

        additionalPanel.setBorder(contentBorder);
        additionalPanel.setLayout(additionalLayout);
        additionalPanel.add(new SearchPanel(1, c2, cp));

        additionalPanel1.setBorder(contentBorder);
        additionalPanel1.setLayout(additionalLayout1);
        additionalPanel1.add(new SearchPanel(2, c3, cp));

        cont.add(additionalPanel);
        cont.add(cp);
        cont.add(additionalPanel1);

        setContentPane(cont);
        pack();
        setResizable(false);
    }

    public static class MyRunnable implements Runnable {
        public void run() {
            try {
                Main frame = new Main();
                frame.setVisible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
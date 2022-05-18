package com.company;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class CommonPanel extends JPanel { // логика фигни с белым фоном
    private JTextArea text;
    private ArrayList<String> res;
    private Object sync = new Object();
    private static final int X = 540, Y = 600;
    private static final long serialVersionUID = 1L;
    private byte userCounter = 0;

    public CommonPanel(Color back) {
        setSize(X, Y);
        EmptyBorder commonBorder = new EmptyBorder(7, 7, 7, 7);
        setBorder(commonBorder);
        setBackground(back);
        setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 50, 450, 500);
        add(scrollPane);
        text = new JTextArea();
        Color c = new Color(0xFFFFFFFF);
        text.setBackground(c);
        text.setDisabledTextColor(Color.black);
        Font textFont = new Font("Monospaced", Font.BOLD, 10);
        text.setFont(textFont);
        text.setEditable(false);
        text.setBounds(0, 0, 4, 16);

        scrollPane.setViewportView(text);
    }

    public Dimension getPreferredSize() {
        return new Dimension(X, Y);
    }

    private synchronized void newList() {
        if(userCounter == 0)
            res = new ArrayList<>();
    }

    public synchronized void addString(String s) {
        res.add(s);
    }

    public synchronized void delText() {
        if(userCounter == 0)
            text.setText("");
    }

    public void add() {
        newList();
        synchronized(sync) {
            userCounter++;
        }
    }

    public void remove() {
        synchronized(sync) {
            userCounter--;
        }
    }

    public synchronized void print() {
        text.setText("");
        for(int i = 0; i < res.size(); i++)
            text.append(res.get(i));
    }
}
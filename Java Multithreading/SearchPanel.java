package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPanel extends JPanel { // кнопки

    private static final int CX = 520, CY = 260;

    private JTextField templateTextField;
    private JButton directoryButton;

    private String directory;
    private JTextField stringSearchTextField;

    private JCheckBox templateCheckBox;
    private JCheckBox stringSearchCheckBox;
    private JCheckBox directoryCheckBox;
    private JComboBox<String> comboBox;

    private Timer timer;
    JButton btnStart;
    SearchTread threaddd = null;
    private int number;
    private CommonPanel common;

    public SearchPanel(int number, Color back, CommonPanel cp) {
        this.number = number;
        common = cp;
        setSize(CX, CY);
        EmptyBorder eb = new EmptyBorder(5, 5, 5, 5);
        setBorder(eb);
        setBackground(back);
        setLayout(null);
        createButtons();
        createLabels();
        createTextFields();
        createCheckBoxes();
        comboBox = new JComboBox<>();
        String[] numbers = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        DefaultComboBoxModel cb = new DefaultComboBoxModel<>(numbers);
        comboBox.setModel(cb);
        comboBox.setSelectedIndex(0);
        comboBox.setMaximumRowCount(10);
        comboBox.setBounds(270, 87, 43, 20);
        add(comboBox);

        timer = new Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(threaddd != null) {
                    common.print();
                    if(!threaddd.isAlive()) {
                        threaddd.myInterrupt();
                        threaddd = null;
                        timer.stop();
                        setFieldsEnabled(true);
                        btnStart.setEnabled(true);
                        lastPrBtn = LastPressedButton.Stop;
                    }
                }
            }
        });
    }

    public Dimension getPreferredSize() {
        return new Dimension(CX, CY);
    }

    private void setFieldsEnabled(boolean enabled) {
        directoryButton.setEnabled(enabled);
        stringSearchCheckBox.setEnabled(enabled);
        templateCheckBox.setEnabled(enabled);

        if(stringSearchCheckBox.isSelected())
            stringSearchTextField.setEnabled(enabled);
        if(templateCheckBox.isSelected())
            templateTextField.setEnabled(enabled);
        directoryCheckBox.setEnabled(enabled);
        comboBox.setEnabled(enabled);
        if(enabled)
            common.remove();
    }

    private void createButtons() {
        btnStart = new JButton("Start");
        Color l = new Color(255, 239, 213);
        Color g = new Color(0, 255, 117);
        btnStart.setForeground(l);
        btnStart.setBackground(g);
        btnStart.setBounds(421, 11, 80, 50);
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnStart.setEnabled(false);
                if(lastPrBtn != LastPressedButton.Start) {
                    lastPrBtn = LastPressedButton.Start;
                    if(threaddd == null) {
                        common.delText();
                        try {
                            String regX = (templateCheckBox.isSelected()) ? templateTextField.getText() : null;
                            String searchStr = (stringSearchCheckBox.isSelected()) ? stringSearchTextField.getText() : null;
                            threaddd = new SearchTread(number, regX, directory, searchStr, directoryCheckBox.isSelected(), comboBox.getSelectedIndex() + 1, common);
                        }
                        catch(Exception except) {
                            JOptionPane.showMessageDialog(null, except.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                            btnStart.setEnabled(true);
                            lastPrBtn = LastPressedButton.Stop;
                            return;
                        }
                        setFieldsEnabled(false);
                        threaddd.start();
                    }
                    else threaddd.myResume();
                    timer.start();
                }
            }
        });
        add(btnStart);
        JButton btnStop = new JButton("Stop");
        Color p = new Color(255, 240, 245);
        btnStop.setForeground(p);
        Color r =  new Color(253, 0, 0);
        btnStop.setBackground(r);
        btnStop.setBounds(421, 72, 80, 50);
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(lastPrBtn != LastPressedButton.Stop) {
                    if(threaddd != null) {
                        threaddd.myInterrupt();
                        threaddd = null;
                    }
                    timer.stop();
                    setFieldsEnabled(true);
                    btnStart.setEnabled(true);
                }
                lastPrBtn = LastPressedButton.Stop;
            }
        });
        add(btnStop);
        JButton btnPause = new JButton("Pause");
        Color w = new Color(255, 245, 238);
        Color j = new Color(252, 228, 6);
        btnPause.setForeground(w);
        btnPause.setBackground(j);
        btnPause.setBounds(421, 133, 80, 50);

        btnPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(lastPrBtn != LastPressedButton.Pause) {
                    if(threaddd != null)
                        threaddd.mySuspend();
                    btnStart.setEnabled(true);
                }
                lastPrBtn = LastPressedButton.Pause;
                timer.stop();
            }
        });

        add(btnPause);
        directoryButton = new JButton("Выбрать");
        directoryButton.setBounds(151, 33, 162, 20);
        directoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    directory = chooser.getSelectedFile().getAbsolutePath();
                    directoryButton.setText(directory);
                }
            }
        });
        add(directoryButton);
    }

    private void createLabels() {
        JLabel label_1 = new JLabel("\u0414\u0438\u0440\u0435\u043A\u0442\u043E\u0440\u0438\u044F \u043D\u0430\u0447\u0430\u043B\u0430:");
        label_1.setBounds(10, 36, 132, 14);
        add(label_1);
        JLabel label_2 = new JLabel("\u041F\u0440\u0438\u043E\u0440\u0438\u0442\u0435\u0442:");
        label_2.setBounds(201, 90, 80, 14);
        add(label_2);
    }

    private void createTextFields() {
        templateTextField = new JTextField();
        templateTextField.setBounds(151, 8, 162, 20);
        add(templateTextField);
        templateTextField.setColumns(10);
        templateTextField.setEnabled(false);
        stringSearchTextField = new JTextField();
        stringSearchTextField.setBounds(151, 58, 162, 20);
        add(stringSearchTextField);
        stringSearchTextField.setColumns(10);
        stringSearchTextField.setEnabled(false);
    }

    private void createCheckBoxes() {
        templateCheckBox = new JCheckBox("\u0428\u0430\u0431\u043B\u043E\u043D:");
        templateCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                templateTextField.setEnabled(templateCheckBox.isSelected());
            }
        });
        templateCheckBox.setBounds(10, 11, 132, 24);
        templateCheckBox.setBackground(getBackground());
        add(templateCheckBox);
        stringSearchCheckBox = new JCheckBox("\u043F\u043E\u0438\u0441\u043A \u0441\u0442\u0440\u043E\u043A\u0438:");

        ActionListener al =  new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stringSearchTextField.setEnabled(stringSearchCheckBox.isSelected());
            }
        };

        stringSearchCheckBox.addActionListener(al);

        stringSearchCheckBox.setBounds(10, 57, 132, 24);
        stringSearchCheckBox.setBackground(getBackground());
        add(stringSearchCheckBox);
        directoryCheckBox = new JCheckBox("\u043F\u043E\u0438\u0441\u043A \u0432 \u043F\u043E\u0434\u0434\u0438\u0440\u0435\u043A\u0442\u043E\u0440\u0438\u044F\u0445");
        directoryCheckBox.setBounds(10, 86, 170, 24);
        directoryCheckBox.setBackground(getBackground());
        add(directoryCheckBox);
    }

    private enum LastPressedButton {Start, Pause, Stop}

    private LastPressedButton lastPrBtn = null;
}
package com.diploma.classdiagram.gui;

import com.diploma.gui.DiplomaGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

/**
 * User: d.ulanovych
 * Date: 1/23/14
 */
public class UMLTesterGUI {
    private JButton classVerifierButton;
    private JButton sequenceVerifierButton;
    private JPanel umlTesterPanel;

    public JPanel getUmlTesterPanel() {
        return umlTesterPanel;
    }

    public UMLTesterGUI() {
        sequenceVerifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DiplomaGUI diplomaGUI = new DiplomaGUI();
                            Field frameField = diplomaGUI.getClass().getDeclaredField("frmSequenceDiagrammVerifier");
                            frameField.setAccessible(true);
                            JFrame sequence = (JFrame) frameField.get(diplomaGUI);
                            sequence.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            sequence.setVisible(true);
                        } catch (NoSuchFieldException e1) {
                            e1.printStackTrace();
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        }
                    }
                }.run();
            }
        });
        classVerifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Runnable() {
                    @Override
                    public void run() {
                        JFrame frame = new JFrame("Class Diagram Verifier");
                        frame.setContentPane(new ClassTester().getClassTesterPanel());
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                    }
                }.run();

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UML Tester");
        UMLTesterGUI umlTesterGUI = new UMLTesterGUI();
        frame.setContentPane(umlTesterGUI.getUmlTesterPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

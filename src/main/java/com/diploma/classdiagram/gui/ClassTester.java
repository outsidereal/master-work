package com.diploma.classdiagram.gui;

import com.diploma.classdiagram.parser.ClassParser;
import com.diploma.classdiagram.parser.UMLParserException;
import com.diploma.classdiagram.verification.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * User: d.ulanovych
 * Date: 11/16/13
 */
public class ClassTester {
    private JPanel classTesterPanel;
    private JRadioButton graphRadioButton;
    private JRadioButton testDriverRadioButton;
    private JRadioButton setRadioButton;
    private JRadioButton patternsRadioButton;
    private JRadioButton complexRadioButton;
    private JButton verifyButton;
    private JButton fileSelector;
    private JTextArea log;
    private File currentDiagram;
    final JFileChooser fileChooser = new JFileChooser();

    public JPanel getClassTesterPanel() {
        return classTesterPanel;
    }

    public ClassTester() {
        testDriverRadioButton.setSelected(true);
        Verification.LOGGER.addAppender(new GuiAppender(log));
        testDriverRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testDriverRadioButton.setSelected(true);
                graphRadioButton.setSelected(false);
                setRadioButton.setSelected(false);
                patternsRadioButton.setSelected(false);
                complexRadioButton.setSelected(false);
            }
        });
        graphRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphRadioButton.setSelected(true);
                testDriverRadioButton.setSelected(false);
                setRadioButton.setSelected(false);
                patternsRadioButton.setSelected(false);
                complexRadioButton.setSelected(false);
            }
        });
        setRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRadioButton.setSelected(true);
                graphRadioButton.setSelected(false);
                testDriverRadioButton.setSelected(false);
                patternsRadioButton.setSelected(false);
                complexRadioButton.setSelected(false);
            }
        });
        patternsRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patternsRadioButton.setSelected(true);
                graphRadioButton.setSelected(false);
                setRadioButton.setSelected(false);
                testDriverRadioButton.setSelected(false);
                complexRadioButton.setSelected(false);
            }
        });
        complexRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                complexRadioButton.setSelected(true);
                graphRadioButton.setSelected(false);
                setRadioButton.setSelected(false);
                patternsRadioButton.setSelected(false);
                testDriverRadioButton.setSelected(false);
            }
        });
        fileSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setCurrentDirectory(currentDiagram);
                int returnVal = fileChooser.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    currentDiagram = fileChooser.getSelectedFile();
                    log.append("Selected diagram :"+currentDiagram.getAbsolutePath() + "\n\n");
                }
            }
        });
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verify();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UML Tester");
        frame.setContentPane(new ClassTester().classTesterPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void verify() {
        if (null != currentDiagram) {
            ClassParser parser = new ClassParser();
            Verification verification = null;
            try {
                parser.parse(currentDiagram.getAbsolutePath());
            } catch (UMLParserException e) {
                for (StackTraceElement element : e.getStackTrace()) {
                    log.append(element.toString());
                    log.append("\n");
                }
                return;
            }
            if (testDriverRadioButton.isSelected()) {
                verification = new DriverVerification(parser.getParsedClasses(), parser.getParsedRelationships());
            } else if (setRadioButton.isSelected()) {
                verification = new SetVerification();
            } else if (patternsRadioButton.isSelected()) {
                verification = new PatternVerification(parser.getParsedClasses(), parser.getParsedRelationships());
            } else if (graphRadioButton.isSelected()) {
                verification = new GraphVerification(parser.getParsedClasses(), parser.getParsedRelationships());
            }
            if (null != verification) {
                verification.verify();
            } else if (complexRadioButton.isSelected()) {
                verification = new DriverVerification(parser.getParsedClasses(), parser.getParsedRelationships());
                verification.verify();
                verification = new SetVerification();
                verification.verify();
                verification = new PatternVerification(parser.getParsedClasses(), parser.getParsedRelationships());
                verification.verify();
                verification = new GraphVerification(parser.getParsedClasses(), parser.getParsedRelationships());
                verification.verify();
            }
        }
    }
}

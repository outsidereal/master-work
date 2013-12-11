package com.diploma.classdiagram.gui;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import javax.swing.*;

/**
 * Date: 11.12.13
 * Time: 10:04
 */
public class GuiAppender extends AppenderSkeleton {
    private JTextArea log;

    public GuiAppender(JTextArea log){
        this.log = log;
    }
    @Override
    protected void append(LoggingEvent event) {
        log.append(event.getMessage().toString());
        log.append("\n");
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}

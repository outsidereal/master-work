package com.diploma.sequencediagram;

import com.diploma.global.XMLElement;

/**
 * Created with IntelliJ IDEA.
 * User: ksu
 * Date: 24.02.13
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public class Fragment extends XMLElement {
    private String message;
    private String covered;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    private int sequence;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCovered() {
        return covered;
    }

    public void setCovered(String covered) {
        this.covered = covered;
    }
}

package com.diploma.sequencediagram;

import com.diploma.global.XMLElement;

/**
 * Created with IntelliJ IDEA.
 * User: ksu
 * Date: 10.09.12
 * Time: 21:32
 */
public class Message extends XMLElement {

    private String receivedEvent;
    private Boolean parsed;
    private String clazz;
    private String messageSort;

    public void setReceivedEvent(String receivedEvent) {
        this.receivedEvent = receivedEvent;
    }

    public String getReceivedEvent() {
        return this.receivedEvent;
    }

    public void setParsed(Boolean parsed) {
        this.parsed = parsed;
    }

    public Boolean getParsed() {
        return this.parsed;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return this.clazz;
    }

    public String getMessageSort() {
        return messageSort;
    }

    public void setMessageSort(String messageSort) {
        this.messageSort = messageSort;
    }
}

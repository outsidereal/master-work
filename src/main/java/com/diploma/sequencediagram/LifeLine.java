package com.diploma.sequencediagram;

import com.diploma.global.XMLElement;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ksu
 * Date: 10.09.12
 * Time: 21:32
 */
public class LifeLine extends XMLElement {

    private String lifeClass;
    private String coveredBy;
    private List<Message> messages;

    public void setLifeClass(String lifeClass) {
        this.lifeClass = lifeClass;
    }

    public String getLifeClass() {
        return this.lifeClass;
    }

    public void setCoveredBy(String coveredBy) {
        this.coveredBy = coveredBy;
    }

    public String getCoveredBy() {
        return this.coveredBy;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

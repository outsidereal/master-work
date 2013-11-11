package com.diploma.parser;

import com.diploma.global.Constants;
import com.diploma.global.IElement;
import com.diploma.sequencediagram.Fragment;
import com.diploma.sequencediagram.LifeLine;
import com.diploma.sequencediagram.Message;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ksu
 * Date: 08.09.12
 * Time: 1:22
 * To change this template use File | Settings | File Templates.
 */

/**
 * Created with IntelliJ IDEA.
 * User: ksu
 * Date: 08.09.12
 * Time: 14:53
 */
public class SequenceDiagramParser implements IParser {

    private List<LifeLine> lifeLineList;
    private List<Message> messageList;
    private List<Fragment> fragmentList;

    private void setLifeLineList(List<LifeLine> lifeLineList) {
        this.lifeLineList = lifeLineList;
    }

    public List<LifeLine> getLifeLineList() {
        return this.lifeLineList;
    }

    private void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public List<Message> getMessageList() {
        return this.messageList;
    }

    public String getClassByMessage(Message message, List<LifeLine> lifeLineList) {
        String[] coveredBy = null;
        for (LifeLine lifeLine : lifeLineList) {
            coveredBy = lifeLine.getCoveredBy().split(" ");
            for (String cover : coveredBy) {
                if (cover.equals(message.getReceivedEvent()))
                    return lifeLine.getLifeClass();
            }
        }
        return null;
    }

    public LifeLine getLifeLineByMessage(Message message, List<LifeLine> lifeLineList) {
        String[] coveredBy = null;
        for (LifeLine lifeLine : lifeLineList) {
            coveredBy = lifeLine.getCoveredBy().split(" ");
            for (String cover : coveredBy) {
                if (cover.equals(message.getReceivedEvent()))
                    return lifeLine;
            }
        }
        return null;

    }
    public List<IElement> parse(String sourceFile) {
        Document document = getDocument(sourceFile);
        if (document == null) return null;

        // Getting the root element of xml file
        Element root = document.getRootElement();
        Element packagedElement = root.element(Constants.PACKAGED_ELEMENT);
        lifeLineList = new ArrayList<LifeLine>();
        messageList = new ArrayList<Message>();
        fragmentList = new ArrayList<Fragment>();
        //  List<IRelationship> relationships = new ArrayList<IRelationship>();

        // iterate through child elements of root with element name "packagedElement"
        for (Iterator iterator = packagedElement.elementIterator(); iterator.hasNext(); ) {
            // getting current packaged element
            Element subElement = (Element) iterator.next();
            //getting attribute "type" (it could be also "xmi:type" or something like that)
            Attribute type = subElement.attribute(Constants.TYPE);
            //deciding what kind of element it is.
            if (subElement.getName().equals(Constants.LIFE_LINE)) {
                System.out.println("this is a lifeline");
                LifeLine lifeLine = parseLifeLine(subElement);
                lifeLineList.add(lifeLine);
            }
            if (subElement.getName().equals(Constants.MESSAGE)) {
                System.out.println("this is a message");
                Message message = parseMessage(subElement);
                messageList.add(message);
            }
            if (subElement.getName().equals(Constants.FRAGMENT)) {
                System.out.println("this is a fragment");
                Fragment fragment = parseFragment(subElement);
                fragmentList.add(fragment);
            }

        }
        return null;
    }

    private Fragment parseFragment(Element fragment) {
        Fragment newFragment = new Fragment();

        newFragment.setName(fragment.attribute(Constants.NAME).getValue());
        newFragment.setMessage(fragment.attribute(Constants.MESSAGE).getValue());


        return newFragment;
    }
    private LifeLine parseLifeLine(Element lifeLine) {

        LifeLine newLifeLine = new LifeLine();
        // set the name and identifier of element
        newLifeLine.setName(lifeLine.attribute(Constants.NAME).getValue());
        System.out.println("Name: " + newLifeLine.getName().toString());

        newLifeLine.setLifeClass(parseClass(newLifeLine.getName()));
        System.out.println("Class: " + newLifeLine.getLifeClass().toString());

        newLifeLine.setCoveredBy(lifeLine.attribute(Constants.COVERED_BY).getValue());
        System.out.println("Covered by: " + newLifeLine.getCoveredBy().toString());

        newLifeLine.setId(lifeLine.attribute(Constants.ID).getValue());
        System.out.println("Id: " + newLifeLine.getId().toString());

        return newLifeLine;
    }

    private Message parseMessage(Element message) {

        Message newMessage = new Message();
        // set the name and identifier of element
        newMessage.setName(message.attribute(Constants.NAME).getValue());
        System.out.println("Name: " + newMessage.getName().toString());

        newMessage.setReceivedEvent(message.attribute(Constants.RECEIVE_EVENT).getValue());
        System.out.println("Receive event: " + newMessage.getReceivedEvent().toString());

        newMessage.setId(message.attribute(Constants.ID).getValue());
        System.out.println("Id: " + newMessage.getId().toString());

        return newMessage;
    }

    private String parseClass(String name) {
        String[] nameStr = name.split(":");
        return nameStr[1];
    }

    private Document getDocument(String filePath) {
        SAXReader reader = new SAXReader();

        Document document = null;

        try {
            document = reader.read(filePath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }
}

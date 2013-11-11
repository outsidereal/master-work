package com.diploma.verification.sequencediagram;

import com.diploma.classdiagram.Class;
import com.diploma.classdiagram.Method;
import com.diploma.parser.ClassParser;
import com.diploma.parser.SequenceDiagramParser;
import com.diploma.sequencediagram.Fragment;
import com.diploma.sequencediagram.LifeLine;
import com.diploma.sequencediagram.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ksu
 * Date: 10.09.12
 * Time: 21:53
 */
public class SequenceDiagramVerification {

    private boolean verified;

    Map<String, List<Method>> methods = new HashMap<String, List<Method>>();
    Map<LifeLine, List<Message>> messages = new HashMap<LifeLine, List<Message>>();

    public void setVerified(Boolean verified) {
        this.verified = true;
    }

    public boolean getVerified() {
        return this.verified;
    }

    public Map<String, List<Method>> getMethodsWithClass() {
        ClassParser parser = new ClassParser();
        parser.parse("D:\\5KURS\\Diplom\\TestDiag\\Diagramms\\src\\my1.uml");
        for (Class parsedClass : parser.getParsedClasses().values()) {
            methods.put(parsedClass.getName(), parsedClass.getMethods());
        }
        return methods;
    }

   /* public Map<LifeLine, List<Message>> getMessagesWithLifeLine() {
        SequenceDiagramParser parser = new SequenceDiagramParser();
        parser.parse("D:\\5KURS\\Diplom\\TestDiag\\Diagramms\\src\\new.uml");
        for (LifeLine parsedLine : parser.getLifeLineList()) {
            parsedLine.getCoveredBy();
        }
    /*public List<Fragment> getFragmentsMessages() {
       Seque
    }    */


}
